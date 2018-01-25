package mb.pie.runtime.core.test

import com.nhaarman.mockito_kotlin.*
import mb.pie.runtime.core.*
import mb.pie.runtime.core.test.util.TestGenerator
import mb.util.async.Cancelled
import mb.util.async.NullCancelled
import mb.vfs.path.PPath
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.TestFactory
import org.mockito.Mockito

inline fun <reified T : Any> safeAny(default: T) = Mockito.any(T::class.java) ?: default

fun anyER() = safeAny<ExecReason>(UnknownExecReason())
fun anyC() = safeAny<Cancelled>(NullCancelled())

internal class ObservingExecutorTests {
  @TestFactory
  fun testTopdownExec() = TestGenerator.generate("testTopdownExec") {
    val input = "CAPITALIZED"
    val func = spy(toLowerCase)
    registerFunc(func)
    val app = app(func, input)

    val exec = spy(observingExec())
    val info = exec.require(app)
    Assertions.assertEquals(NoResultReason(), info.reason)
    val result = info.result
    Assertions.assertEquals(func.id, result.id)
    Assertions.assertEquals(input, result.input)
    Assertions.assertEquals("capitalized", result.output)
    Assertions.assertEquals(0, result.reqs.size)
    Assertions.assertEquals(0, result.gens.size)

    inOrder(exec, func) {
      verify(exec, times(1)).require(eq(app), any())
      verify(exec, times(1)).exec(eq(app), eq(NoResultReason()), any(), any())
      verify(func, times(1)).exec(eq(input), anyOrNull())
    }

    verify(func, atLeastOnce()).desc(input)
  }

  @TestFactory
  fun testExecMultiple() = TestGenerator.generate("testExecMultiple") {
    val func = spy(toLowerCase)
    registerFunc(func)

    val input1 = "CAPITALIZED"
    val app1 = app(func, input1)
    val exec1 = spy(observingExec())
    val info1 = exec1.require(app1)
    Assertions.assertEquals(NoResultReason(), info1.reason)
    val result1 = info1.result
    Assertions.assertEquals(func.id, result1.id)
    Assertions.assertEquals(input1, result1.input)
    Assertions.assertEquals("capitalized", result1.output)

    val input2 = "CAPITALIZED_EVEN_MORE"
    val app2 = app(func, input2)
    val exec2 = spy(observingExec())
    val info2 = exec2.require(app2)
    Assertions.assertEquals(NoResultReason(), info2.reason)
    val result2 = info2.result
    Assertions.assertEquals(func.id, result2.id)
    Assertions.assertEquals(input2, result2.input)
    Assertions.assertEquals("capitalized_even_more", result2.output)

    Assertions.assertNotEquals(result1, result2)

    inOrder(func, exec1, exec2) {
      verify(exec1, times(1)).require(eq(app1), any())
      verify(exec1, times(1)).exec(eq(app1), eq(NoResultReason()), any(), any())
      verify(func, times(1)).exec(eq(input1), anyOrNull())

      verify(exec2, times(1)).require(eq(app2), any())
      verify(exec2, times(1)).exec(eq(app2), eq(NoResultReason()), any(), any())
      verify(func, times(1)).exec(eq(input2), anyOrNull())
    }
  }

  @TestFactory
  fun testReuse() = TestGenerator.generate("testReuse") {
    val func = spy(toLowerCase)
    registerFunc(func)

    val input = "CAPITALIZED"
    val app = app(func, input)
    val exec1 = observingExec()
    val info1 = exec1.require(app)
    Assertions.assertEquals(NoResultReason(), info1.reason)
    val result1 = info1.result

    val exec2 = spy(observingExec())
    val info2 = exec2.require(app)
    Assertions.assertNull(info2.reason)
    val result2 = info2.result

    Assertions.assertEquals(result1, result2)

    // Result is reused if rebuild is never called
    verify(exec2, never()).exec(eq(app), eq(NoResultReason()), any(), any())

    verify(func, atMost(1)).exec(eq(input), anyOrNull())
    verify(func, atLeastOnce()).desc(input)
  }

  @TestFactory
  fun testPathChange() = TestGenerator.generate("testReuse") {
    val toLowerCase = spy(toLowerCase)
    registerFunc(toLowerCase)
    val readPath = spy(readPath)
    registerFunc(readPath)
    val combine = spy(func<PPath, String>("combine", { "toLowerCase(read($it))" }) {
      val text = requireOutput(app(readPath, it))
      requireOutput(app(toLowerCase, text))
    })
    registerFunc(combine)

    val filePath = path(fs, "/file")
    write("HELLO WORLD!", filePath)

    // Build 'combine', observe rebuild of all
    val exec1 = spy(observingExec())
    val result1 = exec1.require(app(combine, filePath)).result
    Assertions.assertEquals("hello world!", result1.output)
    inOrder(exec1) {
      verify(exec1).exec(eq(app(combine, filePath)), eq(NoResultReason()), anyC(), any())
      verify(exec1).exec(eq(app(readPath, filePath)), eq(NoResultReason()), anyC(), any())
      verify(exec1).exec(eq(app(toLowerCase, "HELLO WORLD!")), eq(NoResultReason()), anyC(), any())
    }

    // Change required file in such a way that the output of 'readPath' changes (change file content)
    val newStr = "!DLROW OLLEH"
    write(newStr, filePath)

    // Notify of path change, observe bottom-up execution to [combine], and then top-down execution of [toLowerCase].
    val exec2 = spy(observingExec())
    exec2.pathsChanged(listOf(filePath), NullCancelled())
    inOrder(exec2) {
      verify(exec2).requireBottomUp(eq(app(readPath, filePath)), anyER(), anyC())
      verify(exec2).exec(eq(app(readPath, filePath)), anyER(), anyC(), any())
      verify(exec2).requireBottomUp(eq(app(combine, filePath)), anyER(), anyC())
      verify(exec2).exec(eq(app(combine, filePath)), anyER(), anyC(), any())
      verify(exec2).require(eq(app(toLowerCase, newStr)), anyC())
      verify(exec2).exec(eq(app(toLowerCase, newStr)), anyER(), anyC(), any())
    }

    // Notify of path change, but path hasn't actually changed, observe no execution.
    val exec3 = spy(observingExec())
    exec3.pathsChanged(listOf(filePath), NullCancelled())
    verify(exec3, never()).exec(eq(app(readPath, filePath)), anyER(), anyC(), any())
    verify(exec3, never()).exec(eq(app(combine, filePath)), anyER(), anyC(), any())
    verify(exec3, never()).exec(eq(app(toLowerCase, newStr)), anyER(), anyC(), any())

    // Change required file in such a way that the file changes, but the output of [readPath] does not.
    write(newStr, filePath)

    // Notify of path change, observe bottom-up execution of [readPath], but stop there because [combine] is still consistent
    val exec4 = spy(observingExec())
    exec4.pathsChanged(listOf(filePath), NullCancelled())
    inOrder(exec4) {
      verify(exec4).requireBottomUp(eq(app(readPath, filePath)), anyER(), anyC())
      verify(exec4).exec(eq(app(readPath, filePath)), anyER(), anyC(), any())
    }
    verify(exec4, never()).requireBottomUp(eq(app(combine, filePath)), anyER(), anyC())
    verify(exec4, never()).exec(eq(app(combine, filePath)), anyER(), anyC(), any())
    verify(exec4, never()).require(eq(app(toLowerCase, newStr)), anyC())
    verify(exec4, never()).exec(eq(app(toLowerCase, newStr)), anyER(), anyC(), any())
  }
}