package mb.pie.lang.test.returnTypes;

import mb.pie.api.ExecException;
import mb.resource.fs.FSPath;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class PathAbsoluteTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new TaskDefsModule_pathAbsoluteTestGen(), main_pathAbsolute.class, new FSPath("/foo/bar"));
    }
}
