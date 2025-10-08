package model;

import model.brick.Brick;
import model.brick.OrdinaryBrick;
import model.enemy.Enemy;
import model.hero.Fireball;
import model.hero.Mario;
import model.prize.Coin;
import model.prize.Prize;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Map implements IMap {

    private double remainingTime;
    private Mario mario;
    private EndFlag endPoint;
    private final BufferedImage backgroundImage;
    private String path;

    // Unified renderables registry; draw order is derived via a layer comparator
    private final List<IRenderable> renderables = new ArrayList<>();

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
    }

    @Override
    public ArrayList<Enemy> getEnemies() {
        ArrayList<Enemy> list = new ArrayList<>();
        for (IRenderable r : new ArrayList<>(renderables)) {
            if (r instanceof Enemy) list.add((Enemy) r);
        }
        return list;
    }

    @Override
    public ArrayList<Fireball> getFireballs() {
        ArrayList<Fireball> list = new ArrayList<>();
        for (IRenderable r : new ArrayList<>(renderables)) {
            if (r instanceof Fireball) list.add((Fireball) r);
        }
        return list;
    }

    @Override
    public ArrayList<Prize> getRevealedPrizes() {
        ArrayList<Prize> list = new ArrayList<>();
        for (IRenderable r : new ArrayList<>(renderables)) {
            if (r instanceof Prize) list.add((Prize) r);
        }
        return list;
    }

    @Override
    public ArrayList<Brick> getAllBricks() {
        ArrayList<Brick> allBricks = new ArrayList<>();
        for (IRenderable r : new ArrayList<>(renderables)) {
            if (r instanceof Brick) allBricks.add((Brick) r);
        }
        return allBricks;
    }

    @Override
    public void addBrick(Brick brick) {
        this.renderables.add(brick);
    }

    @Override
    public void addGroundBrick(Brick brick) {
        this.renderables.add(brick);
    }

    @Override
    public void addEnemy(Enemy enemy) {
        this.renderables.add(enemy);
    }

    @Override
    public void drawMap(Graphics2D g2) {
        // Draw static background first
        drawBackground(g2);
        // Work on a snapshot to avoid ConcurrentModificationException and avoid mutating shared order
        List<IRenderable> toDraw = new ArrayList<>(renderables);
        toDraw.sort(Comparator.comparingInt(IRenderable::getRenderLayer));
        // Draw all renderables in order
        for (IRenderable renderable : toDraw) {
            if (renderable != null) {
                renderable.draw(g2);
            }
        }
    }


    private void drawBackground(Graphics2D g2) {
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
            if (r instanceof Coin c) {
                if (c.getRevealBoundary() > c.getY()) {
                    renderables.remove(r);
                }
            } else if (r instanceof OrdinaryBrick ob) {
                ob.animate();
                if (ob.getFrames() < 0) {
                    renderables.remove(r);
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
    }

    @Override
    public void addFireball(Fireball fireball) {
        renderables.add(fireball);
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
    }

    @Override
    public EndFlag getEndPoint() {
        return endPoint;
    }

    @Override
    public void addRevealedBrick(OrdinaryBrick ordinaryBrick) {
        // no-op: handled via updateLocations lifecycle
    }

    @Override
    public void removeFireball(Fireball object) {
        renderables.remove(object);
    }

    @Override
    public void removeEnemy(Enemy object) {
        renderables.remove(object);
    }

    @Override
    public void removePrize(Prize object) {
        renderables.remove(object);
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
    public void updateTime(double passed) {
        remainingTime = remainingTime - passed;
    }

    @Override
    public boolean isTimeOver() {
        return remainingTime <= 0;
    }

    @Override
    public double getRemainingTime() {
        return remainingTime;
    }
}
