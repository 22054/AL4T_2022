package model;

import model.brick.Brick;
import model.brick.GroundBrick;
import model.brick.OrdinaryBrick;
import model.enemy.Enemy;
import model.hero.Fireball;
import model.hero.Mario;
import model.prize.BoostItem;
import model.prize.Coin;
import model.prize.Prize;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class Map implements IMap {

    private double remainingTime;
    private Mario mario;
    private EndFlag endPoint;
    private final BufferedImage backgroundImage;
    private String path;

    // Unified renderables registry; draw order is derived via a layer comparator
    private final List<IRenderable> renderables = new ArrayList<>();

    // Cached, sorted view; invalidated on mutations
    private List<IRenderable> renderablesInDrawOrder = new ArrayList<>();
    private boolean renderablesDirty = true;


    public Map(double remainingTime, BufferedImage backgroundImage) {
        this.backgroundImage = backgroundImage;
        this.remainingTime = remainingTime;
    }


    @Override
    public Mario getMario() {
        return mario;
    }

    @Override
    public void setMario(Mario mario) {
        // remove previous mario from renderables if any
        if (this.mario != null) {
            renderables.remove(this.mario);
        }
        this.mario = mario;
        if (mario != null) {
            renderables.add(mario);
        }
        this.renderablesDirty = true;
    }

    @Override
    public ArrayList<Enemy> getEnemies() {
        ArrayList<Enemy> list = new ArrayList<>();
        for (IRenderable r : renderables) {
            if (r instanceof Enemy) list.add((Enemy) r);
        }
        return list;
    }

    @Override
    public ArrayList<Fireball> getFireballs() {
        ArrayList<Fireball> list = new ArrayList<>();
        for (IRenderable r : renderables) {
            if (r instanceof Fireball) list.add((Fireball) r);
        }
        return list;
    }

    @Override
    public ArrayList<Prize> getRevealedPrizes() {
        ArrayList<Prize> list = new ArrayList<>();
        for (IRenderable r : renderables) {
            if (r instanceof Prize) list.add((Prize) r);
        }
        return list;
    }

    @Override
    public ArrayList<Brick> getAllBricks() {
        ArrayList<Brick> allBricks = new ArrayList<>();
        for (IRenderable r : renderables) {
            if (r instanceof Brick) allBricks.add((Brick) r);
        }
        return allBricks;
    }

    @Override
    public void addBrick(Brick brick) {
        this.renderables.add(brick);
        this.renderablesDirty = true;
    }

    @Override
    public void addGroundBrick(Brick brick) {
        this.renderables.add(brick);
        this.renderablesDirty = true;
    }

    @Override
    public void addEnemy(Enemy enemy) {
        this.renderables.add(enemy);
        this.renderablesDirty = true;
    }

    @Override
    public void drawMap(Graphics2D g2){
        // Draw static background first
        drawBackground(g2);
        // Draw all renderables in order
        for (IRenderable renderable : getRenderablesInDrawOrder()) {
            if (renderable != null) {
                renderable.draw(g2);
            }
        }
    }

    // Aggregates all renderable entities in the correct drawing order
    private List<IRenderable> getRenderablesInDrawOrder() {
        if (!renderablesDirty && renderablesInDrawOrder != null) {
            return renderablesInDrawOrder;
        }

        // rebuild cache by sorting unified registry based on each object's own layer
        renderablesInDrawOrder = new ArrayList<>(renderables);
        renderablesInDrawOrder.sort(Comparator.comparingInt(IRenderable::getRenderLayer));

        renderablesDirty = false;
        return renderablesInDrawOrder;
    }

    private void drawBackground(Graphics2D g2){
        g2.drawImage(backgroundImage, 0, 0, null);
    }

    @Override
    public void updateLocations() {
        // iterate over a copy to allow safe removals during iteration
        List<IRenderable> snapshot = new ArrayList<>(renderables);
        for (IRenderable r : snapshot) {
            if (r instanceof IPhysical) {
                ((IPhysical) r).updateLocation();
            }

            // lifecycle management for objects that should disappear after animation
            if (r instanceof Coin) {
                Coin c = (Coin) r;
                if (c.getRevealBoundary() > c.getY()) {
                    renderables.remove(r);
                    renderablesDirty = true;
                }
            } else if (r instanceof OrdinaryBrick) {
                OrdinaryBrick ob = (OrdinaryBrick) r;
                ob.animate();
                if (ob.getFrames() < 0) {
                    renderables.remove(r);
                    renderablesDirty = true;
                }
            }
        }

        if (endPoint != null) {
            endPoint.updateLocation();
        }
    }

    @Override
    public double getBottomBorder() {
        return 720 - 96;
    }

    @Override
    public void addRevealedPrize(Prize prize) {
        if (prize instanceof IRenderable) {
            renderables.add((IRenderable) prize);
        }
        this.renderablesDirty = true;
    }

    @Override
    public void addFireball(Fireball fireball) {
        renderables.add(fireball);
        this.renderablesDirty = true;
    }

    @Override
    public void setEndPoint(EndFlag endPoint) {
        // remove previous endpoint from renderables if any
        if (this.endPoint != null) {
            renderables.remove(this.endPoint);
        }
        this.endPoint = endPoint;
        if (endPoint != null) {
            renderables.add(endPoint);
        }
        this.renderablesDirty = true;
    }

    @Override
    public EndFlag getEndPoint() {
        return endPoint;
    }

    @Override
    public void addRevealedBrick(OrdinaryBrick ordinaryBrick) {
        // no dedicated list; brick will animate and be removed when finished in updateLocations
        this.renderablesDirty = true;
    }

    @Override
    public void removeFireball(Fireball object) {
        renderables.remove(object);
        this.renderablesDirty = true;
    }

    @Override
    public void removeEnemy(Enemy object) {
        renderables.remove(object);
        this.renderablesDirty = true;
    }

    @Override
    public void removePrize(Prize object) {
        renderables.remove(object);
        this.renderablesDirty = true;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public void updateTime(double passed){
        remainingTime = remainingTime - passed;
    }

    @Override
    public boolean isTimeOver(){
        return remainingTime <= 0;
    }

    @Override
    public double getRemainingTime() {
        return remainingTime;
    }
}
