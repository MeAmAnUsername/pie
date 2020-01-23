package mb.pie.lang.test.funcDef;

import mb.pie.api.ExecException;
import mb.pie.api.None;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class OneFuncTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new test_funcDef_oneFunc.TaskDefsModule_test_funcDef_oneFunc(), test_funcDef_oneFunc.main_oneFunc.class, None.instance);
    }
}
