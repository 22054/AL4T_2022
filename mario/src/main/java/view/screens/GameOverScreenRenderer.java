package view.screens;

import manager.IGameEngine;
import view.UIManager;
import view.ImageResourceManager;

import java.awt.*;

public class GameOverScreenRenderer implements ScreenRenderer {

    private final UIManager uiManager;
    private final IGameEngine engine;

    public GameOverScreenRenderer(UIManager uiManager, IGameEngine engine) {
        this.uiManager = uiManager;
        this.engine = engine;
    }

    @Override
    public void render(Graphics2D g2) {
        ImageResourceManager irm = uiManager.getImageResourceManager();

        g2.drawImage(irm.getGameOverScreen(), 0, 0, null);
        g2.setFont(uiManager.getGameFont().deriveFont(50f));
        g2.setColor(new Color(130, 48, 48));
        String acquiredPoints = "Score: " + engine.getScore();
        int stringLength = g2.getFontMetrics().stringWidth(acquiredPoints);
        int stringHeight = g2.getFontMetrics().getHeight();
        g2.drawString(acquiredPoints, (uiManager.getWidth()-stringLength)/2, uiManager.getHeight()-stringHeight*2);
    }
}