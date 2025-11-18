package model.hero;

import org.junit.jupiter.api.Test;
import testsupport.FakeImageLoader;
import view.Animation;
import view.IImageLoader;

import static org.assertj.core.api.Assertions.assertThat;

public class Unit_test {

    @Test
    void acquireCoinIncrementsByOne() {
        IImageLoader loader = new FakeImageLoader();
        Mario mario = new Mario(1, 2, loader);
        mario.acquireCoin();
        assertThat(mario.getCoins()).isEqualTo(1);
    }

    @Test
    void acquirePointsAccumulates() {
        IImageLoader loader = new FakeImageLoader();
        Mario mario = new Mario(1, 2, loader);
        mario.acquirePoints(10);
        mario.acquirePoints(5);
        assertThat(mario.getPoints()).isEqualTo(15);
    }

    @Test
    void marioFormFlagsReflectState() {
        IImageLoader loader = new FakeImageLoader();
        Animation dummy = new Animation(loader.getLeftFrames(MarioForm.SUPER), loader.getRightFrames(MarioForm.SUPER));
        MarioForm superForm = new MarioForm(dummy, true, false, loader);
        MarioForm fireForm = new MarioForm(dummy, true, true, loader);

        assertThat(superForm.isSuper()).isTrue();
        assertThat(fireForm.isFire()).isTrue();
    }

    @Test
    void isSuperFalseByDefault() {
        IImageLoader loader = new FakeImageLoader();
        Mario mario = new Mario(1, 2, loader);
        assertThat(mario.isSuper()).isFalse();
    }
}

