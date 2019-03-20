package io;

import arkanoid.Block;

/**
 * interface of a factory-object that is used for creating blocks.
 */
public interface BlockCreator {
    /**
     * Create a block at the specified location.
     *
     * @param xpos - x coordinate of the upper-left point of the block
     * @param ypos - y coordinate of the upper-left point of the block
     * @return a suitable block in this position
     */
    Block create(int xpos, int ypos);
}