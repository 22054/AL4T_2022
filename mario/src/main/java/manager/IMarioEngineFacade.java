package manager;

import view.IImageLoader;

/**
 * Narrow interface to decouple Mario from the concrete GameEngine.
 * Exposes only what Mario needs: sounds for jump/death, camera shake, and image loader access.
 */
public interface IMarioEngineFacade {
    void playJump();
    void playMarioDies();
    void shakeCamera();
    IImageLoader getImageLoader();
    void playFireball();
}
