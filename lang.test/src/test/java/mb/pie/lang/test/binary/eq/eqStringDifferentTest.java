package mb.pie.lang.test.binary.eq;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class eqStringDifferentTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new eqStringDifferentTestGen.TaskDefsModule_eqStringDifferentTestGen(), eqStringDifferentTestGen.main_eqStringDifferent.class, new Boolean(false));
    }
}
