package mb.pie.lang.test.returnTypes;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class NullableStringNullTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new nullableStringNullTestGen.TaskDefsModule_nullableStringNullTestGen(), nullableStringNullTestGen.main_nullableStringNull.class, null);
    }
}
