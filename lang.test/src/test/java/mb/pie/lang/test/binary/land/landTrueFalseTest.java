package mb.pie.lang.test.binary.land;

import mb.pie.api.ExecException;
import mb.pie.api.None;
import mb.pie.api.PieSession;
import mb.pie.lang.test.util.PieRunner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class landTrueFalseTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new test_binary_land_landTrueFalse.TaskDefsModule_test_binary_land_landTrueFalse(), test_binary_land_landTrueFalse.main_landTrueFalse.class, new Boolean(false));
    }
}
