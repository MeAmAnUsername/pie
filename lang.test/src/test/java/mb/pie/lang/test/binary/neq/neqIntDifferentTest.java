package mb.pie.lang.test.binary.neq;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class neqIntDifferentTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new test_binary_neq_neqIntDifferent.TaskDefsModule_test_binary_neq_neqIntDifferent(), test_binary_neq_neqIntDifferent.main_neqIntDifferent.class, new Boolean(true));
    }
}
