package arkanoid;

import core.Collidable;
import core.CollisionInfo;
import geometry.Line;
import geometry.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * arkanoid.GameEnvironment - create the environment where the game takes place.
 */
public class GameEnvironment {

    private List<Collidable> arrCollidable;

    /**
     * arkanoid.GameEnvironment - list of collidable objects.
     */
    public GameEnvironment() {
        this.arrCollidable = new ArrayList<>();
    }

    /**
     * add the given collidable to the environment.
     *
     * @param c - collidable object.
     */
    public void addCollidable(Collidable c) {
        if (c != null) {
            this.arrCollidable.add(c);
        }
    }

    /**
     * remove the given collidable from the environment.
     *
     * @param c - collidable object.
     */
    public void removeCollidable(Collidable c) {
        this.arrCollidable.remove(c);
    }

    /**
     * @param trajectory - line from ball to collision object
     * @return closest collision object and the collision point
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        //set collision point and collidible object to 'null'.
        Point closetCollisionPoint = null;
        Collidable closetCollide = null;
        int firstIndex = 0, secondIndex = 0;
        //for each object check if the ball is going to collide with it in it's current track.
        for (Object object : this.arrCollidable) {
            firstIndex++;
            closetCollide = (Collidable) object;
            closetCollisionPoint = trajectory.closestIntersectionToStartOfLine(closetCollide.getCollisionRectangle());
            //if a collision point was found - break.
            if (closetCollisionPoint != null) {
                break;
            }
        }
        //if there's no collision point in the ball's current track - return 'null'.
        if (closetCollisionPoint == null) {
            return null;
        } //compare each two objects to find the closest intersecting object and intersection points.
        for (Object object2 : this.arrCollidable) {
            secondIndex++;
            Collidable collideObject = (Collidable) object2;
            Point collisionPoint = trajectory.closestIntersectionToStartOfLine(closetCollide.getCollisionRectangle());
            //continue if one of the objects has no intersection point or if two compared objects are the same one.
            if ((collisionPoint == null) || (firstIndex == secondIndex)) {
                continue;
            } //if a closer intersection point was found - update it.
            if (trajectory.start().distance(collisionPoint) < trajectory.start().distance(closetCollisionPoint)) {
                closetCollisionPoint = collisionPoint;
                closetCollide = collideObject;
            }
        } //return closest colliding object and collision point found.
        return new CollisionInfo(closetCollisionPoint, closetCollide);
    }
}