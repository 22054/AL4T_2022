package model;

import manager.GameEngine;
import manager.IMapManager;
import model.brick.OrdinaryBrick;
import model.hero.Mario;
import model.prize.Coin;
import org.junit.jupiter.api.Test;
import testsupport.FakeImageLoader;
import view.IImageLoader;
import view.Animation;

import java.awt.image.BufferedImage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class MapLifecycleTest {

    @Test
    void revealedCoinIsRemovedAfterReachingBoundary() {
        IImageLoader loader = new FakeImageLoader();
        BufferedImage sprite = loader.loadImage("/sprite.png");
        Coin coin = new Coin(100, 200, loader.getSubImage(sprite, 1, 5, 48, 48), 10);

        Map map = new Map(100, loader.loadImage("/background.png"));
        map.addRevealedPrize(coin);
        coin.reveal();

        // Step until coin goes above revealBoundary and gets removed by updateLocations
        int guard = 0;
        while (guard++ < 200) {
            map.updateLocations();
            if (!map.getRevealedPrizes().contains(coin)) break;
        }

        assertThat(map.getRevealedPrizes()).doesNotContain(coin);
    }

    @Test
    void breakingOrdinaryBrickIsRemovedAfterAnimationFrames() {
        IImageLoader loader = new FakeImageLoader();
        BufferedImage sprite = loader.loadImage("/sprite.png");
        OrdinaryBrick brick = new OrdinaryBrick(50, 50, loader.getSubImage(sprite, 1, 1, 48, 48), loader);

        Map map = new Map(100, loader.loadImage("/background.png"));
        map.addGroundBrick(brick);

        // Trigger breaking by simulating a super Mario bump through GameEngine/MapManager mocks
        GameEngine engine = mock(GameEngine.class);
        IMapManager manager = mock(IMapManager.class);
        when(engine.getMapManager()).thenReturn(manager);

        Mario mario = new Mario(0, 0, loader);
        // set super form
        mario.setMarioForm(new model.hero.MarioForm(new Animation(loader.getLeftFrames(1), loader.getRightFrames(1)), true, false, loader));
        when(manager.getMario()).thenReturn(mario);

        // onHeadBump returns null but sets internal breaking state and registers revealed brick
        brick.onHeadBump(engine);

        // Step enough times for frames to go below zero and be removed by map.updateLocations
        int guard = 0;
        while (guard++ < 50) {
            map.updateLocations();
            if (!map.getAllBricks().contains(brick)) break;
        }
        assertThat(map.getAllBricks()).doesNotContain(brick);
    }
}
