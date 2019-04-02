package mb.pie.dagger;

import dagger.Component;
import mb.pie.api.Pie;

@Component(modules = PieModule.class, dependencies = {TaskDefsComponent.class, ResourceSystemsComponent.class})
public interface PieComponent {
    Pie getPie();
}
