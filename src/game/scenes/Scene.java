package game.scenes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public interface Scene {

    void init();

    void drawScene(Graphics g);

    void keyPressed(KeyEvent e);

    void keyReleased(KeyEvent e);

    void actionPerformed();

    void addUiComponents(JComponent target);

    SceneState getSceneState();
}
