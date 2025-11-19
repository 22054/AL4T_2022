package di;

import dagger.Component;
import javax.inject.Singleton;
import manager.IGameEngine;

@Singleton
@Component(modules = {MarioModule.class})
public interface MarioComponent {
    IGameEngine gameEngine();

    @Component.Factory
    interface Factory {
        MarioComponent create();
    }
}
