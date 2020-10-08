package mb.pie.lang.test.contextParameter.oneParam;

import dagger.Component;
import mb.pie.dagger.PieComponent;
import mb.pie.dagger.PieModule;

import javax.inject.Singleton;

@Singleton
@Component(modules = {PieModule.class, PieTestModule.class})
public interface oneParamComponent extends PieComponent {
    main_oneParam get();
}
