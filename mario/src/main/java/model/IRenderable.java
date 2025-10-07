package model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * Rendering responsibility abstraction for game objects.
 */
public interface IRenderable {
    void draw(Graphics g);
    BufferedImage getStyle();
    void setStyle(BufferedImage style);

    // Render layer for draw ordering. Lower numbers are drawn earlier (behind).
    default int getRenderLayer() { return 100; }
}
