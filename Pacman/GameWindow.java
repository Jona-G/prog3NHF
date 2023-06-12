package Pacman;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.util.*;
import java.util.List;

public class GameWindow extends JPanel implements ActionListener {
    public static Difficulty difficulty = Difficulty.NORMAL;
    public static Color color = Color.BLUE;
    private final Pacman pacman;
    private final Timer timer = new Timer(30, this);
    private final List<Ghost> ghosts = new ArrayList<>();
    private int maxScore, ghostNumber, score = 0, currentLevel = 1;
    private final short[][] levelData = new short[15][];
    public GameWindow(String file) {
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(800, 600));
        pacman = new Pacman(this);
        timer.start();
        try {
            Scanner sc = new Scanner(new FileInputStream(file));
            for (int i = 0; i < 15; i++) {
                levelData[i] = new short[20];
                for (int j = 0; j < 20; j++) {
                    levelData[i][j] = sc.nextShort();
                    switch (levelData[i][j]) {
                        case 0:
                            maxScore++;
                            break;
                        case 4:
                            pacman.setStartPos(j * 40, i * 40);
                            break;
                        case 5:
                            summonGhosts(i, j);
                            break;
                    }
                }
            }
            sc.close();
        } catch (Exception exc) { exc.printStackTrace(); System.exit(0);}
    }

    //Ez a metódus hívódik meg a Timer minden lépésén, további függvényeket hív meg.
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        draw(g);
    }

    //A Graphics-ként kapott attribútumot Graphics2D-re kasztolja,
    //és azzal hívja meg a többi kirajzoláshoz szükséges függvényeket.
    private void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        drawMaze(g2d);
        movePacman(g2d);
        moveGhosts(g2d);
        drawText(g2d);
        Random rand = new Random();
        if (rand.nextInt(1000) == 0)
            summonPowerUp();
    }

    //Meghívja a pályán lévő Pacman move() függvényét,
    // illetve az irányától függően kirajzolja őt a pályára.
    // Ha golyót vesz fel, növeli a pontot, ha egy színes golyót vesz fel,
    // akkor egy ideig halhatatlanná válik.
    private void movePacman(Graphics2D g2d) {
        pacman.move();
        switch (Pacman.dir) {
            case LEFT:
                g2d.rotate(Math.toRadians(180), pacman.getX()+20, pacman.getY()+20);
                g2d.drawImage(pacman.getImage(), pacman.getX(), pacman.getY(), this);
                g2d.rotate(Math.toRadians(180), pacman.getX()+20, pacman.getY()+20);
                break;
            case STILL:
            case RIGHT:
                g2d.drawImage(pacman.getImage(), pacman.getX(), pacman.getY(), this);
                break;
            case UP:
                g2d.rotate(Math.toRadians(270), pacman.getX()+20, pacman.getY()+20);
                g2d.drawImage(pacman.getImage(), pacman.getX(), pacman.getY(), this);
                g2d.rotate(Math.toRadians(90), pacman.getX()+20, pacman.getY()+20);
                break;
            case DOWN:
                g2d.rotate(Math.toRadians(90), pacman.getX()+20, pacman.getY()+20);
                g2d.drawImage(pacman.getImage(), pacman.getX(), pacman.getY(), this);
                g2d.rotate(Math.toRadians(270), pacman.getX()+20, pacman.getY()+20);
                break;
        }
        switch (levelData[pacman.getY() / 40][pacman.getX() / 40]) {
            case 6:
                pacman.setPowerUp();
            case 0:
                levelData[pacman.getY() / 40][pacman.getX() / 40] = 9;
                score++;
                if (score == maxScore) nextLevel();
                break;
        }
    }

    //Az összes szellem move() függvényét hívja,
    //illetve kirajzolja a képüket a pályára.
    public void moveGhosts(Graphics2D g2d) {
        for (int i = 0; i < ghostNumber; i++) {
            g2d.drawImage(ghosts.get(i).getImage(), ghosts.get(i).getX(), ghosts.get(i).getY(), this);
            ghosts.get(i).move();
        }
    }

    //A bal alsó sarokban lévő szövegeket írja ki.
    public void drawText(Graphics2D g2d) {
        String s1 = "Level: " + currentLevel;
        String s2 = "Score: " + score;
        String s3 = "Lives: " + pacman.getLives();
        g2d.setFont(new Font("Helvetica", Font.BOLD, 14));
        g2d.setColor(new Color(96, 128, 255));
        g2d.drawString(s1, 580, 595);
        g2d.drawString(s2, 650, 595);
        g2d.drawString(s3, 740, 595);
    }

    //A pályát alakító blokkokat és golyókat
    //rajzolja ki a kezdőképernyőben beállított színnel.
    private void drawMaze(Graphics2D g2d) {
        g2d.setStroke(new BasicStroke(5));
        g2d.setColor(color);
        g2d.drawRect(0, 0, 800, 600);
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 20; j++) {
                switch (levelData[i][j]) {
                    case 0: { //Begyűjthető golyó
                        g2d.setColor(Color.WHITE);
                        g2d.fillRect(j*40+18, i*40+18, 4, 4);
                        g2d.setColor(color);
                        break;
                    }
                    case 1: { //Pálya közepén lévő blokk
                        g2d.drawRect(j*40, i*40, 40, 40);
                        break;
                    }
                    case 6: {
                        g2d.setColor(Color.RED);
                        g2d.fillRect(j*40+13, i*40+13, 14, 14);
                        g2d.setColor(color);
                        break;
                    }
                }
            }
        }
    }

    //Random időközönként hívott függvény,
    //ami a pályán lévő golyók közül random helyre egy színes golyót hív le.
    public void summonPowerUp() {
        boolean validPosition = false;
        while (!validPosition) {
            Random rand = new Random();
            int px = rand.nextInt(20);
            int py = rand.nextInt(15);
            if (levelData[py][px] == 0) {
                validPosition = true;
                levelData[py][px] = 6;
            }
        }
    }

    //A pályán egy megjelölt helyre, megadott számú szellemet rak le a függvény.
    public void summonGhosts(int i, int j) {
        switch (difficulty) {
            case EASY:
                pacman.setLives(4);
                ghostNumber = 5 + currentLevel;
                for (int k = 0; k < ghostNumber; k++) {
                    ghosts.add(new Ghost(4, pacman, this));
                    ghosts.get(k).setStartPos(j * 40, i * 40);
                }
                break;
            case NORMAL:
                pacman.setLives(3);
                ghostNumber = 7 + currentLevel * 2;
                for (int k = 0; k < ghostNumber; k++) {
                    ghosts.add(new Ghost(5, pacman, this));
                    ghosts.get(k).setStartPos(j * 40, i * 40);
                }
                break;
            case HARD:
                pacman.setLives(2);
                ghostNumber = 9 + currentLevel * 3;
                for (int k = 0; k < ghostNumber; k++) {
                    ghosts.add(new Ghost(8, pacman, this));
                    ghosts.get(k).setStartPos(j * 40, i * 40);
                }
                break;
        }
    }

    //Lekérdezi az adott helyen lévő mező értékét.
    public int getDataAt(int x, int y) {
        return levelData[y][x];
    }

    //Az adott szellemet eltünteti a pályáról.
    public void killGhost(Ghost g) {
        ghosts.remove(g);
        ghostNumber--;
    }

    //Ha minden golyó fel lett szedve a pályáról,
    //akkor a Pacman és a szellemek helyét a kezdőpozíciókra teszi,
    //a golyókat újrarajzolja, és indul a következő szint.
    public void nextLevel() {
        for (Iterator<Ghost> iter = ghosts.iterator(); iter.hasNext();) {
            iter.next();
            iter.remove();
            ghostNumber--;
        }
        pacman.setStartPos(pacman.getStartX(), pacman.getStartY());
        currentLevel++;
        for (int i = 0; i < 15; i++)
            for (int j = 0; j < 20; j++)
                if (levelData[i][j] == 9) {
                    levelData[i][j] = 0;
                    maxScore++;
                }
                else if (levelData[i][j] == 5)
                    summonGhosts(i, j);
    }

    //A P gomb megnyomására hívódik meg a függvény, mely leállítja a játékmenetet,
    //majd a gomb ismételt megnyomására újra elindítja a játékot ott, ahol az abba lett hagyva.
    public void pause() {
        if (timer.isRunning()) {
            timer.stop();
        }
        else timer.start();
    }

    //Ha a Pacman meghal, akkor egy élet levonódik és ő,
    //illetve a szellemek a kezdőpozícióba kerülnek,
    // de az adott szinten lévő golyók megmaradnak.
    public void reset() {
        pacman.setStartPos(pacman.getStartX(), pacman.getStartY());
        for (int i = 0; i < ghostNumber; i++)
            ghosts.get(i).setStartPos(ghosts.get(i).getStartX(), ghosts.get(i).getStartY());
    }

    //Ha a Pacman meghal, és nincs több élete,
    //akkor a játék véget ér, és a megszerzett pont bekerülhet a ranglistába.
    public void end() {
        ((Ablak) SwingUtilities.getWindowAncestor(this)).OpenTitleScreen();
        System.out.println("A megszerzett pontszámod: " + score);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
