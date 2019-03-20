package arkanoid;

import core.Collidable;
import core.Sprite;
import core.HitNotifier;
import core.HitListener;
import core.Velocity;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import biuoop.DrawSurface;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * Block class - creates block (rectangle, colour, number of hits).
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private Rectangle rec;
    private int numberOfHits;
    private Image img;
    private List<HitListener> hitListeners;
    private Boolean isImage;
    private Color stroke;
    private Map<Integer, Color> colorMap = new HashMap<Integer, Color>();
    private Boolean isColor;
    private Map<Integer, Color> defaultColorMap = new HashMap<Integer, Color>();
    private BlockFilling blockFilling;

    /**
     * @param r1           - rectangle
     * @param stroke       - color of the block's border
     * @param blockColor   - color of the block for every value of hit-points
     * @param numberOfHits - number of hits needed
     */
    public Block(Rectangle r1, Color stroke, Map<Integer, Color> blockColor, int numberOfHits) {
        this.rec = r1;
        this.colorMap = blockColor;
        this.stroke = stroke;
        this.numberOfHits = numberOfHits;
        this.hitListeners = new ArrayList<>();
        this.isImage = false;
        this.isColor = false;
    }

    /**
     * @param r1              - rectangle
     * @param defaultImageMap - map from hit-points to file with the suitable image
     * @param stroke          - color of the block's border
     * @param numberOfHits    - number of hits needed
     */
    public Block(Rectangle r1, Map<Integer, File> defaultImageMap, Color stroke, int numberOfHits) {
        this.rec = r1;
        this.numberOfHits = numberOfHits;
        this.hitListeners = new ArrayList<>();
        try {
            this.img = ImageIO.read((defaultImageMap.get(this.numberOfHits)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.isImage = true;
        this.stroke = stroke;
        this.isColor = false;
    }

    /**
     * @param r1           - rectangle
     * @param numberOfHits - number of hits needed
     * @param blockColor   - color of the block
     * @param stroke       - color of the block's border
     */
    public Block(Rectangle r1, int numberOfHits, Color blockColor, Color stroke) {
        this.rec = r1;
        this.defaultColorMap.put(1, blockColor);
        this.stroke = stroke;
        this.numberOfHits = numberOfHits;
        this.hitListeners = new ArrayList<>();
        this.isImage = false;
        this.isColor = true;

    }

    /**
     * @param r1           - rectangle shape of the block
     * @param blockFilling - filling of the block that can draw itself (so far just colorFilling is implemented,
     *                     but it can be imageFilling, too..)
     * @param stroke       - color of the block's border
     * @param numberOfHits - number of hits needed
     */
    public Block(Rectangle r1, BlockFilling blockFilling, Color stroke, int numberOfHits) {
        this.rec = r1;
        this.numberOfHits = numberOfHits;
        this.hitListeners = new ArrayList<>();
        this.blockFilling = blockFilling;
        this.stroke = stroke;
    }


    /**
     * @return rectangle
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return this.rec;
    }

    /**
     * notify the block that a ball collided with it at collisionPoint with a given velocity:
     * calculate new Velocity for the hitter.
     * decrease the block's numberOfHits (=number of hits needed to remove it).
     *
     * @param hitter          - the ball that collided with this block
     * @param collisionPoint  - the ball's collision point with the block
     * @param currentVelocity - the ball's velocity before hitting the block
     * @return velocity (of the hitter)  after hit
     * (based on position and direction of the collision)
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        //current velocity and it's coordinates.
        Velocity oldVel = currentVelocity;
        double dx = currentVelocity.getDeltaX();
        double dy = currentVelocity.getDeltaY();
        //coordinates of collision point.
        double collisionPointX = collisionPoint.getX();
        double collisionPointY = collisionPoint.getY();
        //borders of block.
        Line downLine = this.rec.getDownLine();
        Line upLine = this.rec.getUpLine();
        Line rightLine = this.rec.getRightLine();
        Line leftLine = this.rec.getLeftLine();
        //collision with the top of the block.
        if (collisionPointX >= downLine.end().getX()
                && (collisionPointX <= downLine.start().getX())
                && collisionPointY == upLine.start().getY()) {
            currentVelocity = new Velocity(dx, -dy);
            //collision with the bottom of the block.
        } else if (collisionPointX >= upLine.end().getX()
                && (collisionPointX <= upLine.start().getX())
                && collisionPointY == downLine.start().getY()) {
            currentVelocity = new Velocity(dx, -dy);
            //collision with the left hand side of the block.
        } else if (collisionPointY >= leftLine.start().getY()
                && (collisionPointY <= leftLine.end().getY())
                && collisionPointX == leftLine.start().getX()) {
            currentVelocity = new Velocity(-dx, dy);
            //collision with the right hand side of the block.
        } else if (collisionPointY >= rightLine.start().getY()
                && (collisionPointY <= rightLine.end().getY())
                && collisionPointX == rightLine.start().getX()) {
            currentVelocity = new Velocity(-dx, dy);
        }
        //if velocity changed - reduce 1 hit for the block.
        if (oldVel != currentVelocity) {
            this.numberOfHits--;
        }
        this.notifyHit(hitter);

        //return new velocity after hit.
        return currentVelocity;
    }

    /**
     * @return current number of hits left
     */
    public int getNumberOfHits() {
        //if no hits are left - return -1.
        if (this.numberOfHits <= 0) {
            this.numberOfHits = -1;
        }
        return this.numberOfHits;
    }

    /**
     * draw the block on the given DrawSurface.
     *
     * @param surface - window that contains the block
     */
    @Override
    public void drawOn(DrawSurface surface) {
        //get size and coordinates of block
        int width = (int) rec.getWidth();
        int height = (int) rec.getHeight();
        int x = (int) rec.getLeftLine().start().getX();
        int y = (int) rec.getLeftLine().start().getY();

        if (this.blockFilling == null) {

            if ((!this.isImage) && (!this.isColor)) {
                //get colour of block
                surface.setColor(this.colorMap.get(this.numberOfHits));
                surface.fillRectangle(x, y, width, height);
            } else if (this.isImage) {
                surface.drawImage(x, y, this.img);
            } else {
                surface.setColor(this.defaultColorMap.get(1));
                surface.fillRectangle(x, y, width, height);
            }

        } else {
            this.blockFilling.drawFilling(x, y, surface, this.numberOfHits);
            if (this.getNumberOfHits() >= 0) {
                surface.setColor(Color.white);
                surface.drawRectangle(x, y, width, height);
            }
        }
        if (this.stroke != null) {
            surface.setColor(this.stroke);
            surface.drawRectangle(x, y, width, height);
        }
    }

    /**
     * @return colour of block
     */
    public Map<Integer, Color> getColor() {
        return this.defaultColorMap;
    }

    /**
     * notify the block that time has passed.
     * ignored.
     *
     * @param dt - amount of seconds passed since the last frame
     */
    @Override
    public void timePassed(double dt) {

    }

    /**
     * add this block to a game.
     *
     * @param g - game to add this Block to
     */
    public void addToGame(GameLevel g) {
        g.addCollidable(this);
        g.addSprite(this);
    }

    /**
     * remove this block from a game.
     *
     * @param gameLevel - game to remove this Block from
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeCollidable(this);
        gameLevel.removeSprite(this);
    }

    /**
     * Add hl as a listener to hit events.
     *
     * @param hl - HitListener to add as a listener to hit events
     */
    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * Remove hl from the list of listeners to hit events.
     *
     * @param hl - HitListener to remove from the list of listeners to hit events
     */
    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * notify that the hitter Ball hit this Block.
     * This notification will be sent to all of the registered HitListener objects by calling their hitEvent method.
     *
     * @param hitter - the ball that hit this Block
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

}
