package manager;

import view.IImageLoader;

/**
 * Minimal interface for UI/input/resource managers to interact with the engine
 * without depending on the concrete GameEngine implementation.
 */
public interface IGameEngine {
    GameStatus getGameStatus();
    IImageLoader getImageLoader();

    // Input routing to UI without exposing UIManager directly
    void receiveInput(ButtonAction action);
    void selectMapViaMouse();
}
