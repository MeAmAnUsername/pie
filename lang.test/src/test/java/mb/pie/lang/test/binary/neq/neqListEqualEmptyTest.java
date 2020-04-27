package mb.pie.lang.test.binary.neq;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class neqListEqualEmptyTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new test_binary_neq_neqListEqualEmpty.TaskDefsModule_test_binary_neq_neqListEqualEmpty(), test_binary_neq_neqListEqualEmpty.main_neqListEqualEmpty.class, new Boolean(false));
    }
}
