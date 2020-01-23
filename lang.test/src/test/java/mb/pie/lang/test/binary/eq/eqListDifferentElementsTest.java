package mb.pie.lang.test.binary.eq;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class eqListDifferentElementsTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new test_binary_eq_eqListDifferentElements.TaskDefsModule_test_binary_eq_eqListDifferentElements(), test_binary_eq_eqListDifferentElements.main_eqListDifferentElements.class, new Boolean(false));
    }
}
