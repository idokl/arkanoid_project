package core;

import arkanoid.Ball;
import geometry.Point;
import geometry.Rectangle;

/**
 * collidable interface - define which objects are collidable.
 */
public interface Collidable {
    // Return the "collision shape" of the object.

    /**
     * @return collision with rectangle
     */
    Rectangle getCollisionRectangle();


    // Notify the object that we collided with it at collisionPoint with
    // a given velocity.
    // The return is the new velocity expected after the hit (based on
    // the force the object inflicted on us).

    /**
     * @param hitter          the ball that hit the Collidable
     * @param collisionPoint  - the ball's collision point with the Collidable
     * @param currentVelocity - the ball's velocity before hitting the Collidable
     * @return velocity after hit
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}