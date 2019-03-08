package game;

import java.awt.*;

public interface Drawable {

    boolean isComposite();

    void draw(Graphics2D g2d);
}
