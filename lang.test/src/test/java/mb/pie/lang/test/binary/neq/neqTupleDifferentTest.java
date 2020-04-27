package mb.pie.lang.test.binary.neq;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class neqTupleDifferentTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new test_binary_neq_neqTupleDifferent.TaskDefsModule_test_binary_neq_neqTupleDifferent(), test_binary_neq_neqTupleDifferent.main_neqTupleDifferent.class, new Boolean(true));
    }
}
