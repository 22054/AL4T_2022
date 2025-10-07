package model;

import model.brick.Brick;
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
import java.util.Iterator;
import java.util.List;

public class Map implements IMap {

    private double remainingTime;
    private Mario mario;
    private final ArrayList<Brick> bricks = new ArrayList<>();
    private final ArrayList<Enemy> enemies = new ArrayList<>();
    private final ArrayList<Brick> groundBricks = new ArrayList<>();
    private final ArrayList<Prize> revealedPrizes = new ArrayList<>();
    private final ArrayList<Brick> revealedBricks = new ArrayList<>();
    private final ArrayList<Fireball> fireballs = new ArrayList<>();
    private EndFlag endPoint;
    private final BufferedImage backgroundImage;
    private String path;

    // Cached renderables list to avoid rebuilding each frame; invalidated on mutations
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
        this.mario = mario;
        this.renderablesDirty = true;
    }

    @Override
    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    @Override
    public ArrayList<Fireball> getFireballs() {
        return fireballs;
    }

    @Override
    public ArrayList<Prize> getRevealedPrizes() {
        return revealedPrizes;
    }

    @Override
    public ArrayList<Brick> getAllBricks() {
        ArrayList<Brick> allBricks = new ArrayList<>();

        allBricks.addAll(bricks);
        allBricks.addAll(groundBricks);

        return allBricks;
    }

    @Override
    public void addBrick(Brick brick) {
        this.bricks.add(brick);
        this.renderablesDirty = true;
    }

    @Override
    public void addGroundBrick(Brick brick) {
        this.groundBricks.add(brick);
        this.renderablesDirty = true;
    }

    @Override
    public void addEnemy(Enemy enemy) {
        this.enemies.add(enemy);
        this.renderablesDirty = true;
    }

    @Override
    public void drawMap(Graphics2D g2){
        // Draw static background first
        drawBackground(g2);
        // Draw all renderables in order, respecting layering without type checks (OCP)
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

        // rebuild cache
        renderablesInDrawOrder = new ArrayList<>();
        // 1) Prizes (revealed)
        for (Prize prize : revealedPrizes) {
            if (prize instanceof IRenderable) {
                renderablesInDrawOrder.add((IRenderable) prize);
            }
        }
        // 2) Bricks (non-ground first), then ground bricks
        renderablesInDrawOrder.addAll(bricks);
        renderablesInDrawOrder.addAll(groundBricks);
        // 3) Enemies
        renderablesInDrawOrder.addAll(enemies);
        // 4) Fireballs
        renderablesInDrawOrder.addAll(fireballs);
        // 5) Mario
        if (mario != null) {
            renderablesInDrawOrder.add(mario);
        }
        // 6) End flag
        if (endPoint != null) {
            renderablesInDrawOrder.add(endPoint);
        }

        renderablesDirty = false;
        return renderablesInDrawOrder;
    }

    private void drawBackground(Graphics2D g2){
        g2.drawImage(backgroundImage, 0, 0, null);
    }

    @Override
    public void updateLocations() {
        mario.updateLocation();
        for(Enemy enemy : enemies){
            enemy.updateLocation();
        }

        for(Iterator<Prize> prizeIterator = revealedPrizes.iterator(); prizeIterator.hasNext();){
            Prize prize = prizeIterator.next();
            if(prize instanceof Coin){
                ((Coin) prize).updateLocation();
                if(((Coin) prize).getRevealBoundary() > ((Coin) prize).getY()){
                    prizeIterator.remove();
                    this.renderablesDirty = true;
                }
            }
            else if(prize instanceof BoostItem){
                ((BoostItem) prize).updateLocation();
            }
        }

        for (Fireball fireball: fireballs) {
            fireball.updateLocation();
        }

        for(Iterator<Brick> brickIterator = revealedBricks.iterator(); brickIterator.hasNext();){
            OrdinaryBrick brick = (OrdinaryBrick)brickIterator.next();
            brick.animate();
            if(brick.getFrames() < 0){
                bricks.remove(brick);
                brickIterator.remove();
                this.renderablesDirty = true;
            }
        }

        endPoint.updateLocation();
    }

    @Override
    public double getBottomBorder() {
        return 720 - 96;
    }

    @Override
    public void addRevealedPrize(Prize prize) {
        revealedPrizes.add(prize);
        this.renderablesDirty = true;
    }

    @Override
    public void addFireball(Fireball fireball) {
        fireballs.add(fireball);
        this.renderablesDirty = true;
    }

    @Override
    public void setEndPoint(EndFlag endPoint) {
        this.endPoint = endPoint;
        this.renderablesDirty = true;
    }

    @Override
    public EndFlag getEndPoint() {
        return endPoint;
    }

    @Override
    public void addRevealedBrick(OrdinaryBrick ordinaryBrick) {
        revealedBricks.add(ordinaryBrick);
    }

    @Override
    public void removeFireball(Fireball object) {
        fireballs.remove(object);
        this.renderablesDirty = true;
    }

    @Override
    public void removeEnemy(Enemy object) {
        enemies.remove(object);
        this.renderablesDirty = true;
    }

    @Override
    public void removePrize(Prize object) {
        revealedPrizes.remove(object);
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
