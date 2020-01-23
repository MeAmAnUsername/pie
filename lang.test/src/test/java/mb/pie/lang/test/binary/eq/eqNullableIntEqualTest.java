package mb.pie.lang.test.binary.eq;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class eqNullableIntEqualTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new test_binary_eq_eqNullableIntEqual.TaskDefsModule_test_binary_eq_eqNullableIntEqual(), test_binary_eq_eqNullableIntEqual.main_eqNullableIntEqual.class, new Boolean(true));
    }
}
