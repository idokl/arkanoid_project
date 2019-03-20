package arkanoid;

import animation.Animation;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * CountDown animation before every level/start of new phase.
 */
public class CountdownAnimation implements Animation {
    private double numOfSeconds;
    private int countFrom;
    private SpriteCollection spriteCollection;
    private boolean done;
    private boolean start;
    private long startMillisecond;

    /**
     * @param numOfSeconds     - number of seconds the animation should run.
     * @param countFrom        - count from this number to zero
     * @param spriteCollection - the spriteCollection the game.
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection spriteCollection) {
        this.numOfSeconds = numOfSeconds;
        this.countFrom = countFrom;
        this.spriteCollection = spriteCollection;
        this.done = false;
        this.start = true;
    }

    /**
     * @return true, if the countdown is over. false, otherwise.
     */
    @Override
    public boolean shouldStop() {
        return this.done;
    }

    /**
     * @param d - draw surface to draw the sprites of the Animation on
     * @param dt amount of seconds passed since the last frame
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        if (this.start) {
            this.startMillisecond = System.currentTimeMillis();
            this.start = false;
        }

        this.spriteCollection.drawAllOn(d);
        long currentMillisecond = System.currentTimeMillis();
        double singleCountLength = this.numOfSeconds / (double) this.countFrom;
        Integer currentNumber = (int) ((1 + this.countFrom)
                - (currentMillisecond - this.startMillisecond) / (singleCountLength * 1000));
        d.setColor(Color.white);
        if (currentNumber != 0) {
            d.drawText(d.getWidth() / 2, d.getHeight() / 2 + 100, currentNumber.toString(), 48);
        }
        if ((currentMillisecond - this.startMillisecond) > this.numOfSeconds * 1000) {
            this.done = true;
        }
    }
}
