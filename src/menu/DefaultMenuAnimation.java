package menu;

import java.awt.Color;


import animation.Animation;
import biuoop.DrawSurface;

/**
 * empty menu (waiting to be decorated when selections will be added to it).
 */
public class DefaultMenuAnimation implements Animation {

    private String title;

    /**
     * @param title - the title of the menu
     */
    public DefaultMenuAnimation(String title) {
        this.title = title;
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(new Color(51, 170, 255));
        d.fillRectangle(0, 0, 800, 600);
        d.setColor(new Color(200, 0, 0));
        d.drawText(100, 70, this.title, 50);
    }

    @Override
    public boolean shouldStop() {
        return false;
    }


}
