package mb.pie.lang.test.funcDef.oneFunc;

import mb.pie.api.*;
import mb.pie.dagger.PieComponent;
import mb.pie.dagger.PieModule;
import mb.pie.runtime.PieBuilderImpl;
import org.junit.jupiter.api.Test;

import java.io.Serializable;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OneFuncTest {
    @Test
    void test() throws Exception {
        None input = None.instance;
        None expectedOutput = None.instance;
        Class<? extends PieComponent> componentClass = DaggeroneFuncComponent.class;

//        Object builder = componentClass.getMethod("builder").invoke(null);
//        builder.getClass().getMethod("pieModule", PieModule.class).invoke(builder, new PieModule(PieBuilderImpl::new));
//        PieComponent component = (PieComponent) builder.getClass().getMethod("build").invoke(builder);

        oneFuncComponent component = DaggeroneFuncComponent.builder().pieModule(new PieModule(PieBuilderImpl::new)).build();
        Pie pie = component.getPie();
        try (MixedSession session = pie.newSession()) {
            final TaskDef<None, None> main = component.get();
//            final TaskDef<None, None> main = (TaskDef<None, None>) component.getClass().getMethod("get").invoke(component);
            final None actualOutput = session.require(main.createTask(input));
            assertEquals(expectedOutput, actualOutput);
        }
    }

    public static <O extends Serializable> O assertTaskOutputEquals(TaskDef<None, O> main, O expectedOutput)
        throws ExecException {
        return assertTaskOutputEquals(main, None.instance, expectedOutput);
    }


    public static <I extends Serializable, O extends Serializable> O assertTaskOutputEquals(
        TaskDef<I, O> main, I input, O expectedOutput) throws ExecException {
        Pie pie = new PieBuilderImpl().build();
        try (MixedSession session = pie.newSession()) {
            final O actualOutput = session.require(main.createTask(input));
            assertEquals(expectedOutput, actualOutput);
            return actualOutput;
        }
    }
}
