package mb.pie.lang.test.binary.neq;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class neqDataEqualTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new test_binary_neq_neqDataEqual.TaskDefsModule_test_binary_neq_neqDataEqual(), test_binary_neq_neqDataEqual.main_neqDataEqual.class, new Boolean(false));
    }
}
