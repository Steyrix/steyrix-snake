package game;

import java.awt.*;

public interface Drawable {

    Image getImage();

    Point getPosition();

    Dimension getSize();

    int getWidth();

    int getHeight();

    boolean intersects(Drawable another);

    boolean isComposite();

    void setPosition(Point newPos);

    void draw(Graphics2D g2d);
}
