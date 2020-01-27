package mb.pie.lang.test.binary.neq;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class neqIntEqualTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new neqIntEqualTestGen.TaskDefsModule_neqIntEqualTestGen(), neqIntEqualTestGen.main_neqIntEqual.class, new Boolean(false));
    }
}
