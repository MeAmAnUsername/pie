package mb.pie.lang.test.returnTypes;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class BoolFalseTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new test_returnTypes_boolFalse.TaskDefsModule_test_returnTypes_boolFalse(), test_returnTypes_boolFalse.main_boolFalse.class, new Boolean(false));
    }
}
