package game.snakecore;

import game.Drawable;

import java.awt.*;

public class SnakeBodyPart implements Drawable {

    public static int SNAKE_PART_SIZE = 50;
    private final int width = 50;
    private final int height = 50;
    private int x;
    private int y;

    public SnakeBodyPart (int x, int y){
        this.x = x;
        this.y = y;
    }

    public SnakeBodyPart (Point initPos){
        this.x = initPos.x;
        this.y = initPos.y;
    }

    @Override
    public void setPosition(Point newPos){
        x = newPos.x;
        y = newPos.y;
    }

    @Override
    public Dimension getSize() {
        return new Dimension(width, height);
    }

    @Override
    public int getWidth(){
        return width;
    }

    @Override
    public int getHeight(){
        return height;
    }

    @Override
    public Image getImage() {
        return null;
    }

    @Override
    public boolean intersects(Drawable another) {
        Point anotherPos = another.getPosition();
        return Math.abs(anotherPos.x - x) < width &&
                Math.abs(anotherPos.y - y) < height && another != this;
    }

    @Override
    public boolean isComposite(){
        return false;
    }

    @Override
    public Point getPosition() {
        return new Point(x, y);
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.drawRect(x, y, width, height);
        g2d.fillRect(x, y, width, height);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x, y, width, height);
        g2d.drawRect(x + 5, y + 5, width - 10, height - 10);
    }
}
