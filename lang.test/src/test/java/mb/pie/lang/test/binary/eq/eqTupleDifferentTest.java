package mb.pie.lang.test.binary.eq;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class eqTupleDifferentTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new test_binary_eq_eqTupleDifferent.TaskDefsModule_test_binary_eq_eqTupleDifferent(), test_binary_eq_eqTupleDifferent.main_eqTupleDifferent.class, new Boolean(false));
    }
}
