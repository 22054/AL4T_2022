package manager;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static org.mockito.Mockito.*;

public class InputManagerTest {

    @Test
    void arrowKeysMapToActionsDependingOnStatus() {
        IGameEngine engine = mock(IGameEngine.class);
        InputManager input = new InputManager(engine);

        when(engine.getGameStatus()).thenReturn(GameStatus.START_SCREEN);
        input.keyPressed(new KeyEvent(new Canvas(), 0, 0, 0, KeyEvent.VK_UP, ' '));
        verify(engine).receiveInput(ButtonAction.GO_UP);

        reset(engine);
        when(engine.getGameStatus()).thenReturn(GameStatus.MAP_SELECTION);
        input.keyPressed(new KeyEvent(new Canvas(), 0, 0, 0, KeyEvent.VK_DOWN, ' '));
        verify(engine).receiveInput(ButtonAction.GO_DOWN);

        reset(engine);
        when(engine.getGameStatus()).thenReturn(GameStatus.RUNNING);
        input.keyPressed(new KeyEvent(new Canvas(), 0, 0, 0, KeyEvent.VK_UP, ' '));
        verify(engine).receiveInput(ButtonAction.JUMP);

        reset(engine);
        when(engine.getGameStatus()).thenReturn(GameStatus.RUNNING);
        input.keyPressed(new KeyEvent(new Canvas(), 0, 0, 0, KeyEvent.VK_RIGHT, ' '));
        verify(engine).receiveInput(ButtonAction.M_RIGHT);

        reset(engine);
        when(engine.getGameStatus()).thenReturn(GameStatus.RUNNING);
        input.keyPressed(new KeyEvent(new Canvas(), 0, 0, 0, KeyEvent.VK_LEFT, ' '));
        verify(engine).receiveInput(ButtonAction.M_LEFT);

        reset(engine);
        when(engine.getGameStatus()).thenReturn(GameStatus.RUNNING);
        input.keyPressed(new KeyEvent(new Canvas(), 0, 0, 0, KeyEvent.VK_ENTER, ' '));
        verify(engine).receiveInput(ButtonAction.SELECT);

        reset(engine);
        when(engine.getGameStatus()).thenReturn(GameStatus.RUNNING);
        input.keyPressed(new KeyEvent(new Canvas(), 0, 0, 0, KeyEvent.VK_ESCAPE, ' '));
        verify(engine).receiveInput(ButtonAction.PAUSE_RESUME);

        reset(engine);
        when(engine.getGameStatus()).thenReturn(GameStatus.PAUSED);
        input.keyPressed(new KeyEvent(new Canvas(), 0, 0, 0, KeyEvent.VK_ESCAPE, ' '));
        verify(engine).receiveInput(ButtonAction.PAUSE_RESUME);
    }

    @Test
    void spaceFires() {
        IGameEngine engine = mock(IGameEngine.class);
        InputManager input = new InputManager(engine);
        when(engine.getGameStatus()).thenReturn(GameStatus.RUNNING);
        input.keyPressed(new KeyEvent(new Canvas(), 0, 0, 0, KeyEvent.VK_SPACE, ' '));
        verify(engine).receiveInput(ButtonAction.FIRE);
    }

    @Test
    void mousePressedSelectsMapInSelectionScreen() {
        IGameEngine engine = mock(IGameEngine.class);
        InputManager input = new InputManager(engine);
        when(engine.getGameStatus()).thenReturn(GameStatus.MAP_SELECTION);

        input.mousePressed(new MouseEvent(new Canvas(), 0, 0, 0, 10, 10, 1, false));
        verify(engine).selectMapViaMouse();
    }
}
