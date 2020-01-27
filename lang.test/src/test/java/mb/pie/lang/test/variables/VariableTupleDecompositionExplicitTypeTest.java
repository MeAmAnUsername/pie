package mb.pie.lang.test.variables;

import mb.pie.api.ExecException;
import mb.pie.util.Tuple2;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class VariableTupleDecompositionExplicitTypeTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new variableTupleDecompositionExplicitTypeTestGen.TaskDefsModule_variableTupleDecompositionExplicitTypeTestGen(), variableTupleDecompositionExplicitTypeTestGen.main_variableTupleDecompositionExplicitType.class, new variableTupleDecompositionExplicitTypeTestGen.main_variableTupleDecompositionExplicitType.Output("out of ideas for string values", new Integer(-11)));
    }
}
