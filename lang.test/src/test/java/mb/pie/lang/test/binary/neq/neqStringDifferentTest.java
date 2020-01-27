package mb.pie.lang.test.binary.neq;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class neqStringDifferentTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new neqStringDifferentTestGen.TaskDefsModule_neqStringDifferentTestGen(), neqStringDifferentTestGen.main_neqStringDifferent.class, new Boolean(true));
    }
}
