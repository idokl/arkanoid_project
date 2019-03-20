package arkanoid;

import core.Counter;
import core.HitListener;

/**
 * a BallRemover is in charge of removing balls from the game, as well as keeping count
 * of the number of balls that remained.
 */
public class BallRemover implements HitListener {
    private GameLevel gameLevel;
    //Counter that keep track of the remaining number of balls
    private Counter remainingBalls;

    /**
     * Constructor.
     *
     * @param gameLevel   - the Game Level that uses this BallRemover to remove Balls
     * @param removedBals - Counter that keep track of the number of balls that remained in the game (=didn't fall)
     */
    public BallRemover(GameLevel gameLevel, Counter removedBals) {
        this.gameLevel = gameLevel;
        this.remainingBalls = removedBals;
    }

    /**
     * @return the Game Level that uses this BallRemover
     */
    public GameLevel getGameLevel() {
        return this.gameLevel;
    }

    /**
     * @param beingHit - the block that is hit
     * @param hitter   - the Ball that's doing the hitting
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(this.getGameLevel());
        this.remainingBalls.decrease(1);

    }
}