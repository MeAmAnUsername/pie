package mb.pie.lang.test.binary.neq;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class neqListDifferentSizeTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new test_binary_neq_neqListDifferentSize.TaskDefsModule_test_binary_neq_neqListDifferentSize(), test_binary_neq_neqListDifferentSize.main_neqListDifferentSize.class, new Boolean(true));
    }
}
