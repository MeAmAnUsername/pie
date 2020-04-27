package mb.pie.lang.test.binary.neq;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class neqStringDifferentTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new test_binary_neq_neqStringDifferent.TaskDefsModule_test_binary_neq_neqStringDifferent(), test_binary_neq_neqStringDifferent.main_neqStringDifferent.class, new Boolean(true));
    }
}
