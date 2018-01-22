package mb.pie.runtime.core.impl

import com.google.inject.Inject
import com.google.inject.Provider
import com.google.inject.assistedinject.Assisted
import mb.pie.runtime.core.*
import mb.util.async.Cancelled
import mb.vfs.path.PPath
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

class PushingExecutorImpl @Inject constructor(
  @Assisted private val store: Store,
  @Assisted private val cache: Cache,
  private val share: Share,
  private val layer: Provider<Layer>,
  private val logger: Provider<Logger>,
  private val funcs: MutableMap<String, UFunc>,
  mbLogger: mb.log.Logger
) : PushingExecutor {
  private val mbLogger = mbLogger.forContext(PushingExecutorImpl::class.java)
  private val dirtyFlagger = DirtyFlaggerImpl(cache, mbLogger)
  private val obsFuncApps = ConcurrentHashMap<Any, AnyObsFuncApp>()
  private val changedPaths = HashSet<PPath>()
  private val lock = ReentrantReadWriteLock()


  override fun add(key: Any, obsFuncApp: AnyObsFuncApp) {
    obsFuncApps[key] = obsFuncApp
  }

  override fun update(key: Any, obsFuncApp: AnyObsFuncApp) {
    obsFuncApps[key] = obsFuncApp
  }

  override fun remove(key: Any) {
    obsFuncApps.remove(key)
  }


  override fun pathChanged(path: PPath) {
    changedPaths.add(path)
  }

  override fun pathsChanged(paths: Collection<PPath>) {
    changedPaths.addAll(paths)
  }

  override fun dirtyFlag() {
    lock.write {
      try {
        store.writeTxn().use {
          dirtyFlagger.flag(changedPaths, it)
        }
      } finally {
        changedPaths.clear()
        store.sync()
      }
    }
  }


  override fun executeAll(cancel: Cancelled) {
    lock.read {
      try {
        mbLogger.trace("Execution")
        val exec = PushingExec(store, cache, share, layer.get(), logger.get(), funcs, dirtyFlagger)
        // TODO: observable functions may change during iteration, and will not be updated. Is this a problem?
        for((funcApp, changedFunc) in obsFuncApps.values) {
          mbLogger.trace("  requiring: ${funcApp.toShortString(200)}")
          val info = exec.require(funcApp, cancel)
          changedFunc(info.result.output)
        }
      } finally {
        store.sync()
      }
    }
  }

  override fun addAndExecute(key: Any, obsFuncApp: AnyObsFuncApp, cancel: Cancelled) {
    obsFuncApps[key] = obsFuncApp
    execOne(obsFuncApp, cancel)
  }

  override fun updateAndExecute(key: Any, obsFuncApp: AnyObsFuncApp, cancel: Cancelled) {
    obsFuncApps[key] = obsFuncApp
    execOne(obsFuncApp, cancel)
  }

  private fun execOne(obsFuncApp: AnyObsFuncApp, cancel: Cancelled) {
    lock.read {
      val exec = exec()
      try {
        val (funcApp, changedFunc) = obsFuncApp
        val info = exec.require(funcApp, cancel)
        changedFunc(info.result.output)
      } finally {
        store.sync()
      }
    }
  }


  fun exec(): PushingExec {
    return PushingExec(store, cache, share, layer.get(), logger.get(), funcs, dirtyFlagger)
  }

  override fun dropStore() {
    store.writeTxn().use { it.drop() }
    store.sync()
  }

  override fun dropCache() {
    cache.drop()
  }
}

