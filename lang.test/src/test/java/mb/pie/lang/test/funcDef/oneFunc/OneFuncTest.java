package mb.pie.lang.test.funcDef.oneFunc;

import mb.pie.api.*;
import mb.pie.runtime.PieBuilderImpl;
import org.junit.jupiter.api.Test;

import java.io.Serializable;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OneFuncTest {
    @Test void test() throws Exception {
        assertTaskOutputEquals(DaggeroneFuncComponent.create(), None.instance);
    }

    public static <O extends Serializable> O assertTaskOutputEquals(TaskDef<None, O> main, O expectedOutput)
        throws ExecException {
        return assertTaskOutputEquals(main, None.instance, expectedOutput);
    }


    public static <I extends Serializable, O extends Serializable> O assertTaskOutputEquals(
        TaskDef<I, O> main, I input, O expectedOutput) throws ExecException {
        Pie pie = new PieBuilderImpl().build();
        try (MixedSession session = pie.newSession()) {
            final O actualOutput = session.require(main.createTask(input));
            assertEquals(expectedOutput, actualOutput);
            return actualOutput;
        }
    }
}
