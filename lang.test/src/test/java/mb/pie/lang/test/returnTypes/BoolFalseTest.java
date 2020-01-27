package mb.pie.lang.test.returnTypes;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class BoolFalseTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new boolFalseTestGen.TaskDefsModule_boolFalseTestGen(), boolFalseTestGen.main_boolFalse.class, new Boolean(false));
    }
}
