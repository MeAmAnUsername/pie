package mb.pie.lang.test.returnTypes;

import mb.pie.api.ExecException;
import mb.pie.util.Tuple2;
import org.junit.jupiter.api.Test;

import static mb.pie.lang.test.util.SimpleChecker.assertTaskOutputEquals;

class TupleIntIntTest {
    @Test void test() throws ExecException {
        assertTaskOutputEquals(new test_returnTypes_tupleIntInt.TaskDefsModule_test_returnTypes_tupleIntInt(), test_returnTypes_tupleIntInt.main_tupleIntInt.class, new test_returnTypes_tupleIntInt.main_tupleIntInt.Output(4, -90));
    }
}
