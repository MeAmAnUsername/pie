package mb.pie.lang.test.binary.neq;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class neqBoolDifferentTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new test_binary_neq_neqBoolDifferent.TaskDefsModule_test_binary_neq_neqBoolDifferent(), test_binary_neq_neqBoolDifferent.main_neqBoolDifferent.class, new Boolean(true));
    }
}
