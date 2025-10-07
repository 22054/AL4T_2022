package model.brick;

import manager.GameEngine;
import model.GameObject;
import model.IBumpable;
import model.prize.Prize;

import java.awt.image.BufferedImage;

public abstract class Brick extends GameObject implements IBumpable {

    private boolean breakable;

    public Brick(double x, double y, BufferedImage style){
        super(x, y, style);
        setDimension(48, 48);
        setRenderLayer(2);
    }

    // Bricks are static; prevent physics updates so they don't fall through ground
    @Override
    public void updateLocation() {
        // no-op for static bricks
    }

    public boolean isBreakable() {
        return breakable;
    }

    public void setBreakable(boolean breakable) {
        this.breakable = breakable;
    }

    // Legacy API: subclasses may override reveal; default is no prize
    public Prize reveal(GameEngine engine){ return null;}

    // IBumpable: default delegates to legacy reveal
    @Override
    public Prize onHeadBump(GameEngine engine) { return reveal(engine); }

    // IBumpable: bricks are solid by default
    @Override
    public boolean isSolidForCollision() { return true; }

    public Prize getPrize() {
        return null;
    }
}
