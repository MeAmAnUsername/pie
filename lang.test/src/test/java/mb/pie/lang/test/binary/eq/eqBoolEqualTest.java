package mb.pie.lang.test.binary.eq;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class eqBoolEqualTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new test_binary_eq_eqBoolEqual.TaskDefsModule_test_binary_eq_eqBoolEqual(), test_binary_eq_eqBoolEqual.main_eqBoolEqual.class, new Boolean(true));
    }
}
