package mb.pie.lang.test.binary.add;

import mb.pie.api.ExecException;
import mb.pie.api.None;
import mb.pie.api.PieSession;
import mb.pie.lang.test.util.PieRunner;
import mb.resource.fs.FSPath;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class addPathPathAbsoluteRelativeTest {
    @Test void test() throws ExecException {
        FSPath expected = new FSPath("/path/to/./foo");
        assertTaskOutputEquals(new test_binary_add_addPathPathAbsoluteRelative.TaskDefsModule_test_binary_add_addPathPathAbsoluteRelative(), test_binary_add_addPathPathAbsoluteRelative.main_addPathPathAbsoluteRelative.class, expected);
    }
}
