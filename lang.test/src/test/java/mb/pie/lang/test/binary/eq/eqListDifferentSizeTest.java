package mb.pie.lang.test.binary.eq;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class eqListDifferentSizeTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new test_binary_eq_eqListDifferentSize.TaskDefsModule_test_binary_eq_eqListDifferentSize(), test_binary_eq_eqListDifferentSize.main_eqListDifferentSize.class, new Boolean(false));
    }
}
