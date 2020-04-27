package mb.pie.lang.test.binary.eq;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class eqDataDifferentTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new test_binary_eq_eqDataDifferent.TaskDefsModule_test_binary_eq_eqDataDifferent(), test_binary_eq_eqDataDifferent.main_eqDataDifferent.class, new Boolean(false));
    }
}
