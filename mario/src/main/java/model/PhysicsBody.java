package model;

/**
 * Encapsulates physics state and update logic.
 */
public class PhysicsBody implements IPhysical {
    private double x, y;
    private double velX, velY;
    private double gravityAcc = 0.38;
    private boolean falling = true;
    private boolean jumping = false;

    public PhysicsBody(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void updateLocation() {
        if (jumping && velY <= 0) {
            jumping = false;
            falling = true;
        } else if (jumping) {
            velY = velY - gravityAcc;
            y = y - velY;
        }

        if (falling) {
            y = y + velY;
            velY = velY + gravityAcc;
        }

        x = x + velX;
    }

    @Override
    public double getX() { return x; }

    @Override
    public void setX(double x) { this.x = x; }

    @Override
    public double getY() { return y; }

    @Override
    public void setY(double y) { this.y = y; }

    @Override
    public double getVelX() { return velX; }

    @Override
    public void setVelX(double velX) { this.velX = velX; }

    @Override
    public double getVelY() { return velY; }

    @Override
    public void setVelY(double velY) { this.velY = velY; }

    @Override
    public double getGravityAcc() { return gravityAcc; }

    @Override
    public void setGravityAcc(double gravityAcc) { this.gravityAcc = gravityAcc; }

    @Override
    public boolean isFalling() { return falling; }

    @Override
    public void setFalling(boolean falling) { this.falling = falling; }

    @Override
    public boolean isJumping() { return jumping; }

    @Override
    public void setJumping(boolean jumping) { this.jumping = jumping; }
}
