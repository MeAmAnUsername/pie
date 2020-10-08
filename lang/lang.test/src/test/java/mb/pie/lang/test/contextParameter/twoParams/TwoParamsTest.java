package mb.pie.lang.test.contextParameter.twoParams;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class TwoParamsTest {
    @Test void test() throws ExecException {
         assertTaskOutputEquals(DaggertwoParamsComponent.class, "There have been no accidents for 0 days!");
    }
}
