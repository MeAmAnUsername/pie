package mb.pie.lang.test.binary.neq;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class neqListEqualEmptyTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new neqListEqualEmptyTestGen.TaskDefsModule_neqListEqualEmptyTestGen(), neqListEqualEmptyTestGen.main_neqListEqualEmpty.class, new Boolean(false));
    }
}