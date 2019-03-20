package core;

import geometry.Point;

/**
 * core.Velocity - specifies the change in position on the `x` and the `y` axes.
 */
public class Velocity {
    private double deltaX;
    private double deltaY;

    // constructor

    /**
     * @param dx - movement in x coordinates
     * @param dy - movement in y coordinates
     */
    public Velocity(double dx, double dy) {
        this.deltaX = dx;
        this.deltaY = dy;
    }
    //convert movement in x and y coordinates to movement by angle and absolute movement

    /**
     * @param angle - get the angle to move the ball
     * @param speed - get the speed to move the ball
     * @return the new core.Velocity of the ball
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double dx = Math.sin(Math.toRadians(angle)) * speed;
        double dy = Math.cos(Math.toRadians(angle)) * speed;
        return new Velocity(dx, dy);
    }

    /**
     * @return movement in x coordinates
     */
    public double getDeltaX() {
        return this.deltaX;
    }

    /**
     * @return movement in y coordinates
     */
    public double getDeltaY() {
        return this.deltaY;
    }

    // Take a point with position (x,y) and return a new point
    // with position (x+dx, y+dy)

    /**
     * @param p  - point that represents the location of an object that has this velocity
     * @param dt - amount of time units that have passed
     * @return new point of the object. (represents the location of the object after dt units of time)
     */
    public Point applyToPoint(Point p, double dt) {
        return new Point(p.getX() + this.getDeltaX() * dt, p.getY() + this.getDeltaY() * dt);
    }
}