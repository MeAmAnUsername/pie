package mb.pie.lang.test.binary.neq;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class neqListEqualTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new neqListEqualTestGen.TaskDefsModule_neqListEqualTestGen(), neqListEqualTestGen.main_neqListEqual.class, new Boolean(false));
    }
}
