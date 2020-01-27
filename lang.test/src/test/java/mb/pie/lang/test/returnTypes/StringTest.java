package mb.pie.lang.test.returnTypes;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class StringTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new stringTestGen.TaskDefsModule_stringTestGen(), stringTestGen.main_string.class, "Hello, world!");
    }
}
