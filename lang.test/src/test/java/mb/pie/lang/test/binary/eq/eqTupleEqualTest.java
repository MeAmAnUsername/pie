package mb.pie.lang.test.binary.eq;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class eqTupleEqualTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new eqTupleEqualTestGen.TaskDefsModule_eqTupleEqualTestGen(), eqTupleEqualTestGen.main_eqTupleEqual.class, new Boolean(true));
    }
}