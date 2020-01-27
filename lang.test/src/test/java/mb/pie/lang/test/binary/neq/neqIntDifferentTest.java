package mb.pie.lang.test.binary.neq;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class neqIntDifferentTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new neqIntDifferentTestGen.TaskDefsModule_neqIntDifferentTestGen(), neqIntDifferentTestGen.main_neqIntDifferent.class, new Boolean(true));
    }
}
