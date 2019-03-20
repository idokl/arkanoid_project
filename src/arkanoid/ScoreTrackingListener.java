package arkanoid;

import core.Counter;
import core.HitListener;

/**
 * Object that updates a counter of score when blocks are being hit and removed.
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;

    /**
     * Constructor.
     *
     * @param scoreCounter counter that maintains the score of the game and has to be updated
     *                     when blocks are being hit and removed
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * This method is called whenever the beingHit Block is hit (if this ScoreTrackingListener registered to this
     * Block notifications). It change the value of the currentScore Counter according to these rules:
     * a). hitting a block is worth 5 points,
     * b). destroying a block is worth additional 10 points.
     *
     * @param beingHit the block that is hit
     * @param hitter   the Ball that's doing the hitting
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        //hitting a block is worth 5 points
        this.currentScore.increase(5);
        //destroying a block is worth additional 10 points
        if (beingHit.getNumberOfHits() == -1) {
            this.currentScore.increase(10);
        }
    }
}