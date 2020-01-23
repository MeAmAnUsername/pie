package mb.pie.lang.test.binary.eq;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class eqListEqualTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new test_binary_eq_eqListEqual.TaskDefsModule_test_binary_eq_eqListEqual(), test_binary_eq_eqListEqual.main_eqListEqual.class, new Boolean(true));
    }
}
