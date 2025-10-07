package model;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject implements IRenderable, IPhysical {

    private final PhysicsBody physics;
    private final SpriteRenderer renderer;
    private Dimension dimension;

    public GameObject(double x, double y, BufferedImage style){
        this.physics = new PhysicsBody(x, y);
        this.renderer = new SpriteRenderer(new SpriteRenderer.PositionProvider() {
            @Override
            public double getX() { return physics.getX(); }
            @Override
            public double getY() { return physics.getY(); }
        }, style);

        if(style != null){
            setDimension(style.getWidth(), style.getHeight());
        }

        setVelX(0);
        setVelY(0);
        setGravityAcc(0.38);
        setJumping(false);
        setFalling(true);
    }

    @Override
    public void draw(Graphics g) {
        renderer.draw(g);

        //for debugging
        /*Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.WHITE);

        g2.draw(getTopBounds());
        g2.draw(getBottomBounds());
        g2.draw(getRightBounds());
        g2.draw(getLeftBounds());*/
    }

    @Override
    public void updateLocation() {
        physics.updateLocation();
    }

    public void setLocation(double x, double y) {
        setX(x);
        setY(y);
    }

    @Override
    public double getX() {
        return physics.getX();
    }

    @Override
    public void setX(double x) {
        physics.setX(x);
    }

    @Override
    public double getY() {
        return physics.getY();
    }

    @Override
    public void setY(double y) {
        physics.setY(y);
    }

    public Dimension getDimension(){
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public void setDimension(int width, int height){ this.dimension =  new Dimension(width, height); }

    @Override
    public BufferedImage getStyle() {
        return renderer.getStyle();
    }

    @Override
    public void setStyle(BufferedImage style) {
        renderer.setStyle(style);
    }

    @Override
    public double getVelX() {
        return physics.getVelX();
    }

    @Override
    public void setVelX(double velX) {
        physics.setVelX(velX);
    }

    @Override
    public double getVelY() {
        return physics.getVelY();
    }

    @Override
    public void setVelY(double velY) {
        physics.setVelY(velY);
    }

    @Override
    public double getGravityAcc() {
        return physics.getGravityAcc();
    }

    @Override
    public void setGravityAcc(double gravityAcc) {
        physics.setGravityAcc(gravityAcc);
    }

    public Rectangle getTopBounds(){
        return new Rectangle((int)getX()+dimension.width/6, (int)getY(), 2*dimension.width/3, dimension.height/2);
    }

    public Rectangle getBottomBounds(){
        return new Rectangle((int)getX()+dimension.width/6, (int)getY() + dimension.height/2, 2*dimension.width/3, dimension.height/2);
    }

    public Rectangle getLeftBounds(){
        return new Rectangle((int)getX(), (int)getY() + dimension.height/4, dimension.width/4, dimension.height/2);
    }

    public Rectangle getRightBounds(){
        return new Rectangle((int)getX() + 3*dimension.width/4, (int)getY() + dimension.height/4, dimension.width/4, dimension.height/2);
    }

    public Rectangle getBounds(){
        return new Rectangle((int)getX(), (int)getY(), dimension.width, dimension.height);
    }

    @Override
    public boolean isFalling() {
        return physics.isFalling();
    }

    @Override
    public void setFalling(boolean falling) {
        physics.setFalling(falling);
    }

    @Override
    public boolean isJumping() {
        return physics.isJumping();
    }

    @Override
    public void setJumping(boolean jumping) {
        physics.setJumping(jumping);
    }
}
