package mb.pie.lang.test.funcDef;

import mb.pie.api.ExecException;
import mb.pie.api.None;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class TwoFuncLinearTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new test_funcDef_twoFuncLinear.TaskDefsModule_test_funcDef_twoFuncLinear(), test_funcDef_twoFuncLinear.main_twoFuncLinear.class, None.instance);
    }
}
