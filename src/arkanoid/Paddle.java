package arkanoid;

import core.Collidable;
import core.HitListener;
import core.Sprite;
import core.Velocity;
import geometry.Line;
import geometry.Point;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.Color;

import geometry.Rectangle;

/**
 * paddle - the paddle on the bottom of the board (rectangle, colour, keyboard sensor).
 */
public class Paddle implements Collidable, Sprite, HitListener {
    private Rectangle rec;
    private Color paddleColor;
    private biuoop.KeyboardSensor keyboard;
    private int guiWidth;
    private int borderThickness;
    //units of speed: pixels per 1 second
    private int speed;

    /**
     * @param r1              - rectangle
     * @param c               - colour of paddle
     * @param keyboard        - keyboard sensor (enables to move the paddle left and right by using the keyboard)
     * @param guiWidth        - have the gui width, in order to make the paddle within
     * @param borderThickness - have the border thickness, in order not to cross them
     * @param speed           - speed of the paddle
     */
    public Paddle(Rectangle r1, Color c, biuoop.KeyboardSensor keyboard, int guiWidth, int borderThickness, int speed) {
        this.rec = r1;
        this.paddleColor = c;
        this.keyboard = keyboard;
        this.guiWidth = guiWidth;
        this.borderThickness = borderThickness;
        this.speed = speed;
    }

    /**
     * moveLeft  - moves the paddle 5 spaces left.
     *
     * @param dt - amount of seconds passed since the last call (i.e. last frame)
     */
    public void moveLeft(double dt) {
        int newX = (int) (this.rec.getUpperLeft().getX() - speed * dt);
        if (newX >= this.borderThickness) {
            this.rec = new Rectangle(new Point(newX,
                    this.rec.getUpperLeft().getY()), this.rec.getWidth(), this.rec.getHeight());
        } else {
            this.rec = new Rectangle(new Point(this.borderThickness,
                    this.rec.getUpperLeft().getY()), this.rec.getWidth(), this.rec.getHeight());
        }
    }

    /**
     * moveRight - moves the paddle right.
     *
     * @param dt - amount of seconds passed since the last call (i.e. last frame)
     */
    public void moveRight(double dt) {
        int newX = (int) (this.rec.getUpperLeft().getX() + speed * dt);
        if (newX <= this.guiWidth - this.borderThickness - this.rec.getWidth()) {
            this.rec = new Rectangle(new Point(newX,
                    this.rec.getUpperLeft().getY()), this.rec.getWidth(), this.rec.getHeight());
        } else {
            this.rec = new Rectangle(new Point(this.guiWidth - this.borderThickness - this.rec.getWidth(),
                    this.rec.getUpperLeft().getY()), this.rec.getWidth(), this.rec.getHeight());
        }
    }

    /**
     * timePassed - for each time unit check if left/right keys were clicked and move paddle if so.
     *
     * @param dt amount of seconds passed since the last frame
     */
    @Override
    public void timePassed(double dt) {
        //if the 'right' key was clicked and the paddle didn't reach the right end of the board - move it right.
        if (this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY)
                && ((int) rec.getRightLine().start().getX() < this.guiWidth - borderThickness)) {
            this.moveRight(dt);
        }
        //if the 'left' key was clicked and the paddle didn't reach the left end of the board - move it left.
        if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY)
                && ((int) rec.getLeftLine().start().getX() > this.borderThickness)) {
            this.moveLeft(dt);
        }
    }

    /**
     * draw the paddle on the given DrawSurface.
     *
     * @param surface - window that contains the paddle
     */
    @Override
    public void drawOn(DrawSurface surface) {
        //get size and coordinates of paddle
        int x = (int) rec.getLeftLine().start().getX();
        int y = (int) rec.getLeftLine().start().getY();
        int width = (int) rec.getWidth();
        int height = (int) rec.getHeight();
        //get colour of paddle
        surface.setColor(this.getColor());
        surface.fillRectangle(x, y, width, height);
        //set colour of paddle's border
        surface.setColor(Color.black);
        surface.drawRectangle(x, y, width, height);
    }

    /**
     * @return colour of paddle.
     */
    public Color getColor() {
        return this.paddleColor;
    }

    /**
     * @return "collision shape" of the object.
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return this.rec;
    }

    // Notify the object that we collided with it at collisionPoint with
    // a given velocity.
    // The return is the new velocity expected after the hit (based on
    // the force the object inflicted on us).

    /**
     * @param hitter          the ball that hit this Paddle
     * @param collisionPoint  - the ball's collision point with the paddle
     * @param currentVelocity - the ball's velocity before hitting the paddle
     * @return velocity after hit
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        //velocity before hit.
        double dx = currentVelocity.getDeltaX();
        double dy = currentVelocity.getDeltaY();
        //absolute value of velocity.
        double ballSpeed = (Math.sqrt(dx * dx + dy * dy));
        //coordinates of collision point of ball with paddle.
        double collisionPointX = collisionPoint.getX();
        double collisionPointY = collisionPoint.getY();
        //borders of paddle.
        Line downLine = this.rec.getDownLine();
        Line upLine = this.rec.getUpLine();
        Line leftLine = this.rec.getLeftLine();
        Line rightLine = this.rec.getRightLine();
        double upLeftX = this.rec.getUpperLeft().getX();
        double recWidth = this.rec.getWidth();
        //if ball hits the top of the paddle - paddle is divided to 5 regions (1 - left-most, 5 - right-most).
        if (collisionPointX >= downLine.end().getX()
                && (collisionPointX <= downLine.start().getX())
                && collisionPointY == upLine.start().getY()) {
            //if ball hits region 1 - it bounces back in an angel of -60 degrees.
            if (collisionPointX - upLeftX < (recWidth / 5)) {
                currentVelocity = Velocity.fromAngleAndSpeed(-120, ballSpeed);
                //if ball hits region 2 - it bounces back in an angel of -30 degrees.
            } else if (collisionPointX - upLeftX < (recWidth * 2 / 5)) {
                currentVelocity = Velocity.fromAngleAndSpeed(-150, ballSpeed);
                //if ball hits region 3 - it bounces back normally (like hitting a regular block).
            } else if (collisionPointX - upLeftX < (recWidth * 3 / 5)) {
                currentVelocity = new Velocity(dx, -dy);
                //if ball hits region 4 - it bounces back in an angel of 30 degrees.
            } else if (collisionPointX - upLeftX < (recWidth * 4 / 5)) {
                currentVelocity = Velocity.fromAngleAndSpeed(150, ballSpeed);
                //if ball hits region 5 - it bounces back in an angel of 60 degrees.
            } else {
                currentVelocity = Velocity.fromAngleAndSpeed(120, ballSpeed);
            }
            //collision with the left hand side of the paddle.
        } else if (collisionPointY >= leftLine.start().getY()
                && (collisionPointY <= leftLine.end().getY())
                && collisionPointX == leftLine.start().getX()) {
            currentVelocity = new Velocity(-dx, dy);
            //collision with the right hand side of the paddle.
        } else if (collisionPointY >= rightLine.start().getY()
                && (collisionPointY <= rightLine.end().getY())
                && collisionPointX == rightLine.start().getX()) {
            currentVelocity = new Velocity(-dx, dy);
        } //return new velocity.
        return currentVelocity;
    }

    /**
     * @param g - game environment of paddle
     */
    public void addToGame(GameLevel g) {
        g.addCollidable(this);
        g.addSprite(this);
    }

    /**
     * Remove this Paddle from a game.
     *
     * @param gameLevel gameLevel to remove this Paddle from
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeCollidable(this);
        gameLevel.removeSprite(this);
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {

    }
}
