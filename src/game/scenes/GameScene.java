package game.scenes;

import game.Drawable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class GameScene implements Scene {

    private static int PANEL_WIDTH = 500;
    private static int PANEL_HEIGHT = 500;

    private static final int squareSize = 50;
    private final ArrayList<Drawable> objects;

    public GameScene(){
        objects = new ArrayList<>();

        init();
    }

    @Override
    public void init() {

    }

    @Override
    public void drawScene(Graphics g) {

    }

    @Override
    public void actionPerformed() {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void addUiComponents(JComponent target){

    }

    private void renderDrawables(Graphics g) {
        for (Drawable obj : objects) {
            drawObject(g, obj);
        }
    }

    private void drawObject(Graphics g, Drawable dObject){
        Graphics2D g2d = (Graphics2D) g;
        dObject.draw(g2d);
    }

    private void drawPlayField(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.BLACK);

        for(int i = 0; i <= PANEL_WIDTH; i+= 25)
            g2d.drawLine(0, i, PANEL_WIDTH, i);
        for(int i = 0; i <= PANEL_HEIGHT; i+= 25)
            g2d.drawLine(i,0, i, PANEL_HEIGHT);

        g2d.drawString("Points: ", 0, 520);
    }
}