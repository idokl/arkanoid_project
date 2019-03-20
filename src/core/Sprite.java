package core;

import biuoop.DrawSurface;

/**
 * core.Sprite interface - draw the sprites on the screen and notify them the passed time.
 */
public interface Sprite {

    /**
     * draw the sprite to the screen.
     *
     * @param d - biuoop.DrawSurface to draw the sprite on
     */
    void drawOn(DrawSurface d);

    /**
     * notify the sprite that time has passed.
     *
     * @param dt - amount of seconds passed since the last call (i.e. last frame)
     */
    void timePassed(double dt);
}
