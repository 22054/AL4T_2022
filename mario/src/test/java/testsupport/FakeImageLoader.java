package testsupport;

import view.IImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Deterministic in-memory image loader for tests. Avoids file IO and provides
 * predictable frame arrays and subimages.
 */
public class FakeImageLoader implements IImageLoader {

    private BufferedImage solid(int w, int h, int argb) {
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setColor(new Color(argb, true));
        g2.fillRect(0, 0, w, h);
        g2.dispose();
        return img;
    }

    @Override
    public BufferedImage loadImage(String path) {
        // Return a distinct color per path so tests can assert differences if needed
        int color = 0xFF000000 | Math.abs(path.hashCode()) & 0x00FFFFFF;
        // Keep standard sizes used by production sprites to avoid surprises
        if ("/brick-animation.png".equals(path)) {
            return solid(105 * 4, 105, color);
        }
        if ("/mario-forms.png".equals(path)) {
            // Width enough for 9 columns of varying widths up to 52, height for 5 frames of up to 96
            return solid(52 * 9, 96 * 5, color);
        }
        return solid(256, 256, color);
    }

    @Override
    public BufferedImage getSubImage(BufferedImage image, int col, int row, int w, int h) {
        int x = (col - 1) * w;
        int y = (row - 1) * h;
        // Clamp to bounds in case of synthetic sizes
        x = Math.max(0, Math.min(x, image.getWidth() - w));
        y = Math.max(0, Math.min(y, image.getHeight() - h));
        return image.getSubimage(x, y, Math.min(w, image.getWidth() - x), Math.min(h, image.getHeight() - y));
    }

    @Override
    public BufferedImage[] getLeftFrames(int marioForm) {
        int width = 52, height = 48;
        if (marioForm == 1 || marioForm == 2) { // super or fire
            width = 48; height = 96;
        }
        BufferedImage[] frames = new BufferedImage[5];
        for (int i = 0; i < 5; i++) {
            frames[i] = solid(width, height, 0xFF0000FF - i * 12345);
        }
        return frames;
    }

    @Override
    public BufferedImage[] getRightFrames(int marioForm) {
        int width = 52, height = 48;
        if (marioForm == 1 || marioForm == 2) { // super or fire
            width = 48; height = 96;
        }
        BufferedImage[] frames = new BufferedImage[5];
        for (int i = 0; i < 5; i++) {
            frames[i] = solid(width, height, 0xFF00FF00 - i * 54321);
        }
        return frames;
    }

    @Override
    public BufferedImage[] getBrickFrames() {
        BufferedImage[] frames = new BufferedImage[4];
        for (int i = 0; i < 4; i++) {
            frames[i] = solid(105, 105, 0xFFFFFF00 - i * 1000);
        }
        return frames;
    }
}
