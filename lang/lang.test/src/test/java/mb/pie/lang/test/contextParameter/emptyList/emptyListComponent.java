package mb.pie.lang.test.contextParameter.emptyList;

import dagger.Component;
import mb.pie.dagger.PieComponent;
import mb.pie.dagger.PieModule;

import javax.inject.Singleton;

@Singleton
@Component(modules = {PieModule.class, PieTestModule.class})
public interface emptyListComponent extends PieComponent {
    main_emptyList get();
}
