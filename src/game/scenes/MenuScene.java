package game.scenes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class MenuScene implements Scene {

    private final ArrayList<JComponent> uiElements;
    private String infoStr;
    private SceneState currState;
    private int points;

    public MenuScene() {
        uiElements = new ArrayList<>();
        currState = SceneState.STATIC;
        infoStr = "";
        points = 0;
        init();
    }

    public MenuScene(int pts) {
        uiElements = new ArrayList<>();
        currState = SceneState.STATIC;
        points = pts;
        infoStr = "<html> GAME IS OVER. </br> YOUR SCORE IS " + points + "</html>";
        init();
    }

    @Override
    public void init() {
        final JButton startButton = new JButton("START GAME");
        final JButton exitButton = new JButton("EXIT");
        final JLabel infoLabel = new JLabel(infoStr);

        infoLabel.setPreferredSize(new Dimension(200, 100));
        startButton.setPreferredSize(new Dimension(100, 50));
        startButton.setMaximumSize(new Dimension(100, 50));
        exitButton.setPreferredSize(new Dimension(100, 50));
        exitButton.setMaximumSize(new Dimension(100, 50));

        startButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                currState = SceneState.READY_TO_CHANGE;
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        exitButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                currState = SceneState.EXIT;
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        uiElements.add(startButton);
        uiElements.add(exitButton);
        uiElements.add(infoLabel);
    }


    @Override
    public void addUiComponents(final JComponent targetPanel) {
        targetPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        for (JComponent c : uiElements) {
            targetPanel.add(c, gbc);
            gbc.gridy++;
        }
        targetPanel.revalidate();
        targetPanel.repaint();
    }

    @Override
    public void drawScene(Graphics g) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed() {

    }

    @Override
    public SceneState getSceneState() {
        return currState;
    }

    public int getPoints() {
        return points;
    }

    public void setLeaderboard(List<String> leaderScores) {
        JList<String> list = new JList<>();
        list.setListData(leaderScores.toArray(new String[0]));

        JLabel label = new JLabel();
        label.setText("TOP 10: ");
        uiElements.add(label);
        uiElements.add(list);
    }
}
