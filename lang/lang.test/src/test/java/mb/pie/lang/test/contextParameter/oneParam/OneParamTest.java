package mb.pie.lang.test.contextParameter.oneParam;

import mb.pie.api.ExecException;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class OneParamTest {
    @Test void test() throws ExecException {
         assertTaskOutputEquals(DaggeroneParamComponent.class, "injected string");
    }
}
