package di;

import dagger.Component;
import javax.inject.Singleton;
import manager.GameEngine;

@Singleton
@Component(modules = {MarioModule.class})
public interface MarioComponent {
    GameEngine gameEngine();

    @Component.Factory
    interface Factory {
        MarioComponent create();
    }
}
