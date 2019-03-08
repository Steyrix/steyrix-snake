package game;

import java.awt.*;

public interface BoundingBoxed {
    Point getPosition();

    Dimension getSize();

    int getWidth();

    int getHeight();

    boolean intersects(BoundingBoxed another);

    void setPosition(Point newPos);
}
