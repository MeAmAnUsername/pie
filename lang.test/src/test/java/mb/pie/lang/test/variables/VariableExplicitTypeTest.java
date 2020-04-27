package mb.pie.lang.test.variables;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class VariableExplicitTypeTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new test_variables_variableExplicitType.TaskDefsModule_test_variables_variableExplicitType(), test_variables_variableExplicitType.main_variableExplicitType.class, "Greg ate a fish");
    }
}
