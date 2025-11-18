package manager;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CameraTest {

    @Test
    void shakeCameraOscillatesXFor60Frames() {
        Camera cam = new Camera();
        cam.shakeCamera();
        // Move several frames and ensure x changes direction
        double prev = cam.getX();
        int directionChanges = 0;
        int lastSign = 0;
        boolean anyMoved = false;
        for (int i = 0; i < 10; i++) {
            cam.moveCam(0, 0);
            double dx = cam.getX() - prev;
            if (dx != 0) anyMoved = true;
            int sign = dx == 0 ? 0 : (dx > 0 ? 1 : -1);
            if (lastSign != 0 && sign != 0 && sign != lastSign) directionChanges++;
            if (sign != 0) lastSign = sign;
            prev = cam.getX();
        }
        // We don't assert net displacement (it can cancel out), only that motion occurred
        assertThat(anyMoved).isTrue();
        assertThat(directionChanges).isGreaterThanOrEqualTo(1);
    }

    @Test
    void moveCamAppliesRegularMovementWhenNotShaking() {
        Camera cam = new Camera();
        cam.moveCam(5, 3);
        assertThat(cam.getX()).isEqualTo(5);
        assertThat(cam.getY()).isEqualTo(3);
    }
}
