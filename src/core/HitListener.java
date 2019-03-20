package core;

import arkanoid.Ball;
import arkanoid.Block;

/**
 * HitListener is an Object which tracks hit events of HitNotifiers in order to collect information, take action etc.
 * (It needs the ability to be notified of hit events.
 * Objects that want to be notified of hit events, should implement the HitListener interface,
 * and register themselves with a HitNotifier object using its addHitListener method).
 */
public interface HitListener {
    /**
     * This method is called whenever the beingHit Block is hit.
     *
     * @param beingHit - the block that is hit
     * @param hitter   - the arkanoid Ball that's doing the hitting
     */
    void hitEvent(Block beingHit, Ball hitter);
}