package mb.pie.lang.test.binary.eq;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class eqStringDifferentTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new test_binary_eq_eqStringDifferent.TaskDefsModule_test_binary_eq_eqStringDifferent(), test_binary_eq_eqStringDifferent.main_eqStringDifferent.class, new Boolean(false));
    }
}
