package animation;

import biuoop.DrawSurface;

/**
 * animation that AnimationRunner can run.
 */
public interface Animation {
    /**
     * method that has to run in a loop as long as the Animation runs.
     * Here the sprites of the animation has to be drawn according to the animation logic.
     *
     * @param d  DrawSurface to draw the sprites of the Animation on
     * @param dt amount of seconds passed since the last frame
     */
    void doOneFrame(DrawSurface d, double dt);

    /**
     * stopping condition to the running of the Animation.
     *
     * @return true, if the animation running has to stop. false, if it has to continue.
     */
    boolean shouldStop();
}