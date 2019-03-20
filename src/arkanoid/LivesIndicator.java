package arkanoid;

import core.Counter;
import core.Sprite;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * Sprite that indicate the number of "lives" of the player in the game.
 * The LivesIndicator displays the value of the lives Counter that is provided to it in its constructor.
 */
public class LivesIndicator implements Sprite {
    private Counter lives;

    /**
     * Constructor.
     *
     * @param lives - maintain number of lives that the game player has
     */
    public LivesIndicator(Counter lives) {
        this.lives = lives;
    }

    /**
     * draw the LivesIndicator to the screen.
     *
     * @param d - surface to draw the LivesIndicator on
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.black);
        d.drawText(150, 15, "Lives: " + this.lives.getValue(), 15);
    }

    /**
     * notify the LivesIndicator that time has passed.
     *
     * @param dt amount of seconds passed since the last frame
     */
    @Override
    public void timePassed(double dt) {

    }

    /**
     * Add this scoreIndicator to the game.
     *
     * @param g - game level to add the scoreIndicator to
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}
