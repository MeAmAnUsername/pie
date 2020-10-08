package mb.pie.lang.test.contextParameter.twoParams;

import dagger.Component;
import mb.pie.dagger.PieComponent;
import mb.pie.dagger.PieModule;

import javax.inject.Singleton;

@Singleton
@Component(modules = {PieModule.class, PieTestModule.class})
public interface twoParamsComponent extends PieComponent {
    main_twoParams get();
}
