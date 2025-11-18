package model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PhysicsBodyTest {

    @Test
    void jumpingThenFallingTransitionsCorrectly() {
        PhysicsBody body = new PhysicsBody(0, 100);
        body.setVelY(10);
        body.setJumping(true);
        body.setFalling(false);

        // simulate several ticks
        for (int i = 0; i < 50; i++) {
            body.updateLocation();
        }

        // After upward velocity depletes, it should begin falling
        assertThat(body.isJumping()).isFalse();
        assertThat(body.isFalling()).isTrue();
    }

    @Test
    void horizontalVelocityAffectsX() {
        PhysicsBody body = new PhysicsBody(0, 0);
        body.setVelX(2);
        double x0 = body.getX();
        body.updateLocation();
        assertThat(body.getX()).isEqualTo(x0 + 2);
    }
}
