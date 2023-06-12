package Pacman;

import javax.swing.*;
import java.awt.*;

public class Pacman {
    public static Direction dir = Direction.STILL;
    private int startx, starty, x, y, dx, dy, lives;
    private int poweredUp = 0;
    private final GameWindow window;
    public Pacman(GameWindow w) { window = w; }

    //Ha Pacman épp egy színes golyó hatása alatt van,
    // akkor egy piros Pacman képet ad vissza,
    // egyébként egy sárga Pacman képet ad vissza.
    public Image getImage() {
        if (poweredUp != 0)
            return new ImageIcon("game_powerpacman.png").getImage();
        else return new ImageIcon("game_pacman.png").getImage();
    }

    //Beállítja a kezdőpontot, ahová a játék során visszakerül
    // halál vagy szint megnyerése után,
    // ezzel együtt a jelenlegi pozícióját is ide állítja be.
    public void setStartPos(int newx, int newy) { startx = x = newx; starty = y = newy; }
    public int getStartX() {return startx;}
    public int getStartY() {return starty;}
    public int getX() { return x; }
    public int getY() { return y; }
    public void setLives(int l) { lives = l; }
    public int getLives() { return lives; }
    public void setPowerUp() { poweredUp = 500; }
    public boolean isPoweredUp() { return poweredUp != 0; }

    //Ha Pacman épp egy mező közepén áll akkor
    // az utoljára lenyomott gomb irányába mozog.
    // Ha épp halhatatlan, akkor annak idejét csökkenti.
    // Ha Pacman útjában egy blokk van,
    // vagy már a pálya szélén van, akkor oda nem fog átlépni.
    public void move() {
        if (poweredUp > 0) poweredUp--;
        if (x % 40 == 0 && y % 40 == 0) {
            dx = 0; dy = 0;
            switch (dir) {
                case LEFT:
                    if (x > 0)
                        if (window.getDataAt((x-40)/40, y/40) != 1) dx = -10;
                    break;
                case RIGHT:
                    if (x < 760)
                        if (window.getDataAt((x+40)/40, y/40) != 1) dx = 10;
                    break;
                case UP:
                    if (y > 0)
                        if (window.getDataAt(x/40, (y-40)/40) != 1) dy = -10;
                    break;
                case DOWN:
                    if (y < 540)
                        if (window.getDataAt(x/40, (y+40)/40) != 1) dy = 10;
                    break;
            }
        }
        x = x + dx; y = y + dy;
    }

    //Levon egy életet, és ha még marad, akkor a GameWindow reset()-et hívja,
    // ha már nem marad, akkor pedig az end()-et hívja meg.
    public void die() {
        lives--;
        if (lives > 0)
            window.reset();
        else window.end();
    }
}
