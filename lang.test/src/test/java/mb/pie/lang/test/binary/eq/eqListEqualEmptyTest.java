package mb.pie.lang.test.binary.eq;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class eqListEqualEmptyTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new eqListEqualEmptyTestGen.TaskDefsModule_eqListEqualEmptyTestGen(), eqListEqualEmptyTestGen.main_eqListEqualEmpty.class, new Boolean(true));
    }
}
