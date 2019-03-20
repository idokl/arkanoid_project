package arkanoid;

import core.Sprite;
import biuoop.DrawSurface;

import java.util.ArrayList;
import java.util.List;

/**
 * arkanoid.SpriteCollection - collection of sprites.
 */
public class SpriteCollection {

    private List<Sprite> arrSprites;

    /**
     * create collection of sprites.
     */
    public SpriteCollection() {
        this.arrSprites = new ArrayList<>();
    }

    /**
     * add sprite to this collection.
     *
     * @param s - sprite to add to this collection
     */
    public void addSprite(Sprite s) {
        this.arrSprites.add(s);
    }

    /**
     * remove sprite from this Collection.
     *
     * @param s sprite to remove from this collection
     */
    public void removeSprite(Sprite s) {
        this.arrSprites.remove(s);
    }

    /**
     * call timePassed() on all sprites.
     *
     * @param dt amount of seconds passed since the last frame
     */
    public void notifyAllTimePassed(double dt) {
        for (int i = 0; i < this.arrSprites.size(); i++) {
            Sprite newSprite = this.arrSprites.get(i);
            newSprite.timePassed(dt);
        }
    }

    /**
     * call drawOn(d) on all sprites.
     *
     * @param d - draw the sprite on the screen
     */
    public void drawAllOn(DrawSurface d) {
        for (int i = 0; i < this.arrSprites.size(); i++) {
            Sprite newSprite = this.arrSprites.get(i);
            newSprite.drawOn(d);
        }
    }
}