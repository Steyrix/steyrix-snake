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

    private SceneState currState;
    private SnakeFood foodItem;
    private String pointStr;
    private Snake.Direction dir;

    private int previousKeyCode;
    private int points;

    public GameScene() {
        drawables = new ArrayList<>();
        snake = new Snake(3, new Point(250, 250), Snake.Direction.UP);
        drawables.add(snake);
        init();
    }

    @Override
    public void init() {
        currState = SceneState.STATIC;
        points = 0;
        pointStr = "(ESC to pause) Points: ";
        generateFood();
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
            drawables.remove(foodItem);
            generateFood();
        }

        if (snake.intersects(snake)) {
            snake.setDeathClr();
            snake.stop();
            currState = SceneState.READY_TO_CHANGE;
        }

        final boolean outOfBoundsX = snake.getPosition().x >= PANEL_WIDTH
                || snake.getPosition().x <= 0 - snake.getWidth();

        final boolean outOfBoundsY = snake.getPosition().y >= PANEL_HEIGHT
                || snake.getPosition().y <= 0 - snake.getHeight();

        if (outOfBoundsX || outOfBoundsY) {
            snake.setDeathClr();
            snake.stop();
            currState = SceneState.READY_TO_CHANGE;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (previousKeyCode == KeyEvent.VK_ESCAPE) {
                currState = SceneState.READY_TO_CHANGE;
            } else {
                currState = SceneState.PAUSE;
                pointStr = "(Paused. ENTER to resume / ESC to exit) Points: ";
                snake.stop();
                dir = snake.getCurrDir();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (previousKeyCode == KeyEvent.VK_ESCAPE) {
                currState = SceneState.STATIC;
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

    @Override
    public SceneState getSceneState() {
        return currState;
    }

    public int getPoints() {
        return points;
    }

    private void drawPlayField(Graphics g) {

        final int textPosX = 0;
        final int textPosY = 520;

        final Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.BLACK);

        for (int i = 0; i <= PANEL_WIDTH; i += snake.getWidth())
            g2d.drawLine(0, i, PANEL_WIDTH, i);
        for (int i = 0; i <= PANEL_HEIGHT; i += snake.getHeight())
            g2d.drawLine(i, 0, i, PANEL_HEIGHT);

        g2d.drawString(pointStr + points, textPosX, textPosY);
    }

    private void generateFood() {
        SnakeFood temp;
        while (true) {
            int foodX = ThreadLocalRandom.current().nextInt(0, 500);
            int squareSize = 50;

            int realX = foodX - foodX % squareSize + (squareSize - SnakeFood.FOOD_SIZE) / 2;
            int foodY = ThreadLocalRandom.current().nextInt(0, 500);
            int realY = foodY - foodY % squareSize + (squareSize - SnakeFood.FOOD_SIZE) / 2;
            temp = new SnakeFood(realX, realY);

            if (snake.intersects(temp)) {
                continue;
            } else {
                break;
            }
        }
        foodItem = temp;
        drawables.add(foodItem);
    }

    private void renderDrawables(Graphics g) {
        for (Drawable dObj : drawables) {
            dObj.draw((Graphics2D) g);
        }
    }
}