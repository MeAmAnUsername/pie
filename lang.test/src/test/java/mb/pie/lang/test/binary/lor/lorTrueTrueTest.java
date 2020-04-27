package mb.pie.lang.test.binary.lor;

import mb.pie.api.ExecException;
import mb.pie.api.None;
import mb.pie.api.PieSession;
import mb.pie.lang.test.util.PieRunner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class lorTrueTrueTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new test_binary_lor_lorTrueTrue.TaskDefsModule_test_binary_lor_lorTrueTrue(), test_binary_lor_lorTrueTrue.main_lorTrueTrue.class, new Boolean(true));
    }
}
