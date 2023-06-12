package Pacman;

import javax.swing.*;

public class Ablak extends JFrame {
    private final StringBuilder levelFileName = new StringBuilder();
    public Ablak() {
        levelFileName.append("level.txt");
        addKeyListener(new TitleScreenKeyListener(this));
        OpenTitleScreen();
        setTitle("PacMan");
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    //Létrehozza a kezdőképernyőt, a hozzátartozó Listener-rel és menüvel együtt.
    public void OpenTitleScreen() {
        removeKeyListener(getKeyListeners()[0]);
        addKeyListener(new TitleScreenKeyListener(this));
        getContentPane().removeAll();
        repaint();
        JMenuBar mb = new JMenuBar();
        TitleScreen ts = new TitleScreen(mb);
        getContentPane().add(ts);
        ts.create();
        setJMenuBar(mb);
        pack();
    }

    //Létrehozza a játékablakot, a hozzátartozó Listener-rel.
    public void OpenGameWindow() {
        removeKeyListener(getKeyListeners()[0]);
        GameWindow gw = new GameWindow(String.valueOf(levelFileName));
        addKeyListener(new GameWindowKeyListener(gw));
        getContentPane().removeAll();
        setJMenuBar(null);
        repaint();
        getContentPane().add(gw);
        pack();
    }

    //Beállítja az adott pálya fájljának a nevét.
    public void SetLevelFileName(String lfn) {
        levelFileName.delete(0, levelFileName.length());
        levelFileName.append(lfn);
    }
}

