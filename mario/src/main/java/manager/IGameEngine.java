package manager;

import view.IImageLoader;

import java.awt.*;

/**
 * Minimal interface for UI/input/resource managers to interact with the engine
 * without depending on the concrete GameEngine implementation.
 */
public interface IGameEngine extends IMarioEngineFacade {
    void start();
    GameStatus getGameStatus();
    void setGameStatus(GameStatus status);

    // High-level game flow controls
    void startGame();
    void pauseGame();
    void reset();
    void createMap(String path);

    IImageLoader getImageLoader();

    // Input routing to UI without exposing UIManager directly
    void receiveInput(ButtonAction action);
    void selectMapViaMouse();

    // HUD data
    int getRemainingLives();
    int getCoins();
    int getRemainingTime();
    int getScore();

    // Rendering helpers
    Point getCameraLocation();
    void drawMap(Graphics2D g2);

    // Access to sub-systems when needed by UI
    IMapManager getMapManager();
    ICamera getCamera();
}
