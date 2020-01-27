package mb.pie.lang.test.binary.neq;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class neqStringEqualTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new neqStringEqualTestGen.TaskDefsModule_neqStringEqualTestGen(), neqStringEqualTestGen.main_neqStringEqual.class, new Boolean(false));
    }
}
