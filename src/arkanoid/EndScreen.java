package arkanoid;

import animation.Animation;
import biuoop.DrawSurface;
import core.Counter;

import java.awt.Color;

/**
 * EndScreen.
 */
public class EndScreen implements Animation {

    private Counter score;
    private boolean stop;
    private Counter lives;

    /**
     * @param score - Counter that maintains score that was collected during the game
     * @param lives - Counter that maintains number of lives that remained
     */
    public EndScreen(Counter score, Counter lives) {
        this.score = score;
        this.lives = lives;
        this.stop = false;
    }

    /**
     * @param d  - DrawSurface to draw the sprites of the EndScreen on
     * @param dt amount of seconds passed since the last frame
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(new Color(0, 102, 204));
        d.fillRectangle(0, 0, 800, 600);
        if (this.lives.getValue() == 0) {
            d.setColor(Color.black);
            d.drawText(60, 200, "Game Over. Your score is " + this.score.getValue(), 50);
        } else {
            d.setColor(new Color(102, 0, 51));
            d.drawText(100, 200, "You Win! Your score is " + this.score.getValue(), 50);
        }
        d.setColor(Color.black);
        d.drawText(10, 400, "Paused. press space to continue", 20);
    }

    /**
     * @return true, if the space key has been pressed and the EndScreen hasn't to be displayed. false, otherwise.
     */
    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
