package mb.pie.lang.test.binary.neq;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class neqListEqualTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new test_binary_neq_neqListEqual.TaskDefsModule_test_binary_neq_neqListEqual(), test_binary_neq_neqListEqual.main_neqListEqual.class, new Boolean(false));
    }
}
