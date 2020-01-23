package mb.pie.lang.test.binary.neq;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class neqBoolEqualTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new test_binary_neq_neqBoolEqual.TaskDefsModule_test_binary_neq_neqBoolEqual(), test_binary_neq_neqBoolEqual.main_neqBoolEqual.class, new Boolean(false));
    }
}
