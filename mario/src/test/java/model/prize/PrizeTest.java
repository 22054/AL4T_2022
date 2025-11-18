package model.prize;

import manager.GameEngine;
import model.hero.Mario;
import org.junit.jupiter.api.Test;
import testsupport.FakeImageLoader;
import view.IImageLoader;

import java.awt.image.BufferedImage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class PrizeTest {

    @Test
    void coinOnTouchAddsPointsAndCoinAndPlaysSoundOnce() {
        IImageLoader loader = new FakeImageLoader();
        BufferedImage coinStyle = loader.getSubImage(loader.loadImage("/sprite.png"), 1, 1, 24,24);
        Coin coin = new Coin(0, 0, coinStyle, 50);
        GameEngine engine = mock(GameEngine.class);
        Mario mario = new Mario(0, 0, loader);

        // reveal and move once
        coin.reveal();
        int y0 = (int) coin.getY();
        coin.updateLocation();
        assertThat(coin.getY()).isLessThan(y0);

        // touch twice -> should only count once
        coin.onTouch(mario, engine);
        coin.onTouch(mario, engine);

        assertThat(mario.getPoints()).isEqualTo(50);
        assertThat(mario.getCoins()).isEqualTo(1);
        verify(engine, times(1)).playCoin();
    }

    @Test
    void superMushroomUpgradesToSuperAndPlaysSound() {
        IImageLoader loader = new FakeImageLoader();
        BufferedImage style = loader.getSubImage(loader.loadImage("/sprite.png"), 1,1,48,48);
        SuperMushroom mush = new SuperMushroom(0, 0, style);
        GameEngine engine = mock(GameEngine.class);
        when(engine.getImageLoader()).thenReturn(loader);
        Mario mario = new Mario(0, 0, loader);

        mush.onTouch(mario, engine);

        assertThat(mario.getMarioForm().isSuper()).isTrue();
        assertThat(mario.getDimension().height).isEqualTo(96);
        verify(engine).playSuperMushroom();
    }

    @Test
    void fireFlowerUpgradesToFireAndPlaysSound() {
        IImageLoader loader = new FakeImageLoader();
        BufferedImage style = loader.getSubImage(loader.loadImage("/sprite.png"), 1,1,48,48);
        FireFlower flower = new FireFlower(0, 0, style);
        GameEngine engine = mock(GameEngine.class);
        when(engine.getImageLoader()).thenReturn(loader);
        Mario mario = new Mario(0, 0, loader);

        flower.onTouch(mario, engine);

        assertThat(mario.getMarioForm().isFire()).isTrue();
        assertThat(mario.getDimension().height).isEqualTo(96);
        verify(engine).playFireFlower();
    }
}
