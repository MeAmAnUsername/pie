package mb.pie.lang.test.binary.add;

import mb.pie.api.ExecException;
import mb.pie.api.None;
import mb.pie.api.PieSession;
import mb.pie.lang.test.util.PieRunner;
import mb.resource.fs.FSPath;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class addPathPathRelativeAbsoluteTestGenTest {
    @Test void test() throws ExecException {
        FSPath expected = new FSPath("/path/to/foo");
        assertThrows(ExecException.class, () -> {
            assertTaskOutputEquals(new test_binary_add_addPathPathRelativeAbsolute.TaskDefsModule_test_binary_add_addPathPathRelativeAbsolute(), test_binary_add_addPathPathRelativeAbsolute.main_addPathPathRelativeAbsolute.class, expected);
        });
    }
}
