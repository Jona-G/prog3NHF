package Pacman;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameWindowKeyListener extends KeyAdapter {
    private final GameWindow window;
    public GameWindowKeyListener(GameWindow g) {window = g;}

    //Az Esc gomb megnyomásával a játékos visszakerül a kezdőképernyőre,
    //a P gomb megnyomása a GameWindow pause() függvényét hívja meg,
    //az iránygombok pedig Pacman mozgásáért felelősek.
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                ((Ablak) SwingUtilities.getWindowAncestor(window)).OpenTitleScreen();
                break;
            case KeyEvent.VK_P:
                window.pause();
                break;
            case KeyEvent.VK_LEFT:
                Pacman.dir = Direction.LEFT;
                break;
            case KeyEvent.VK_RIGHT:
                Pacman.dir = Direction.RIGHT;
                break;
            case KeyEvent.VK_UP:
                Pacman.dir = Direction.UP;
                break;
            case KeyEvent.VK_DOWN:
                Pacman.dir = Direction.DOWN;
                break;
        }
    }
}
