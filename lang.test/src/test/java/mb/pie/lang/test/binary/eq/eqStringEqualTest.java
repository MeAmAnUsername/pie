package mb.pie.lang.test.binary.eq;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class eqStringEqualTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new eqStringEqualTestGen.TaskDefsModule_eqStringEqualTestGen(), eqStringEqualTestGen.main_eqStringEqual.class, new Boolean(true));
    }
}