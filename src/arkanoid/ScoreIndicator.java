package arkanoid;

import core.Counter;
import core.Sprite;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * Sprite that indicate the score in the game.
 * The ScoreIndicator displays the value of the scoreCounter that is provided to it in its constructor.
 */
public class ScoreIndicator implements Sprite {
    private Counter score;

    /**
     * Constructor.
     *
     * @param score Counter that indicates the current score of the game in every moment
     */
    public ScoreIndicator(Counter score) {
        this.score = score;
    }

    /**
     * draw the ScoreIndicator on the screen.
     *
     * @param d DrawSurface to draw the ScoreIndicator on
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.white);
        d.fillRectangle(0, 0, 800, 25);
        d.setColor(Color.black);
        d.drawText(300, 17, "Score: " + this.score.getValue(), 15);
    }

    /**
     * notify the ScoreIndicator that time has passed.
     * ignored.
     *
     * @param dt - amount of seconds passed since the last call (i.e. last frame)
     */
    @Override
    public void timePassed(double dt) {

    }


    /**
     * Add this scoreIndicator to the game.
     *
     * @param g level of game to add the scoreIndicator to
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}
