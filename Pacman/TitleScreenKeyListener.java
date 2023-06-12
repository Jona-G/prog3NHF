package Pacman;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

public class TitleScreenKeyListener extends KeyAdapter {
    private final Ablak window;
    public TitleScreenKeyListener(Ablak jf) {window = jf;}

    //Az Enter gomb megnyomására a Pacman játék jellegzetes kezdő dallamát indítja el,
    //és meghívja az Ablak OpenGameWindow() metódusát.
    //Az S gomb megnyomására kilép a programból.
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                AudioInputStream audio = AudioSystem.getAudioInputStream(new File("startsound.wav").getAbsoluteFile());
                Clip clip = AudioSystem.getClip();
                clip.open(audio);
                clip.setMicrosecondPosition(0);
                clip.start();
            } catch (Exception exc) {
                System.err.println("Hiba történt egy hang lejátszása közben!");
                exc.printStackTrace();
            }
            window.OpenGameWindow();
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            System.exit(0);
        }
    }
}
