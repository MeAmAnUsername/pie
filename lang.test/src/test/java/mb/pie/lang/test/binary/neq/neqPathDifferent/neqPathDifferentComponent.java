package mb.pie.lang.test.binary.neq.neqPathDifferent;

import dagger.Component;
import mb.pie.dagger.PieComponent;
import mb.pie.dagger.PieModule;

import javax.inject.Singleton;

@Singleton
@Component(modules = {PieModule.class, PieTestModule.class})
public interface neqPathDifferentComponent extends PieComponent {
    main_neqPathDifferent get();
}
