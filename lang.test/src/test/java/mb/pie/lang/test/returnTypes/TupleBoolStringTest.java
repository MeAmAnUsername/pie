package mb.pie.lang.test.returnTypes;

import mb.pie.api.ExecException;
import mb.pie.api.None;
import mb.pie.api.PieSession;
import mb.pie.lang.test.util.PieRunner;
import mb.pie.util.Tuple2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TupleBoolStringTest {
    @Test void test() throws ExecException {
        final main_tupleBoolString main = new main_tupleBoolString();
        final PieRunner pieRunner = new PieRunner(main);
        try(PieSession session = pieRunner.newSession()) {
            final Tuple2<Boolean, String> output = session.require(main.createTask(None.instance));
            assertEquals(new Tuple2<>(false, "hey"), output);
        }
    }
}