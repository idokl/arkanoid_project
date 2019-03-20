package io;

import arkanoid.Block;
import core.Sprite;
import core.Velocity;

import java.util.List;

/**
 * The LevelInformation interface specifies the information required to fully describe a level.
 */
public interface LevelInformation {
    /**
     * @return initial number of balls in one turn of the level
     */
    int numberOfBalls();

    /**
     * @return The initial velocity of each ball (list of velocities)
     */
    // Note that initialBallVelocities().size() == numberOfBalls()
    List<Velocity> initialBallVelocities();

    /**
     * @return speed of the paddle
     */
    int paddleSpeed();

    /**
     * @return width of the paddle
     */
    int paddleWidth();

    /**
     * @return name of the level
     */
    // the level name will be displayed at the top of the screen.
    String levelName();

    /**
     * @return a sprite with the background of the level
     */
    Sprite getBackground();

    /**
     * @return The Blocks that make up this level
     * (each block contains its size, color and location).
     */
    List<Block> blocks();

    /**
     * @return the number of blocks that should be removed to end the level.
     */
    // Number of levels that should be removed
    // before the level is considered to be "cleared".
    // This number should be <= blocks.size();
    int numberOfBlocksToRemove();
}