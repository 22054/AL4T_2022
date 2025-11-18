package model.brick;

import manager.GameEngine;
import model.prize.Prize;
import org.junit.jupiter.api.Test;
import testsupport.FakeImageLoader;
import view.IImageLoader;

import java.awt.image.BufferedImage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class SurpriseBrickTest {

    @Test
    void revealChangesStyleRevealsPrizeAndClearsIt() {
        IImageLoader loader = new FakeImageLoader();
        BufferedImage sprite = loader.loadImage("/sprite.png");
        BufferedImage initial = loader.getSubImage(sprite, 2, 1, 48, 48);
        Prize prize = mock(Prize.class);
        SurpriseBrick brick = new SurpriseBrick(10, 10, initial, prize);

        GameEngine engine = mock(GameEngine.class);
        when(engine.getImageLoader()).thenReturn(loader);

        BufferedImage before = brick.getStyle();
        Prize returned = brick.reveal(engine);
        BufferedImage after = brick.getStyle();

        // Prize is returned and internal prize cleared
        assertThat(returned).isSameAs(prize);
        assertThat(brick.getPrize()).isNull();

        // Style should be updated to a different image as per implementation (1,2)
        assertThat(after).isNotSameAs(before);

        // Prize.reveal() should be called once if non-null
        verify(prize, times(1)).reveal();
    }
}
