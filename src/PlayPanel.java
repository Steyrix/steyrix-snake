import game.scenes.GameScene;
import game.scenes.MenuScene;
import game.scenes.Scene;
import game.scenes.SceneState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Collections;
import java.util.stream.Stream;

public class PlayPanel extends JPanel implements ActionListener {

    private Scene currScene;
    private int delays;
    private boolean sceneChanged;
    private boolean listAdded;

    public PlayPanel() throws IOException {
        sceneChanged = false;
        listAdded = false;
        addKeyListener(new TAdapter());
        setFocusable(true);

        delays = 0;
        currScene = new GameScene();
        final int DELAY = 450;

        final Timer timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        currScene.drawScene(g);

        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        currScene.actionPerformed();

        if (currScene.getSceneState() == SceneState.READY_TO_CHANGE) {
            if (currScene instanceof GameScene) {
                int pts = ((GameScene) currScene).getPoints();
                delays++;
                int delaysToWait = 3;

                if (delays == delaysToWait) {
                    currScene = new MenuScene(pts);
                    currScene.addUiComponents(this);

                    sceneChanged = true;

                    delays = 0;
                }
            }

            if (currScene instanceof MenuScene && !sceneChanged) {
                removeUi();
                currScene = new GameScene();
                listAdded = false;
            }

            sceneChanged = false;
        }

        if (currScene instanceof MenuScene && !listAdded) {

            manageLeaderBoard();
            currScene.addUiComponents(this);
            listAdded = true;
        }

        if (currScene.getSceneState() == SceneState.EXIT)
            System.exit(0);

        repaint();

    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            currScene.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            currScene.keyReleased(e);
        }
    }

    private void removeUi() {
        for (Component c : this.getComponents())
            if (c instanceof JButton || c instanceof JLabel || c instanceof JList)
                this.remove(c);

        this.requestFocusInWindow();
    }

    private void manageLeaderBoard() {
        int pts = ((MenuScene) currScene).getPoints();
        ArrayList<String> list = getLeaderList();

        if (pts != 0 && !list.contains(pts)) {
            list.add(String.valueOf(pts));
        }
        Collections.sort(list);
        Collections.reverse(list);
        if (list.size() > 10) {
            list.remove(list.size() - 1);
        }
        setLeaderList(list);

        ((MenuScene) currScene).setLeaderboard(getLeaderList());
    }

    private ArrayList<String> getLeaderList() {
        final String fileName = "src/leaderboard";
        final ArrayList<String> out = new ArrayList<>();

        File f = new File(fileName);

        try (Stream<String> lines = Files.lines(Paths.get(f.getPath()))) {
            lines.forEach(out::add);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return out;
    }

    private void setLeaderList(List<String> list) {
        final String fileName = "src/leaderboard";
        File f = new File(fileName);

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(f);
            writer.print("");

            for (String item : list) {
                writer.write(item + "\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        writer.close();
    }
}