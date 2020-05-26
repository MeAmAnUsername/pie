package mb.pie.lang.test.imports.subModule.importPartialModulePathForeignJava;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ElementsIntoSet;
import mb.pie.api.TaskDef;
import mb.pie.lang.test.imports.subModule.a.b.c.helper_function;

import javax.inject.Singleton;
import java.util.HashSet;
import java.util.Set;

@Module
abstract class PieTestModule {
    @Provides @Singleton @ElementsIntoSet
    public static Set<TaskDef<?, ?>> provideTaskDefs(
        main_importPartialModulePathForeignJava importPartialModulePathForeignJava,
        helper_function helper_function
    ) {
        final HashSet<TaskDef<?, ?>> taskDefs = new HashSet<>(1, 1);
        taskDefs.add(importPartialModulePathForeignJava);
        taskDefs.add(helper_function);
        return taskDefs;
    }
}
