package mb.pie.lang.test.binary.neq;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class neqListDifferentElementsTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new test_binary_neq_neqListDifferentElements.TaskDefsModule_test_binary_neq_neqListDifferentElements(), test_binary_neq_neqListDifferentElements.main_neqListDifferentElements.class, new Boolean(true));
    }
}
