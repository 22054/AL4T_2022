package model.brick;

import manager.GameEngine;
import manager.IMapManager;
import model.prize.Prize;
import view.Animation;
import view.IImageLoader;

import java.awt.image.BufferedImage;

public class OrdinaryBrick extends Brick {

    private Animation animation;
    private boolean breaking;
    private int frames;

    public OrdinaryBrick(double x, double y, BufferedImage style, IImageLoader imageLoader){
        super(x, y, style);
        setBreakable(true);
        setEmpty(true);

        setAnimation(imageLoader);
        breaking = false;
        frames = animation.getLeftFrames().length;
    }

    private void setAnimation(IImageLoader imageLoader){
        BufferedImage[] leftFrames = imageLoader.getBrickFrames();
        animation = new Animation(leftFrames, leftFrames);
    }

    @Override
    public Prize reveal(GameEngine engine){
        return onHeadBump(engine);
    }

    @Override
    public Prize onHeadBump(GameEngine engine){
        IMapManager manager = engine.getMapManager();
        if(!manager.getMario().isSuper())
            return null;

        breaking = true;
        manager.addRevealedBrick(this);

        double newX = getX() - 27, newY = getY() - 27;
        setLocation(newX, newY);

        return null;
    }

    @Override
    public boolean isSolidForCollision() { return !breaking; }

    public int getFrames(){
        return frames;
    }

    public void animate(){
        if(breaking){
            setStyle(animation.animate(3, true));
            frames--;
        }
    }
}
