package mb.pie.lang.test.binary.add.addStrStr;

import dagger.Component;
import mb.pie.dagger.PieComponent;
import mb.pie.dagger.PieModule;

import javax.inject.Singleton;

@Singleton
@Component(modules = {PieModule.class, PieTestModule.class})
public interface addStrStrComponent extends PieComponent {
    main_addStrStr get();
}
