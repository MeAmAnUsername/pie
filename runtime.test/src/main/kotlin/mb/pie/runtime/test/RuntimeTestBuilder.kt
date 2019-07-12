package mb.pie.runtime.test

import com.nhaarman.mockitokotlin2.spy
import mb.pie.api.*
import mb.pie.api.test.ApiTestBuilder
import mb.pie.runtime.DefaultStampers
import mb.pie.runtime.PieBuilderImpl
import mb.pie.runtime.PieImpl
import mb.pie.runtime.PieSessionImpl
import mb.pie.runtime.exec.BottomUpSession
import mb.pie.runtime.exec.RequireShared
import mb.pie.runtime.exec.TaskExecutor
import mb.pie.runtime.exec.TopDownSession
import mb.pie.runtime.layer.ValidationLayer
import mb.pie.runtime.logger.StreamLogger
import mb.pie.runtime.logger.exec.LoggerExecutorLogger
import mb.pie.runtime.share.NonSharingShare
import mb.pie.runtime.store.InMemoryStore
import mb.resource.ResourceService
import java.io.Serializable
import java.nio.file.FileSystem
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.function.BiFunction
import java.util.function.Consumer
import java.util.function.Function

abstract class RuntimeTestBuilder<Ctx : RuntimeTestCtx>(
  private val shouldSpy: Boolean = true,
  override val testContextFactory: (FileSystem, MapTaskDefs, Pie) -> Ctx
) : ApiTestBuilder<Ctx>(
  { TestPieBuilderImpl(shouldSpy) },
  { StreamLogger.onlyErrors() },
  { l -> LoggerExecutorLogger(l) },
  testContextFactory
) {
  init {
    storeFactories.add { _ -> InMemoryStore() }
    shareFactories.add { _ -> NonSharingShare() }
    layerFactories.add { td, l -> ValidationLayer(td, l) }
  }
}

open class TestPieBuilderImpl(private val shouldSpy: Boolean) : PieBuilderImpl() {
  override fun build(): PieImpl {
    val store = store.apply(logger)
    val share = share.apply(logger)
    val defaultStampers = DefaultStampers(defaultOutputStamper, defaultRequireReadableStamper, defaultProvideReadableStamper,
      defaultRequireHierarchicalStamper, defaultProvideHierarchicalStamper)
    return TestPieImpl(shouldSpy, taskDefs, resourceService, store, share, defaultStampers, layer, logger, executorLoggerFactory)
  }
}

open class TestPieImpl(
  private val shouldSpy: Boolean,
  taskDefs: TaskDefs,
  resourceService: ResourceService,
  store: Store,
  share: Share,
  defaultStampers: DefaultStampers,
  layerFactory: BiFunction<TaskDefs, Logger, Layer>,
  logger: Logger,
  executorLoggerFactory: Function<Logger, ExecutorLogger>
) : PieImpl(taskDefs, resourceService, store, share, defaultStampers, layerFactory, logger, executorLoggerFactory) {
  val store: Store get() = super.store // Make store available for testing.

  override fun createSession(taskDefs: TaskDefs): PieSession {
    val layer = layerFactory.apply(taskDefs, logger)
    val executorLogger = executorLoggerFactory.apply(logger)
    val visited = HashMap<TaskKey, TaskData>()

    val taskExecutor = TaskExecutor(taskDefs, resourceService, super.store, share, defaultStampers, layer, logger, executorLogger,
      callbacks, visited)
    val requireShared = RequireShared(taskDefs, resourceService, super.store, executorLogger, visited)

    var topDownSession = TopDownSession(super.store, layer, executorLogger, taskExecutor, requireShared, callbacks, visited)
    if(shouldSpy) {
      topDownSession = spy(topDownSession)
    }

    var bottomUpSession = BottomUpSession(taskDefs, resourceService, super.store, layer, logger, executorLogger, taskExecutor,
      requireShared, callbacks, visited)
    if(shouldSpy) {
      bottomUpSession = spy(bottomUpSession)
    }

    var session = TestPieSessionImpl(topDownSession, bottomUpSession, taskDefs, resourceService, super.store, callbacks)
    if(shouldSpy) {
      session = spy(session)
    }

    return session
  }
}

open class TestPieSessionImpl(
  topDownSession: TopDownSession,
  bottomUpSession: BottomUpSession,
  taskDefs: TaskDefs,
  resourceService: ResourceService,
  store: Store,
  callbacks: ConcurrentHashMap<TaskKey, Consumer<Serializable?>>
) : PieSessionImpl(topDownSession, bottomUpSession, taskDefs, resourceService, store, callbacks) {
  // Make store available for testing.
  val store: Store get() = super.store

  // Make (possibly spy-ed) sessions visible for testing.
  val topDownSession: TopDownSession get() = super.topDownSession
  val bottomUpSession: BottomUpSession get() = super.bottomUpSession
}


open class DefaultRuntimeTestBuilder(shouldSpy: Boolean = true) : RuntimeTestBuilder<RuntimeTestCtx>(
  shouldSpy,
  { fs, taskDefs, pie -> RuntimeTestCtx(fs, taskDefs, pie as TestPieImpl) }
)
