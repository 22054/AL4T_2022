package testsupport;

import manager.IMapCreator;
import model.EndFlag;
import model.IMap;
import model.Map;
import model.brick.GroundBrick;
import model.hero.Mario;
import view.IImageLoader;

import java.awt.image.BufferedImage;

/**
 * Simple map creator for tests that avoids image parsing. Creates a tiny map with
 * a background, a Mario at (x,y), an end flag, and optional ground bricks.
 */
public class FakeMapCreator implements IMapCreator {

    private IImageLoader loader;

    @Override
    public void setMapCreator(IImageLoader imageLoader) {
        this.loader = imageLoader;
    }

    @Override
    public IMap createMap(String mapPath, double timeLimit) {
        BufferedImage bg = loader.loadImage("/background.png");
        Map map = new Map(timeLimit, bg);
        map.setPath("fake-map.png");

        // Place Mario somewhere above ground by default
        Mario mario = new Mario(200, 500, loader);
        map.setMario(mario);

        // Add an EndFlag so MapManager logic relying on it doesn't NPE
        map.setEndPoint(new EndFlag(1000, 100, loader.getSubImage(loader.loadImage("/sprite.png"), 5,1,48,48)));

        // Add a ground brick platform at y = bottomBorder - 48
        double y = map.getBottomBorder() - 48;
        GroundBrick ground = new GroundBrick(200, y, loader.getSubImage(loader.loadImage("/sprite.png"), 2,2,48,48));
        map.addGroundBrick(ground);

        return map;
    }
}
