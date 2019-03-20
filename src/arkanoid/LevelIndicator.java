package arkanoid;

import core.Sprite;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * Sprite that indicates the level's name.
 */
public class LevelIndicator implements Sprite {
    private String levelName;

    /**
     * @param levelName - name of the level
     */
    public LevelIndicator(String levelName) {
        this.levelName = levelName;
    }

    /**
     * draw the level name to the screen.
     *
     * @param d - surface to draw the level name on
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.black);
        d.drawText(450, 15, "Level Name: " + this.levelName, 15);
    }

    /**
     * notify the sprite that time has passed.
     *
     * @param dt amount of seconds passed since the last frame
     */
    @Override
    public void timePassed(double dt) {

    }


    /**
     * Add this LevelIndicator to the game.
     *
     * @param g - game environment of ball.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}
