package mb.pie.lang.test.returnTypes;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class ListStringEmptyTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new test_returnTypes_listStringEmpty.TaskDefsModule_test_returnTypes_listStringEmpty(), test_returnTypes_listStringEmpty.main_listStringEmpty.class, new ArrayList<>());
    }
}
