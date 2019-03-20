package core;

/**
 * The HitNotifier interface indicate that objects that implement it send notifications when they are being hit.
 * The HitNotifuer has to sends the notifications to every HitListener that was registered to its notifications.
 * It has to do it by calling their "hitEvent" method.
 */
public interface HitNotifier {
    /**
     * Add HitListener as a listener to hit events.
     * (It will enable to this HitNotifier to notify the HitListener that this HitNotifier has been hit
     * by calling to the hitEvent method of the HitListener).
     *
     * @param hl - HitListener to add as a listener to hit events
     */
    void addHitListener(HitListener hl);

    /**
     * Remove HitListener from the list of listeners to hit events.
     *
     * @param hl - HitListener to remove from the list of listeners to hit events
     */
    void removeHitListener(HitListener hl);
}