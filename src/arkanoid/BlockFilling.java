package arkanoid;

import biuoop.DrawSurface;

/**
 * filling of a block (image/color).
 */
public interface BlockFilling {
    /**
     * draw filling of a block.
     *
     * @param x            - the x coordinate of the upper-left point of the block.
     * @param y            - the y coordinate of the upper-left point of the block.
     * @param surface      - DrawSurface to draw on
     * @param numberOfHits - current number of hit-points
     */
    void drawFilling(int x, int y, DrawSurface surface, int numberOfHits);
}
