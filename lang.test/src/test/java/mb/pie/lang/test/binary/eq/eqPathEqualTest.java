package mb.pie.lang.test.binary.eq;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class eqPathEqualTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new test_binary_eq_eqPathEqual.TaskDefsModule_test_binary_eq_eqPathEqual(), test_binary_eq_eqPathEqual.main_eqPathEqual.class, new Boolean(true));
    }
}
