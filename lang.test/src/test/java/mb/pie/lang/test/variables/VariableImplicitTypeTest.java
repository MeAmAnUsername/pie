package mb.pie.lang.test.variables;

import mb.pie.api.ExecException;
import mb.pie.api.None;
import mb.pie.api.PieSession;
import mb.pie.lang.test.util.PieRunner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VariableImplicitTypeTest {
    @Test void test() throws ExecException {
        final main_variableImplicitType main = new main_variableImplicitType();
        final PieRunner pieRunner = new PieRunner(main);
        try(PieSession session = pieRunner.newSession()) {
            final Integer output = session.require(main.createTask(None.instance));
            assertEquals(new Integer(8), output);
        }
    }
}