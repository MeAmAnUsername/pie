package mb.pie.lang.test.binary.neq;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class neqPathEqualTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new test_binary_neq_neqPathEqual.TaskDefsModule_test_binary_neq_neqPathEqual(), test_binary_neq_neqPathEqual.main_neqPathEqual.class, new Boolean(false));
    }
}
