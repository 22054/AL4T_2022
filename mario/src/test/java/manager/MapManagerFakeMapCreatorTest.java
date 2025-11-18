package manager;

import org.junit.jupiter.api.Test;
import testsupport.FakeImageLoader;
import testsupport.FakeMapCreator;
import view.IImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.assertj.core.api.Assertions.assertThat;

public class MapManagerFakeMapCreatorTest {

    @Test
    void createMapWithFakeMapCreatorBuildsPlayableMap() {
        // Arrange: use FakeMapCreator to avoid PNG parsing and UI
        IImageLoader loader = new FakeImageLoader();
        IMapCreator mapCreator = new FakeMapCreator();
        MapManager manager = new MapManager(mapCreator);

        // Act
        boolean ok = manager.createMap(loader, "ignored-by-fake.png");

        // Assert
        assertThat(ok).isTrue();
        assertThat(manager.getMario()).isNotNull();

        // Smoke test drawMap on an offscreen image
        BufferedImage canvas = new BufferedImage(1280, 720, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = canvas.createGraphics();
        manager.drawMap(g2);
        g2.dispose();

        // Time updates are forwarded to map
        int t0 = manager.getRemainingTime();
        manager.updateTime();
        assertThat(manager.getRemainingTime()).isEqualTo(t0 - 1);

        // Mission passing logic works when Mario reaches end flag X
        // FakeMapCreator places EndFlag at x=1000, Mario starts near 200; push Mario to 1000
        manager.getMario().setX(1000);
        int score = manager.passMission();
        assertThat(score).isGreaterThanOrEqualTo(0);
    }
}
