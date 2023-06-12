package Pacman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class LeaderboardListener implements ActionListener {
    private final JFrame leaderboardFrame = new JFrame();
    private final JFileChooser jfc = new JFileChooser();
    private final JLabel[] records = new JLabel[10];
    private final Map<String, String> recordMap = new LinkedHashMap<>();
    private final StringBuilder currentFileName = new StringBuilder();
    public LeaderboardListener(String file) {
        for (int i = 0; i < 10; i++)
            records[i] = new JLabel("", SwingConstants.CENTER);
        currentFileName.append(file);
        recordMap.clear();
        try {
            Scanner sc = new Scanner(new FileInputStream(String.valueOf(currentFileName)));
            while (sc.hasNextLine())
                recordMap.put(sc.nextLine(), sc.nextLine());
            sc.close();
        } catch (Exception exc) {
            System.err.println("Hiba történt fájlkezelés közben!");
            exc.printStackTrace();
        }
    }

    //A gomb megnyomásától függően cselekszik.
    // Ha Load, akkor egy fájlkiválasztás után betölti a
    // kiválasztott textfájlból a ranglistát.
    // Ha Save, akkor egy fájlkiválasztás után elmenti egy textfájlba a ranglistát.
    // Ha Open, akkor egy külön ablakban megnyitja az elmentett ranglistát.
    @SuppressWarnings("rawtypes")
    public void actionPerformed(ActionEvent ae) {
        switch (((JMenuItem) ae.getSource()).getText()) {
            case "Load":
                if (jfc.showDialog(leaderboardFrame, "Load Leaderboard") == JFileChooser.APPROVE_OPTION) {
                    currentFileName.delete(0, currentFileName.length());
                    currentFileName.append(jfc.getSelectedFile().getName());
                    recordMap.clear();
                    try {
                        Scanner sc = new Scanner(new FileInputStream(String.valueOf(currentFileName)));
                        while (sc.hasNextLine())
                            recordMap.put(sc.nextLine(), sc.nextLine());
                        sc.close();
                    } catch (Exception exc) {
                        System.err.println("Hiba történt fájlkezelés közben!");
                        exc.printStackTrace();
                    }
                }
                break;
            case "Save":
                if (jfc.showDialog(leaderboardFrame, "Save Leaderboard") == JFileChooser.APPROVE_OPTION) {
                    int j = 0;
                    try {
                        FileWriter fw = new FileWriter(jfc.getSelectedFile().getName());
                        for (int i = 0; i < 10; i++)
                            if ((i % 2 == 0)) fw.write(recordMap.keySet().toArray()[j] + "\n");
                            else fw.write(recordMap.values().toArray()[j++] + "\n");
                        fw.close();
                    } catch (Exception exc) {
                        System.err.println("Hiba!");
                        exc.printStackTrace();
                    }
                }
                break;
            case "Open":

                leaderboardFrame.setTitle("Leaderboard");
                JPanel leaderboardPanel = new JPanel(new GridLayout(5, 2));
                Iterator iter = recordMap.entrySet().iterator();
                int inc = 0;
                while (iter.hasNext()) {
                    Map.Entry e = (Map.Entry) iter.next();
                    records[inc].setText((String) e.getKey());
                    leaderboardPanel.add(records[inc++]);
                    records[inc].setText((String) e.getValue());
                    leaderboardPanel.add(records[inc++]);
                }
                leaderboardFrame.add(leaderboardPanel);
                leaderboardFrame.setPreferredSize(new Dimension(320, 240));
                leaderboardFrame.pack();
                leaderboardFrame.setLocationRelativeTo(null);
                leaderboardFrame.setResizable(false);
                leaderboardFrame.setVisible(true);
                break;
        }
    }
}