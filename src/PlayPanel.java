import game.scenes.GameScene;
import game.scenes.MenuScene;
import game.scenes.Scene;
import game.scenes.SceneState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class PlayPanel extends JPanel implements ActionListener {

    private Scene currScene;
    private int delays;
    private boolean sceneChanged;

    public PlayPanel() throws IOException {
        sceneChanged = false;
        addKeyListener(new TAdapter());
        setFocusable(true);

        delays = 0;
        currScene = new GameScene();
        final int DELAY = 450;

        final Timer timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g){

        super.paintComponent(g);
        currScene.drawScene(g);

        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        currScene.actionPerformed();

        if(currScene.getSceneState() == SceneState.READY_TO_CHANGE) {
            if(currScene instanceof GameScene) {
                int pts = ((GameScene) currScene).getPoints();
                delays++;
                int delaysToWait = 3;

                if(delays == delaysToWait) {
                    currScene = new MenuScene(pts);
                    currScene.addUiComponents(this);

                    sceneChanged = true;

                    delays = 0;
                }
            }

            if(currScene instanceof MenuScene && !sceneChanged) {
                removeUi();
                currScene = new GameScene();
            }

            sceneChanged = false;
        }

        if(currScene.getSceneState() == SceneState.EXIT)
            System.exit(0);

        repaint();

    }

    private class TAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            currScene.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e){
            currScene.keyReleased(e);
        }
    }

    private void removeUi() {
        for( Component c : this.getComponents())
            if(c instanceof JButton || c instanceof JLabel)
                this.remove(c);

        this.requestFocusInWindow();
    }
}