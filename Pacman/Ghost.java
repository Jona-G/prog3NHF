package Pacman;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Ghost {
    private final int speed;
    private final Pacman pacman;
    private int startx, starty, x, y, dx, dy;
    private final GameWindow window;
    public Ghost(int s, Pacman p, GameWindow w) {
        speed = s; pacman = p; window = w;
    }
    public void setStartPos(int newx, int newy) { startx = x = newx; starty = y = newy; }
    public int getStartX() {return startx;}
    public int getStartY() {return starty;}
    public int getX() { return x; }
    public int getY() { return y; }
    public Image getImage() { return new ImageIcon("game_ghost.png").getImage(); }

    //Egy random irányba mozdul el, ha ott nincs blokk vagy
    // nem a pálya szélén van. Ha egy Pacman-el ütközik,
    // akkor ha az halhatatlan, a szellemet öli meg, ha nem, akkor pedig Pacman-t.
    public void move() {
        if (x % 40 == 0 && y % 40 == 0) {
            dx = 0; dy = 0;
            Random rand = new Random();
            switch (rand.nextInt(4)) {
                case 0:
                    if (x > 0)
                        if (window.getDataAt(x/40-1, y/40) != 1) dx = -speed;
                    break;
                case 1:
                    if (x < 760)
                        if (window.getDataAt(x/40+1, y/40) != 1) dx = speed;
                    break;
                case 2:
                    if (y > 0)
                        if (window.getDataAt(x/40, y/40-1) != 1) dy = -speed;
                    break;
                case 3:
                    if (y < 540)
                        if (window.getDataAt(x/40, y/40+1) != 1) dy = speed;
                    break;
            }
        }
        x = x + dx; y = y + dy;
        if (pacman.getX() > x - 10 && pacman.getX() < x + 10 &&
            pacman.getY() > y - 10 && pacman.getY() < y + 10) {
                if (pacman.isPoweredUp())
                    window.killGhost(this);
                else pacman.die();
        }
    }
}
