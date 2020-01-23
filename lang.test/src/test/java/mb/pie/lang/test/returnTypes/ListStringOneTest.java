package mb.pie.lang.test.returnTypes;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class ListStringOneTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new test_returnTypes_listStringOne.TaskDefsModule_test_returnTypes_listStringOne(), test_returnTypes_listStringOne.main_listStringOne.class, new ArrayList<>(Arrays.asList("first")));
    }
}
