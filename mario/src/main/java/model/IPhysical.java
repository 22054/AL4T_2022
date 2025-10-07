package model;

/**
 * Physics responsibility abstraction for game objects.
 */
public interface IPhysical {
    void updateLocation();

    double getX();
    void setX(double x);
    double getY();
    void setY(double y);

    double getVelX();
    void setVelX(double velX);
    double getVelY();
    void setVelY(double velY);

    double getGravityAcc();
    void setGravityAcc(double gravityAcc);

    boolean isFalling();
    void setFalling(boolean falling);
    boolean isJumping();
    void setJumping(boolean jumping);
}
