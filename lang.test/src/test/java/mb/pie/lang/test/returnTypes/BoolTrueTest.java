package mb.pie.lang.test.returnTypes;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class BoolTrueTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new test_returnTypes_boolTrue.TaskDefsModule_test_returnTypes_boolTrue(), test_returnTypes_boolTrue.main_boolTrue.class, new Boolean(true));
    }
}
