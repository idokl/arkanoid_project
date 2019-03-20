package geometry;

import java.util.ArrayList;
import java.util.List;

/**
 * geometry.Rectangle - creates a rectangle (upper-left point, width, height).
 */
public class Rectangle {

    private Point upLeft;
    private double widthRec;
    private double heightRec;

    /**
     * Create a new rectangle with location and width/height.
     *
     * @param upperLeft - point of upper-left corner of rectangle
     * @param width     - width of rectangle
     * @param height    - height of rectangle
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upLeft = upperLeft;
        this.widthRec = width;
        this.heightRec = height;
    }

    /**
     * @return left line of rectangle.
     */
    public Line getLeftLine() {
        return new Line(this.getUpperLeft(),
                new Point(this.getUpperLeft().getX(), this.getUpperLeft().getY() + this.getHeight()));
    }

    /**
     * @return right line of rectangle.
     */
    public Line getRightLine() {
        return new Line(new Point(this.getUpperLeft().getX() + this.getWidth(), this.getUpperLeft().getY()),
                new Point(this.getUpperLeft().getX() + this.getWidth(), this.getLeftLine().end().getY()));
    }

    /**
     * @return upper line of rectangle.
     */
    public Line getUpLine() {
        return new Line(this.getRightLine().start(), getLeftLine().start());
    }

    /**
     * @return lower line of rectangle.
     */
    public Line getDownLine() {
        return new Line(getRightLine().end(), getLeftLine().end());
    }

    /**
     * @param line - lines of rectangle
     * @return List of intersection points with the specified line (possibly empty).
     */
    public java.util.List intersectionPoints(Line line) {
        List<Point> intersectPointList = new ArrayList<>();
        //if right line is intersecting - add it to list.
        if (getRightLine().isIntersecting(line)) {
            intersectPointList.add(getRightLine().intersectionWith(line));
        }
        //if left line is intersecting - add it to list.
        if (getLeftLine().isIntersecting(line)) {
            intersectPointList.add(getLeftLine().intersectionWith(line));
        }
        //if upper line is intersecting - add it to list.
        if (getUpLine().isIntersecting(line)) {
            intersectPointList.add(getUpLine().intersectionWith(line));
        }
        //if lower line is intersecting - add it to list.
        if (getDownLine().isIntersecting(line)) {
            intersectPointList.add(getDownLine().intersectionWith(line));
        } //return list.
        return intersectPointList;
    }

    /**
     * @return width of rectangle
     */
    public double getWidth() {
        return this.widthRec;
    }

    /**
     * @return height of rectangle
     */
    public double getHeight() {
        return this.heightRec;
    }

    /**
     * @return upper-left point of the rectangle.
     */
    public Point getUpperLeft() {
        return this.upLeft;
    }
}