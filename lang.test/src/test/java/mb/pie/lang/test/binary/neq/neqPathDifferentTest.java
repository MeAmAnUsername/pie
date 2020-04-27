package mb.pie.lang.test.binary.neq;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class neqPathDifferentTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new test_binary_neq_neqPathDifferent.TaskDefsModule_test_binary_neq_neqPathDifferent(), test_binary_neq_neqPathDifferent.main_neqPathDifferent.class, new Boolean(true));
    }
}
