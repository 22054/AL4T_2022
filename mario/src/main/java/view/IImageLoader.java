package view;

import java.awt.image.BufferedImage;

public interface IImageLoader {
    BufferedImage loadImage(String path);

    BufferedImage getSubImage(BufferedImage image, int col, int row, int w, int h);

    BufferedImage[] getLeftFrames(int marioForm);

    BufferedImage[] getRightFrames(int marioForm);

    BufferedImage[] getBrickFrames();
}
