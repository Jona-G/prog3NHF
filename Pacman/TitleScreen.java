package Pacman;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Field;

public class TitleScreen extends JPanel {
    private final JMenuBar menubar;
    public TitleScreen(JMenuBar mb) {
        menubar = mb;
    }

    //A menüpontokat és a bennük lévő opciókat készíti el,
    //hozzáadja a szükséges Listener-eket,
    //illetve a képet és a két szövegdobozt rajzolja ki.
    public void create() {
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(800, 600));
        //Leaderboard menu elkészítése
        JMenu menu_file = new JMenu("Leaderboard");
        JMenuItem menuitem_open = new JMenuItem("Open");
        JMenuItem menuitem_load = new JMenuItem("Load");
        JMenuItem menuitem_save = new JMenuItem("Save");
        LeaderboardListener ll = new LeaderboardListener("leaderboard.txt");
        menuitem_open.addActionListener(ll);
        menuitem_load.addActionListener(ll);
        menuitem_save.addActionListener(ll);
        menu_file.add(menuitem_open);
        menu_file.add(menuitem_load);
        menu_file.add(menuitem_save);
        menubar.add(menu_file);
        //Load level menu elkészítése
        JMenu menu_loadlevel = new JMenu("Load level");
        JMenuItem menuitem_loadlevel = new JMenuItem("Load level");
        ActionListener lll = e -> {
            JFileChooser jfc = new JFileChooser();
            JFrame LoadLevelFrame = new JFrame();
            if (jfc.showDialog(LoadLevelFrame, "Load Level") == JFileChooser.APPROVE_OPTION)
                ((Ablak) SwingUtilities.getWindowAncestor(this))
                        .SetLevelFileName(jfc.getSelectedFile().getName());
        };
        menuitem_loadlevel.addActionListener(lll);
        menu_loadlevel.add(menuitem_loadlevel);
        menubar.add(menu_loadlevel);
        //Options menu elkészítése
        JMenu menu_options = new JMenu("Options");
        JMenu menu_difficulty = new JMenu("Difficulty");
        JMenuItem menuitem_easy = new JMenuItem("Easy");
        JMenuItem menuitem_normal = new JMenuItem("Normal");
        JMenuItem menuitem_hard = new JMenuItem("Hard");
        ActionListener dl = e -> {
            switch (((JMenuItem) e.getSource()).getText()) {
                case "Easy":
                    GameWindow.difficulty = Difficulty.EASY;
                    break;
                case "Normal":
                    GameWindow.difficulty = Difficulty.NORMAL;
                    break;
                case "Hard":
                    GameWindow.difficulty = Difficulty.HARD;
                    break;
            }
        };
        menuitem_easy.addActionListener(dl);
        menuitem_normal.addActionListener(dl);
        menuitem_hard.addActionListener(dl);
        menu_difficulty.add(menuitem_easy);
        menu_difficulty.add(menuitem_normal);
        menu_difficulty.add(menuitem_hard);
        menu_options.add(menu_difficulty);
        JMenu menu_mazecolor = new JMenu("Maze Color");
        JMenuItem menuitem_blue = new JMenuItem("BLUE");
        JMenuItem menuitem_green = new JMenuItem("GREEN");
        JMenuItem menuitem_white = new JMenuItem("WHITE");
        JMenuItem menuitem_magenta = new JMenuItem("MAGENTA");
        ActionListener mcl = e -> {
            GameWindow.color = Color.BLUE;
            try {
                Field field = Class.forName("java.awt.Color").getField(((JMenuItem) e.getSource()).getText());
                GameWindow.color = (Color) field.get(null);
            } catch (Exception exc) { exc.printStackTrace(); }
        };
        menuitem_blue.addActionListener(mcl);
        menuitem_green.addActionListener(mcl);
        menuitem_white.addActionListener(mcl);
        menuitem_magenta.addActionListener(mcl);
        menu_mazecolor.add(menuitem_blue);
        menu_mazecolor.add(menuitem_green);
        menu_mazecolor.add(menuitem_white);
        menu_mazecolor.add(menuitem_magenta);
        menu_options.add(menu_mazecolor);
        menubar.add(menu_options);
        //Pacman.Pacman logo elhelyezése
        try {
            BufferedImage picPacman = ImageIO.read(new File("pacman.png"));
            JLabel logoPacman = new JLabel(new ImageIcon(picPacman));
            logoPacman.setVerticalAlignment(JLabel.TOP);
            logoPacman.setPreferredSize(new Dimension(800, 300));
            add(logoPacman);
        } catch (IOException i) {
            System.err.println("Hiba!");
        }
        //Középső szöveg elhelyezése
        JTextField jtf = new JTextField("Nyomj Entert a játék kezdéséhez!");
        jtf.setHorizontalAlignment(JTextField.CENTER);
        jtf.setBackground(Color.BLACK);
        Font font1 = new Font(jtf.getFont().getName(), Font.BOLD, jtf.getFont().getSize() + 10);
        jtf.setFont(font1);
        jtf.setForeground(Color.WHITE);
        jtf.setFocusable(false);
        jtf.setPreferredSize(new Dimension(800, 260));
        add(jtf);
        //Alsó szöveg elhelyezése
        JTextField jtf2 = new JTextField("Készítette: Pribék Jonatán");
        jtf2.setBackground(Color.BLACK);
        jtf2.setForeground(Color.WHITE);
        jtf2.setFocusable(false);
        jtf2.setPreferredSize(new Dimension(800, 20));
        add(jtf2);
        validate();
    }
}
