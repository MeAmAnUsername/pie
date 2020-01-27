package mb.pie.lang.test.binary.neq;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class neqTupleDifferentTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new neqTupleDifferentTestGen.TaskDefsModule_neqTupleDifferentTestGen(), neqTupleDifferentTestGen.main_neqTupleDifferent.class, new Boolean(true));
    }
}
