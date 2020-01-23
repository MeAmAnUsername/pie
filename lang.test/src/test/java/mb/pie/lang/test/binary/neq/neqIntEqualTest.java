package mb.pie.lang.test.binary.neq;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class neqIntEqualTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new test_binary_neq_neqIntEqual.TaskDefsModule_test_binary_neq_neqIntEqual(), test_binary_neq_neqIntEqual.main_neqIntEqual.class, new Boolean(false));
    }
}
