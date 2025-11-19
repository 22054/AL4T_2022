package di;

import dagger.Binds;
import dagger.Module;
import javax.inject.Singleton;

import manager.*;
import view.IImageLoader;
import view.ImageLoader;

@Module
public interface MarioModule {

    @Binds
    @Singleton
    ICamera bindCamera(Camera impl);

    @Binds
    @Singleton
    ISoundManager bindSoundManager(SoundManager impl);

    @Binds
    @Singleton
    IImageLoader bindImageLoader(ImageLoader impl);

    @Binds
    @Singleton
    IMapCreator bindMapCreator(MapCreator impl);

    @Binds
    @Singleton
    IMapManager bindMapManager(MapManager impl);

    @Binds
    @Singleton
    IMarioEngineFacade bindMarioEngineFacade(GameEngine impl);

    @Binds
    @Singleton
    IGameEngine bindGameEngine(GameEngine impl);
}
