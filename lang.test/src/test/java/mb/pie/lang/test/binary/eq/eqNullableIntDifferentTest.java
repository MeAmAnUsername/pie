package mb.pie.lang.test.binary.eq;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class eqNullableIntDifferentTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new test_binary_eq_eqNullableIntDifferent.TaskDefsModule_test_binary_eq_eqNullableIntDifferent(), test_binary_eq_eqNullableIntDifferent.main_eqNullableIntDifferent.class, new Boolean(false));
    }
}
