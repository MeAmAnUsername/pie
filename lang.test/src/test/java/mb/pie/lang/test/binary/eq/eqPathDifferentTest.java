package mb.pie.lang.test.binary.eq;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class eqPathDifferentTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new test_binary_eq_eqPathDifferent.TaskDefsModule_test_binary_eq_eqPathDifferent(), test_binary_eq_eqPathDifferent.main_eqPathDifferent.class, new Boolean(false));
    }
}
