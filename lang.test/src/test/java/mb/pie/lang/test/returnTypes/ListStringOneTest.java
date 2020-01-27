package mb.pie.lang.test.returnTypes;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class ListStringOneTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new listStringOneTestGen.TaskDefsModule_listStringOneTestGen(), listStringOneTestGen.main_listStringOne.class, new ArrayList<>(Arrays.asList("first")));
    }
}
