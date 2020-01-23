package mb.pie.lang.test.binary.land;

import mb.pie.api.ExecException;
import mb.pie.api.None;
import mb.pie.api.PieSession;
import mb.pie.lang.test.util.PieRunner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class landFalseFalseTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new test_binary_land_landFalseFalse.TaskDefsModule_test_binary_land_landFalseFalse(), test_binary_land_landFalseFalse.main_landFalseFalse.class, new Boolean(false));
    }
}