open class PushingExec(
  private val store: Store,
  private val cache: Cache,
  private val share: Share,
  private val layer: Layer,
  private val logger: Logger,
  private val funcs: Map<String, UFunc>,
  private val dirtyFlagger: DirtyFlagger
) : Exec, Funcs by FuncsImpl(funcs) {
  private val consistent = mutableMapOf<UFuncApp, UExecRes>()


  override fun <I : In, O : Out> require(app: FuncApp<I, O>, cancel: Cancelled): ExecInfo<I, O> {
    cancel.throwIfCancelled()

    try {
      layer.requireStart(app)
      logger.requireStart(app)

      // Check if function application is already consistent this execution.
      logger.checkConsistentStart(app)
      val consistentResult = consistent[app]?.cast<I, O>()
      if(consistentResult != null) {
        // Existing result is known to be consistent this build: reuse
        logger.checkConsistentEnd(app, consistentResult)
        val info = ExecInfo(consistentResult)
        logger.requireEnd(app, info)
        return info
      }
      logger.checkConsistentEnd(app, null)

      // Check cache for result of build application.
      logger.checkCachedStart(app)
      val cachedResult = cache[app]
      logger.checkCachedEnd(app, cachedResult)

      // Check store for result of build application.
      val existingUntypedResult = if(cachedResult != null) {
        cachedResult
      } else {
        logger.checkStoredStart(app)
        val result = store.readTxn().use { it.resultOf(app) }
        logger.checkStoredEnd(app, result)
        result
      }

      // Check if rebuild is necessary.
      val existingResult = existingUntypedResult?.cast<I, O>()
      if(existingResult == null) {
        // No cached or stored result was found: rebuild
        val info = exec(app, NoResultReason(), cancel, true)
        logger.requireEnd(app, info)
        return info
      }

      // Internal consistency: result internal consistency
      run {
        val reason = existingResult.internalInconsistencyReason
        if(reason != null) {
          val info = exec(app, reason, cancel)
          logger.requireEnd(app, info)
          return info
        }
      }

      // Internal consistency: dirty flagged.
      if(store.readTxn().use { it.isDirty(app) }) {
        val info = exec(app, DirtyFlaggedReason(), cancel)
        logger.requireEnd(app, info)
        return info
      }

      // Internal consistency: path gens
      for(gen in existingResult.gens) {
        val (genPath, stamp) = gen
        logger.checkGenStart(app, gen)
        val newStamp = stamp.stamper.stamp(genPath)
        if(stamp != newStamp) {
          // If a generated file is outdated (i.e., its stamp changed): rebuild
          val reason = InconsistentGenPath(existingResult, gen, newStamp)
          logger.checkGenEnd(app, gen, reason)
          val info = exec(app, reason, cancel)
          logger.requireEnd(app, info)
          return info
        } else {
          logger.checkGenEnd(app, gen, null)
        }
      }

      // TODO: is checking path reqs necessary?
      // Internal consistency: path reqs
      for(req in existingResult.pathReqs) {
        val inconsistencyReason = req.makeConsistent(app, existingResult, this, cancel, logger)
        if(inconsistencyReason != null) {
          val info = exec(app, inconsistencyReason, cancel)
          logger.requireEnd(app, info)
          return info
        }
      }

      // No inconsistencies found
      // Validate well-formedness of the dependency graph
      store.readTxn().use { txn -> layer.validate(app, existingResult, this, txn) }
      store.writeTxn().use { txn ->
        // Flag generated files dirty.
        // TODO: is this necessary? If this func app is not executed, its generated files cannot change?
        dirtyFlagger.flag(existingResult.gens.map { it.path }, txn)
        // Mark not dirty.
        txn.setIsDirty(app, false)
      }
      // Mark consistent this execution
      consistent[app] = existingResult
      // Cache result
      cache[app] = existingResult
      // Reuse existing result
      val info = ExecInfo(existingResult)
      logger.requireEnd(app, info)
      return info
    } finally {
      layer.requireEnd(app)
    }
  }

  // Method is open internal for testability
  internal open fun <I : In, O : Out> exec(app: FuncApp<I, O>, reason: ExecReason, cancel: Cancelled, useCache: Boolean = false): ExecInfo<I, O> {
    cancel.throwIfCancelled()

    logger.rebuildStart(app, reason)
    val result = if(useCache) {
      share.reuseOrCreate(app, { store.readTxn().use { txn -> txn.resultOf(it)?.cast<I, O>() } }) { this.execInternal(it, cancel) }
    } else {
      share.reuseOrCreate(app) { this.execInternal(it, cancel) }
    }
    logger.rebuildEnd(app, reason, result)
    return ExecInfo(result, reason)
  }

  // Method is open internal for testability
  internal open fun <I : In, O : Out> execInternal(app: FuncApp<I, O>, cancel: Cancelled): ExecRes<I, O> {
    cancel.throwIfCancelled()

    val (builderId, input) = app
    val builder = getFunc<I, O>(builderId)
    val desc = builder.desc(input)
    val context = ExecContextImpl(this, store, app, cancel)

    try {
      val output = builder.exec(input, context)
      val (reqs, gens) = context.getReqsAndGens()
      val result = ExecRes(builderId, desc, input, output, reqs, gens)

      // Validate well-formedness of the dependency graph
      store.readTxn().use { txn -> layer.validate(app, result, this, txn) }
      store.writeTxn().use { txn ->
        // Store result
        txn.setResultOf(app, result)
        // Store path dependencies
        context.writePathDepsToStore(txn)
        // Flag generated files dirty.
        dirtyFlagger.flag(result.gens.map { it.path }, txn)
        // Mark not dirty.
        txn.setIsDirty(app, false)
      }
      // Mark consistent this execution
      consistent[app] = result
      // Cache result
      cache[app] = result

      return result
    } catch(e: InterruptedException) {
      store.writeTxn().use { txn ->
        // Store path dependencies
        context.writePathDepsToStore(txn)
      }
      throw e
    }
  }
}

class DirtyFlaggedReason : ExecReason {
  override fun toString() = "flagged dirty"


  override fun equals(other: Any?): Boolean {
    if(this === other) return true
    if(other?.javaClass != javaClass) return false
    return true
  }

  override fun hashCode(): Int {
    return 0
  }
}