package mb.pie.lang.test.funcDef;

import mb.pie.api.ExecException;
import mb.pie.api.None;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class TwoFuncUnusedTest {
    @Test void test_main() throws ExecException {
        assertTaskOutputEquals(new test_funcDef_twoFuncUnused.TaskDefsModule_test_funcDef_twoFuncUnused(), test_funcDef_twoFuncUnused.main_twoFuncUnused.class, None.instance);
    }

    @Test void test_helper() throws ExecException {
        assertTaskOutputEquals(new test_funcDef_twoFuncUnused.TaskDefsModule_test_funcDef_twoFuncUnused(), test_funcDef_twoFuncUnused.helper_twoFuncUnused.class, None.instance);
    }
}
