package mb.pie.lang.test.contextParameter.noList;

import dagger.Component;
import mb.pie.dagger.PieComponent;
import mb.pie.dagger.PieModule;

import javax.inject.Singleton;

@Singleton
@Component(modules = {PieModule.class, PieTestModule.class})
public interface noListComponent extends PieComponent {
    main_noList get();
}
