package mb.pie.lang.test.binary.add;

import mb.pie.api.ExecException;
import mb.pie.api.None;
import mb.pie.api.PieSession;
import mb.pie.lang.test.util.PieRunner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class addStrDataTestGenTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new TaskDefsModule_addStrDataTestGen(), main_addStrDataTestGen.class, "String + Sign (foreign data type): Sign with value 7");
    }
}