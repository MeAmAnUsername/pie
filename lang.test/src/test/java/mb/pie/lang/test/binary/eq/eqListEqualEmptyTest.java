package mb.pie.lang.test.binary.eq;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class eqListEqualEmptyTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new test_binary_eq_eqListEqualEmpty.TaskDefsModule_test_binary_eq_eqListEqualEmpty(), test_binary_eq_eqListEqualEmpty.main_eqListEqualEmpty.class, new Boolean(true));
    }
}
