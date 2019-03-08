package game.snakecore;

import game.Drawable;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class Snake implements Drawable {

    public enum Direction {
        UP,
        DOWN,
        RIGHT,
        LEFT,
    }

    private final LinkedList<Drawable> bodyParts;
    private Point position;
    private Point posToAdd;
    private Direction currDir;
    private Color headClr;
    private Color bodyClr;
    private int dx;
    private int dy;
    private boolean stopped;


    public Snake() {
        bodyParts = new LinkedList<>();
        initSnake(1, new Point(0, 0), Direction.UP);
    }


    public Snake(int bodySize, Direction initDir) throws IllegalArgumentException {
        if (bodySize < 1)
            throw new IllegalArgumentException("Must be more than or equal to 1 ");

        bodyParts = new LinkedList<>();
        initSnake(bodySize, new Point(0, 0), initDir);
    }

    public Snake(int bodySize, Point pos, Direction initDir) throws IllegalArgumentException {
        if (bodySize < 1)
            throw new IllegalArgumentException("Must be more than or equal to 1 ");

        bodyParts = new LinkedList<>();
        initSnake(bodySize, pos, initDir);
    }

    private void initSnake(int bodySize, Point pos, Direction initDir) {
        position = pos;

        stopped = true;

        headClr = Color.ORANGE;
        bodyClr = Color.BLUE;


        for (int i = 0; i < bodySize; i++) {
            if (bodyParts.isEmpty())
                bodyParts.add(new SnakeBodyPart(position));
            addBodyPart();
        }

        currDir = initDir;
        calculateVelocityMultipliers();

        stopped = false;
    }

    public void setBodyClr(Color clr) {
        bodyClr = clr;
    }

    public void setHeadClr(Color clr) {
        headClr = clr;
    }

    public void setDeathClr() {
        setBodyClr(Color.RED);
        setHeadClr(Color.MAGENTA);
    }

    public void addBodyPart() {
        bodyParts.add(new SnakeBodyPart(posToAdd));
    }

    public void removeBodyPart() {
        bodyParts.removeLast();
    }

    public Direction getCurrDir() {
        return currDir;
    }

    public void stop() {
        stopped = true;
        dx = 0;
        dy = 0;
    }

    public void resume(Direction dir) {
        if (stopped) {
            currDir = dir;
            stopped = false;
            calculateVelocityMultipliers();
        }
    }

    private void calculateVelocityMultipliers() {
        switch (currDir) {
            case UP:
                dx = 0;
                dy = -1;
                break;
            case DOWN:
                dx = 0;
                dy = 1;
                break;
            case RIGHT:
                dx = 1;
                dy = 0;
                break;
            case LEFT:
                dx = -1;
                dy = 0;
                break;
        }
    }

    public void moveSnake() {
        if (!stopped) {
            position.x += dx * SnakeBodyPart.SNAKE_PART_SIZE;
            position.y += dy * SnakeBodyPart.SNAKE_PART_SIZE;

            Point prevPos = bodyParts.getFirst().getPosition();
            bodyParts.getFirst().setPosition(new Point(position));

            for (Drawable dObject : bodyParts) {
                if (dObject != bodyParts.getFirst()) {
                    Point temp = dObject.getPosition();
                    dObject.setPosition(prevPos);
                    prevPos = temp;
                }
            }

            posToAdd = prevPos;
        }
    }

    @Override
    public Image getImage() {
        return null;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public Dimension getSize() {
        return new Dimension(getWidth(), getHeight());
    }

    @Override
    public int getWidth() {
        return bodyParts.getFirst().getWidth();
    }

    @Override
    public int getHeight() {
        return bodyParts.getFirst().getHeight() * bodyParts.size();
    }

    @Override
    public boolean intersects(Drawable another) {
        for (Drawable dObj : bodyParts) {
            if (another.intersects(dObj)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean isComposite() {
        return true;
    }

    @Override
    public void setPosition(Point newPos) {
        int diffX = getPosition().x - newPos.x;
        int diffY = getPosition().y - newPos.y;

        for (Drawable dObject : bodyParts) {
            Point prevPos = dObject.getPosition();
            dObject.setPosition(new Point(prevPos.x + diffX, prevPos.y + diffY));
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        for (Drawable dObject : bodyParts) {

            if (dObject == bodyParts.getFirst()) {
                g2d.setColor(headClr);
            } else {
                g2d.setColor(bodyClr);
            }

            dObject.draw(g2d);
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key) {
            case KeyEvent.VK_LEFT:
                if (currDir != Direction.RIGHT) {
                    dx = -1;
                    dy = 0;
                    currDir = Direction.LEFT;
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (currDir != Direction.LEFT) {
                    dx = 1;
                    dy = 0;
                    currDir = Direction.RIGHT;
                }
                break;
            case KeyEvent.VK_UP:
                if (currDir != Direction.DOWN) {
                    dy = -1;
                    dx = 0;
                    currDir = Direction.UP;
                }
                break;
            case KeyEvent.VK_DOWN:
                if (currDir != Direction.UP) {
                    dy = 1;
                    dx = 0;
                    currDir = Direction.DOWN;
                }
                break;
        }
    }

    public void keyReleased(KeyEvent e) {

    }
}