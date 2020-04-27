package mb.pie.lang.test.variables;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class VariableImplicitTypeTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new test_variables_variableImplicitType.TaskDefsModule_test_variables_variableImplicitType(), test_variables_variableImplicitType.main_variableImplicitType.class, new Integer(8));
    }
}
