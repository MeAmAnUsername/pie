package mb.pie.lang.test.binary.neq;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class neqDataDifferentTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new test_binary_neq_neqDataDifferent.TaskDefsModule_test_binary_neq_neqDataDifferent(), test_binary_neq_neqDataDifferent.main_neqDataDifferent.class, new Boolean(true));
    }
}
