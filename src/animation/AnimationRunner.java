package animation;

import biuoop.GUI;
import biuoop.Sleeper;
import biuoop.DrawSurface;

/**
 * AnimationRunner runs the animation of the game.
 */
public class AnimationRunner {

    private GUI gui;
    private int framesPerSecond;
    private Sleeper sleeper;

    /**
     * @param gui - get the gui of the game.
     */
    public AnimationRunner(GUI gui) {
        this.gui = gui;
        this.framesPerSecond = 60;
        this.sleeper = new biuoop.Sleeper();

    }

    /**
     * @param animation - gets the animation to run the game.
     */
    public void run(Animation animation) {

        int millisecondsPerFrame = 1000 / framesPerSecond;
        double secondsPassedSinceLastFrame = 0;
        while (!animation.shouldStop()) {
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = gui.getDrawSurface();
           // mySurfce m = new mySurfce(d);
            animation.doOneFrame(d, secondsPassedSinceLastFrame);

            this.gui.show(d);
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
                secondsPassedSinceLastFrame = (double) millisecondsPerFrame / 1000;
            } else {
                secondsPassedSinceLastFrame = (double) usedTime / 1000;
            }
        }
    }
}