package model.hero;
import org.junit.Test;
import view.Animation;
import view.ImageLoader;
import view.IImageLoader;
import static org.junit.Assert.*;
public class Unit_test {
    /*Arrange*/
    int point = 10;
    Animation animation;
    IImageLoader loader = new ImageLoader();
    MarioForm marioFormSuper = new MarioForm(animation,true,false, loader);
    MarioForm marioFormFire = new MarioForm(animation,false,true, loader);
    @Test
    public void acquireCoin() {
        Mario test = new Mario(1,2, loader);
        /*Act*/
        test.acquireCoin();
        int value = test.getCoins();
        /*Assert*/
        assertEquals(1,value);
    }


    @Test
    public void acquirePoints() {
        Mario test = new Mario(1,2, loader);
        test.acquirePoints(point);
        int value = test.getPoints();
        assertEquals(10,value);
    }

    @Test
    public void GetMarioFormissuper() {
        Mario test = new Mario(1,2, loader);
        test.setMarioForm(marioFormSuper);
        boolean value = marioFormSuper.isSuper();
        assertTrue(value);
    }
    @Test
    public void GetMarioFormisfire() {
        Mario test = new Mario(1,2, loader);
        test.setMarioForm(marioFormFire);
        boolean value = marioFormFire.isFire();
        assertTrue(value);
    }
    @Test
    public void isSuper(){
        Mario test = new Mario(1,2, loader);
        boolean value = test.isSuper();
        assertFalse(value);
    }




}

