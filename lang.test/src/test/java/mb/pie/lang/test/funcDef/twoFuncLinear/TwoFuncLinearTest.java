package mb.pie.lang.test.funcDef.twoFuncLinear;

import mb.pie.api.None;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class TwoFuncLinearTest {
    @Test void test() throws Exception {
        assertTaskOutputEquals(DaggertwoFuncLinearComponent.class, None.instance);
    }
}
