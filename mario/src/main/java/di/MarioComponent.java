package di;

import dagger.BindsInstance;
import dagger.Component;
import javax.inject.Singleton;
import manager.IGameEngine;

@Singleton
@Component(modules = {MarioModule.class})
public interface MarioComponent {
    IGameEngine gameEngine();

    @Component.Builder
    interface Builder {
        MarioComponent build();
        @BindsInstance
        Builder screenWidth(@ScreenWidth int width);
        @BindsInstance
        Builder screenHeight(@ScreenHeight int height);
    }
}
