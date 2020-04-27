package mb.pie.lang.test.binary.lor;

import mb.pie.api.ExecException;
import mb.pie.api.None;
import mb.pie.api.PieSession;
import mb.pie.lang.test.util.PieRunner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class lorFalseTrueTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new test_binary_lor_lorFalseTrue.TaskDefsModule_test_binary_lor_lorFalseTrue(), test_binary_lor_lorFalseTrue.main_lorFalseTrue.class, new Boolean(true));
    }
}
