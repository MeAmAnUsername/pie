package mb.pie.lang.test.binary.eq;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class eqDataDifferentTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new eqDataDifferentTestGen.TaskDefsModule_eqDataDifferentTestGen(), eqDataDifferentTestGen.main_eqDataDifferent.class, new Boolean(false));
    }
}
