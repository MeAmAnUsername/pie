package mb.pie.lang.test.contextParameter.noList;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class NoListTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(DaggernoListComponent.class, true);
    }
}
