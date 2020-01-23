package mb.pie.lang.test.returnTypes;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class StringTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new test_returnTypes_string.TaskDefsModule_test_returnTypes_string(), test_returnTypes_string.main_string.class, "Hello, world!");
    }
}
