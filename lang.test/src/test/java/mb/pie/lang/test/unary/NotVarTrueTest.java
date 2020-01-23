package mb.pie.lang.test.unary;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class NotVarTrueTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new test_unary_notVarTrue.TaskDefsModule_test_unary_notVarTrue(), test_unary_notVarTrue.main_notVarTrue.class, new Boolean(false));
    }
}
