package arkanoid;

import core.HitListener;
import core.Counter;

/**
 * a arkanoid.BlockRemover is in charge of removing blocks from the gameLevel, as well as keeping count
 * of the number of blocks that remain.
 */

public class BlockRemover implements HitListener {
    private GameLevel gameLevel;
    //Counter that keep track of the number of impermanent blocks that remained in the game level.
    //(impermanent blocks are the blocks that the player has to remove for winning in the game level).
    private Counter remainingBlocks;

    /**
     * Constructor.
     *
     * @param gameLevel     the level of the game that uses this BlockRemover to remove Blocks
     * @param removedBlocks Counter that keep track of the number of impermanent Blocks that remained in the game
     */
    public BlockRemover(GameLevel gameLevel, Counter removedBlocks) {
        this.gameLevel = gameLevel;
        this.remainingBlocks = removedBlocks;
    }

    /**
     * @return the Game Level that uses this BlockRemover
     */
    public GameLevel getGameLevel() {
        return this.gameLevel;
    }

    // Blocks that are hit and reach 0 hit-points should be removed
    // from the gameLevel. Remember to remove this listener from the block
    // that is being removed from the gameLevel.

    /**
     * @param beingHit - block that was hit
     * @param hitter   - the ball that hit the block
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getNumberOfHits() == -1) {
            beingHit.removeFromGame(getGameLevel());
            beingHit.removeHitListener(this);
            this.remainingBlocks.decrease(1);
        }
    }
}