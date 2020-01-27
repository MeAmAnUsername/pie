package mb.pie.lang.test.variables;

import mb.pie.api.ExecException;
import mb.pie.util.Tuple2;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class VariableTupleAssignmentTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new variableTupleAssignmentTestGen.TaskDefsModule_variableTupleAssignmentTestGen(), variableTupleAssignmentTestGen.main_variableTupleAssignment.class, new variableTupleAssignmentTestGen.main_variableTupleAssignment.Output(new Integer(2), new ArrayList<>(Arrays.asList(new Boolean(true), new Boolean(false)))));
    }
}
