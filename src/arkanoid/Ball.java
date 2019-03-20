package arkanoid;

import core.CollisionInfo;
import core.Sprite;
import core.Velocity;
import geometry.Line;
import geometry.Point;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * arkanoid.Ball class - creates ball (size, center point, colour, velocity, borders of bouncing).
 */
public class Ball implements Sprite {
    private Point c1;
    private java.awt.Color ballColor;
    private int radios;
    private Velocity vel;
    private GameEnvironment gmBall;

    /**
     * @param center - center point of the ball
     * @param r      - radios of ball
     * @param color  - colour of ball
     * @param g      - game environment of ball
     */

    public Ball(Point center, int r, java.awt.Color color, GameEnvironment g) {
        this.c1 = center;
        this.radios = r;
        this.ballColor = color;
        this.gmBall = g;
    }

    /**
     * @param x1    - x coordinate of ball's center
     * @param y1    - y coordinate of ball's center
     * @param r     - radios of ball
     * @param color - colour of ball
     * @param g     - game environment of ball
     */
    public Ball(int x1, int y1, int r, java.awt.Color color, GameEnvironment g) {
        this.c1 = new Point(x1, y1);
        this.radios = r;
        this.ballColor = color;
        this.gmBall = g;

    }

    /**
     * @param x1 - x coordinate of ball's center
     * @param y1 - y coordinate of ball's center
     */
    public void setXY(double x1, double y1) {
        this.c1 = new Point(x1, y1);
    }

    /**
     * @return x coordinate of ball's center
     */
    public int getX() {
        return (int) this.c1.getX();
    }

    /**
     * @return y coordinate of ball's center
     */
    public int getY() {
        return (int) this.c1.getY();
    }

    /**
     * @return radios of ball
     */
    public int getSize() {
        return this.radios;
    }

    /**
     * @return colour of ball
     */
    public java.awt.Color getColor() {
        return this.ballColor;
    }

    /**
     * draw the ball on the given DrawSurface.
     *
     * @param surface - window that contains the ball
     */
    @Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(Color.black);
        surface.drawCircle(this.getX(), this.getY(), this.getSize());
        surface.setColor(this.getColor());
        surface.fillCircle(this.getX(), this.getY(), this.getSize());
    }

    /**
     * @param v - velocity of ball
     */
    public void setVelocity(Velocity v) {
        this.vel = new Velocity(v.getDeltaX(), v.getDeltaY());
    }

    /**
     * @param dx - movement by x
     * @param dy - movement by y
     */
    public void setVelocity(double dx, double dy) {
        this.vel = new Velocity(dx, dy);
    }

    /**
     * @return velocity of ball
     */
    public Velocity getVelocity() {
        return this.vel;
    }

    /**
     * move the ball one step.
     *
     * @param dt - amount of seconds passed since the last call (i.e. last frame)
     */
    public void moveOneStep(double dt) {
        Line nextStep = new Line(this.c1, this.getVelocity().applyToPoint(this.c1, dt));
        CollisionInfo c = this.gmBall.getClosestCollision(nextStep);
        //if ball doesn't collide in the next step - move it to the next point, otherwise change it's velocity.
        if (c == null) {
            this.c1 = this.getVelocity().applyToPoint(this.c1, dt);
        } else {
            this.setVelocity(c.collisionObject().hit(this, c.collisionPoint(), this.getVelocity()));
        }
    }

    /**
     * notify the ball that time has passed.
     *
     * @param dt - amount of seconds passed since the last frame
     */
    @Override
    public void timePassed(double dt) {
        this.moveOneStep(dt);
    }

    /**
     * @param g - gameLevel environment of ball.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    /**
     * @param gameLevel - gameLevel environment of ball.
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeSprite(this);
    }
}