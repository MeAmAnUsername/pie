package mb.pie.lang.test.contextParameter.emptyList;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class EmptyListTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(DaggeremptyListComponent.class, 8);
    }
}
