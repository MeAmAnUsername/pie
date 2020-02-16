package mb.pie.lang.test.variables;

import mb.pie.api.ExecException;
import mb.pie.util.Tuple2;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class VariableTupleDecompositionMixedTypeTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new variableTupleDecompositionMixedTypeTestGen.TaskDefsModule_variableTupleDecompositionMixedTypeTestGen(), variableTupleDecompositionMixedTypeTestGen.main_variableTupleDecompositionMixedType.class, new variableTupleDecompositionMixedTypeTestGen.main_variableTupleDecompositionMixedType.Output(new Boolean(true), "implicitly typed string"));
    }
}