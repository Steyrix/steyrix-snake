package game.snakecore;

import java.awt.*;

import game.BoundingBoxed;
import game.Drawable;

public class SnakeFood implements Drawable, BoundingBoxed {

    public static final int FOOD_SIZE = 30;
    private int x;
    private int y;

    public SnakeFood(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public Point getPosition() {
        return new Point(x,y);
    }

    @Override
    public Dimension getSize() {
        return new Dimension(FOOD_SIZE, FOOD_SIZE);
    }

    @Override
    public int getWidth() {
        return FOOD_SIZE;
    }

    @Override
    public int getHeight() {
        return FOOD_SIZE;
    }

    @Override
    public boolean intersects(BoundingBoxed another) {
        Point anotherPos = another.getPosition();
        return Math.abs(anotherPos.x - x) < FOOD_SIZE &&
                Math.abs(anotherPos.y - y) < FOOD_SIZE && another != this;
    }

    @Override
    public boolean isComposite(){
        return false;
    }

    @Override
    public void setPosition(Point newPos) {
        x = newPos.x;
        y = newPos.y;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.RED);
        g2d.drawRect(x, y, FOOD_SIZE, FOOD_SIZE);
        g2d.fillRect(x, y, FOOD_SIZE, FOOD_SIZE);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x, y, FOOD_SIZE, FOOD_SIZE);
    }
}
