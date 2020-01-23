package mb.pie.lang.test.binary.eq;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class eqTupleEqualTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new test_binary_eq_eqTupleEqual.TaskDefsModule_test_binary_eq_eqTupleEqual(), test_binary_eq_eqTupleEqual.main_eqTupleEqual.class, new Boolean(true));
    }
}
