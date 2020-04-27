package mb.pie.lang.test.returnTypes;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class IntTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new test_returnTypes_int.TaskDefsModule_test_returnTypes_int(), test_returnTypes_int.main_int.class, new Integer(6));
    }
}
