package mb.pie.lang.test.binary.eq;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class eqIntDifferentTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new eqIntDifferentTestGen.TaskDefsModule_eqIntDifferentTestGen(), eqIntDifferentTestGen.main_eqIntDifferent.class, new Boolean(false));
    }
}
