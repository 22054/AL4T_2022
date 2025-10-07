package model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * Encapsulates image-based rendering at a given position provider.
 */
public class SpriteRenderer {

    public interface PositionProvider {
        double getX();
        double getY();
    }

    private final PositionProvider positionProvider;
    private BufferedImage style;

    public SpriteRenderer(PositionProvider positionProvider, BufferedImage initialStyle) {
        this.positionProvider = positionProvider;
        this.style = initialStyle;
    }

    public void draw(Graphics g) {
        if (style != null) {
            g.drawImage(style, (int) positionProvider.getX(), (int) positionProvider.getY(), null);
        }
    }

    public BufferedImage getStyle() {
        return style;
    }

    public void setStyle(BufferedImage style) {
        this.style = style;
    }
}
