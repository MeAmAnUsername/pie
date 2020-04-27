package mb.pie.lang.test.variables;

import mb.pie.api.ExecException;
import mb.pie.util.Tuple2;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class VariableTupleDecompositionExplicitTypeTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new test_variables_variableTupleDecompositionExplicitType.TaskDefsModule_test_variables_variableTupleDecompositionExplicitType(), test_variables_variableTupleDecompositionExplicitType.main_variableTupleDecompositionExplicitType.class, new test_variables_variableTupleDecompositionExplicitType.main_variableTupleDecompositionExplicitType.Output("out of ideas for string values", new Integer(-11)));
    }
}
