package mb.pie.lang.test.binary.eq;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class eqIntEqualTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new test_binary_eq_eqIntEqual.TaskDefsModule_test_binary_eq_eqIntEqual(), test_binary_eq_eqIntEqual.main_eqIntEqual.class, new Boolean(true));
    }
}
