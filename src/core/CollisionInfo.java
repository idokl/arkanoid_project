package core;

import geometry.Point;

/**
 * Collisioninfo - get info of collision (point and object).
 */
public class CollisionInfo {
    private Point collision;
    private Collidable c1;

    /**
     * @param cInfo - collision point
     * @param ca    - collision object
     */
    public CollisionInfo(Point cInfo, Collidable ca) {
        this.collision = cInfo;
        this.c1 = ca;
    }

    /**
     * @return the point at which the collision occurs.
     */
    public Point collisionPoint() {
        return this.collision;
    }

    /**
     * @return the collidable object involved in the collision.
     */
    public Collidable collisionObject() {
        return this.c1;
    }
}