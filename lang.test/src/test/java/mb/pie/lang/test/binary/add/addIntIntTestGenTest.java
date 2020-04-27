package mb.pie.lang.test.binary.add;

import mb.pie.api.ExecException;
import mb.pie.api.None;
import mb.pie.api.PieSession;
import mb.pie.lang.test.util.PieRunner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class addIntIntTestGenTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new test_binary_add_addIntInt.TaskDefsModule_test_binary_add_addIntInt(), test_binary_add_addIntInt.main_addIntInt.class, 9);
    }
}
