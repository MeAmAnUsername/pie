package mb.pie.lang.test.binary.neq;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class neqTupleEqualTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new test_binary_neq_neqTupleEqual.TaskDefsModule_test_binary_neq_neqTupleEqual(), test_binary_neq_neqTupleEqual.main_neqTupleEqual.class, new Boolean(false));
    }
}
