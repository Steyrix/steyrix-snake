package game.scenes;

import game.Drawable;
import game.snakecore.Snake;
import game.snakecore.SnakeFood;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class GameScene implements Scene {


    private static final int PANEL_WIDTH = 500;
    private static final int PANEL_HEIGHT = 500;

    private final ArrayList<Drawable> drawables;

    private final Snake snake;
    private SnakeFood foodItem;
    private String pointStr;
    private Snake.Direction dir;
    private int previousKeyCode;
    private int points;
    private final int squareSize = 50;

    public GameScene() {
        drawables = new ArrayList<>();
        snake = new Snake(3, new Point(250, 250), Snake.Direction.UP);
        drawables.add(snake);
        init();
    }

    @Override
    public void init() {

        points = 0;
        pointStr = "(ESC to pause) Points: ";
        generateFood();
    }

    private void renderDrawables(Graphics g) {
        for (Drawable dObj : drawables) {
            dObj.draw((Graphics2D) g);
        }
    }

    @Override
    public void drawScene(Graphics g) {
        renderDrawables(g);
        drawPlayField(g);
    }

    @Override
    public void actionPerformed() {
        snake.moveSnake();

        if (snake.intersects(foodItem)) {
            snake.addBodyPart();
            points++;
            generateFood();
        }

        if (snake.intersects(snake)) {
            snake.setDeathClr();
            snake.stop();
        }

        if (snake.getPosition().x >= PANEL_WIDTH
                || snake.getPosition().x <= 0 - snake.getWidth()
                || snake.getPosition().y >= PANEL_HEIGHT
                || snake.getPosition().y <= 0 - snake.getHeight()) {
            snake.setDeathClr();
            snake.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            pointStr = "(Paused. ENTER to resume / ESC to exit) Points: ";
            snake.stop();
            dir = snake.getCurrDir();
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (previousKeyCode == KeyEvent.VK_ESCAPE) {
                pointStr = "(ESC to pause) Points: ";
                snake.resume(dir);
            }
        } else
            snake.keyPressed(e);

        previousKeyCode = e.getKeyCode();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        snake.keyReleased(e);
    }

    @Override
    public void addUiComponents(JComponent target) {

    }

    private void drawObject(Graphics g, Drawable dObject) {
        Graphics2D g2d = (Graphics2D) g;
        dObject.draw(g2d);
    }

    private void drawPlayField(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.BLACK);

        for (int i = 0; i <= PANEL_WIDTH; i += snake.getWidth())
            g2d.drawLine(0, i, PANEL_WIDTH, i);
        for (int i = 0; i <= PANEL_HEIGHT; i += snake.getHeight())
            g2d.drawLine(i, 0, i, PANEL_HEIGHT);

        g2d.drawString(pointStr + points, 0, 520);
    }

    private void generateFood() {
        SnakeFood temp;
        while (true) {
            int foodX = ThreadLocalRandom.current().nextInt(0, 500);
            int realX = foodX - foodX % squareSize + (squareSize - SnakeFood.FOOD_SIZE) / 2;
            int foodY = ThreadLocalRandom.current().nextInt(0, 500);
            int realY = foodY - foodY % squareSize + (squareSize - SnakeFood.FOOD_SIZE) / 2;
            temp = new SnakeFood(realX, realY);
            if (snake.intersects(temp)) continue;
            else break;
        }
        foodItem = temp;
        drawables.add(foodItem);
    }

    public int getPoints() {
        return points;
    }
}