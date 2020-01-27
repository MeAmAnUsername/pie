package mb.pie.lang.test.binary.land;

import mb.pie.api.ExecException;
import mb.pie.api.None;
import mb.pie.api.PieSession;
import mb.pie.lang.test.util.PieRunner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class landTrueTrueTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new landTrueTrueTestGen.TaskDefsModule_landTrueTrueTestGen(), landTrueTrueTestGen.main_landTrueTrue.class, new Boolean(true));
    }
}
