package mb.pie.lang.test.unary;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ToNonNullableNullTest {
    @Test()
    void test() throws ExecException {
        assertThrows(ExecException.class, () -> {
            assertTaskOutputEquals(new test_unary_toNonNullableNull.TaskDefsModule_test_unary_toNonNullableNull(), test_unary_toNonNullableNull.main_toNonNullableNull.class, new Integer(4));
        });
    }
}
