package game.snakecore;

import game.BoundingBoxed;
import game.Drawable;

import java.awt.*;

public class SnakeBodyPart implements Drawable, BoundingBoxed {

    static final int PART_SIZE = 50;
    private int x;
    private int y;

    public SnakeBodyPart(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public SnakeBodyPart(Point initPos) {
        this.x = initPos.x;
        this.y = initPos.y;
    }

    @Override
    public void setPosition(Point newPos) {
        x = newPos.x;
        y = newPos.y;
    }

    @Override
    public Dimension getSize() {
        return new Dimension(PART_SIZE, PART_SIZE);
    }

    @Override
    public int getWidth() {
        return PART_SIZE;
    }

    @Override
    public int getHeight() {
        return PART_SIZE;
    }

    @Override
    public boolean intersects(BoundingBoxed another) {
        Point anotherPos = another.getPosition();
        return Math.abs(anotherPos.x - x) < PART_SIZE &&
                Math.abs(anotherPos.y - y) < PART_SIZE && another != this;
    }

    @Override
    public boolean isComposite() {
        return false;
    }

    @Override
    public Point getPosition() {
        return new Point(x, y);
    }

    @Override
    public void draw(Graphics2D g2d) {

        final int posScale = 5;
        final int partSizeScale = -10;

        g2d.drawRect(x, y, PART_SIZE, PART_SIZE);
        g2d.fillRect(x, y, PART_SIZE, PART_SIZE);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x, y, PART_SIZE, PART_SIZE);
        g2d.drawRect(x + posScale, y + posScale, PART_SIZE + partSizeScale, PART_SIZE + partSizeScale);
    }
}
