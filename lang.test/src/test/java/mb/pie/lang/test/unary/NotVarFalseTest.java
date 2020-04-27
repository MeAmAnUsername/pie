package mb.pie.lang.test.unary;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class NotVarFalseTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new test_unary_notVarFalse.TaskDefsModule_test_unary_notVarFalse(), test_unary_notVarFalse.main_notVarFalse.class, new Boolean(true));
    }
}
