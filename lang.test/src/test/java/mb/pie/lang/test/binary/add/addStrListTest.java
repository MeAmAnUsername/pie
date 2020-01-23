package mb.pie.lang.test.binary.add;

import mb.pie.api.ExecException;
import mb.pie.api.None;
import mb.pie.api.PieSession;
import mb.pie.lang.test.util.PieRunner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class addStrListTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new test_binary_add_addStrList.TaskDefsModule_test_binary_add_addStrList(), test_binary_add_addStrList.main_addStrList.class, "String + list: [1, 2, 3]");
    }
}
