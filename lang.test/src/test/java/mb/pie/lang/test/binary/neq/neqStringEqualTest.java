package mb.pie.lang.test.binary.neq;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class neqStringEqualTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new test_binary_neq_neqStringEqual.TaskDefsModule_test_binary_neq_neqStringEqual(), test_binary_neq_neqStringEqual.main_neqStringEqual.class, new Boolean(false));
    }
}
