package mb.pie.lang.test.binary.neq.neqBoolDifferent;

import dagger.Component;
import mb.pie.dagger.PieComponent;
import mb.pie.dagger.PieModule;

import javax.inject.Singleton;

@Singleton
@Component(modules = {PieModule.class, PieTestModule.class})
public interface neqBoolDifferentComponent extends PieComponent {
    main_neqBoolDifferent get();
}
