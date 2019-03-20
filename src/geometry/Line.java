package geometry;

import java.util.List;

/**
 * geometry.Line - create lines and check if any of them are intersecting.
 */
public class Line {
    private Point p1;
    private Point p2;

    // constructors

    /**
     * geometry.Line constructor with 2 points.
     *
     * @param start get the start point
     * @param end   get the end point
     */
    public Line(Point start, Point end) {
        this.p1 = start;
        this.p2 = end;
    }

    /**
     * geometry.Line constructor with 4 double.
     *
     * @param x1 get the first x
     * @param y1 get the first y
     * @param x2 get the second x
     * @param y2 get the seocnd y
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.p1 = new Point(x1, y1);
        this.p2 = new Point(x2, y2);
    }

    /**
     * @return the length of the line - distance between two points
     */
    public double length() {
        return start().distance(end());
    }

    /**
     * @return the middle point of the line
     */
    public Point middle() {
        return new Point(((this.start().getX() + this.end().getX()) / 2),
                ((this.start().getY() + this.end().getY()) / 2));
    }

    /**
     * @return the start point of the line
     */
    public Point start() {
        return this.p1;
    }

    /**
     * @return the end point of the line
     */
    public Point end() {
        return this.p2;
    }

    /**
     * @param other - the other line
     * @return true if the lines intersect, false otherwise
     */
    public boolean isIntersecting(Line other) {
        if (this.intersectionWith(other) == null) {
            return false;
        }
        Point interSectXY = this.intersectionWith(other);
        double x = interSectXY.getX();
        double y = interSectXY.getY();
        Point thisStart = this.start();
        Point thisEnd = this.end();
        Point otherStart = other.start();
        Point otherEnd = other.end();

        //if the lines are intersecting - return true
        //if the lines aren't intersecting - return false
        return ((((x >= thisStart.getX()) && (x <= thisEnd.getX()))
                || ((x >= thisEnd.getX()) && (x <= thisStart.getX())))
                && (((x >= otherStart.getX()) && (x <= otherEnd.getX()))
                || ((x >= otherEnd.getX()) && (x <= otherStart.getX())))
                && (((y >= thisStart.getY()) && (y <= thisEnd.getY()))
                || ((y >= thisEnd.getY()) && (y <= thisStart.getY())))
                && ((y >= otherStart.getY()) && (y <= otherEnd.getY())
                || (y >= otherEnd.getY()) && (y <= otherStart.getY())));

    }

    /**
     * @param other - the other line
     * @return intersection point if the lines intersect, and null otherwise.
     */
    public Point intersectionWith(Line other) {
        double thisSlope = this.slope();
        double otherSlope = other.slope();
        Point thisEnd = this.end();
        Point otherStart = other.start();
        Point otherEnd = other.end();
        double thisStartX = this.start().getX();
        double thisStartY = this.start().getY();
        double otherStartX = otherStart.getX();
        double otherStartY = otherStart.getY();
        //calculate intersection point
        if (thisStartX == thisEnd.getX()) {
            if (otherStartX == otherEnd.getX()) {
                return null;
            } else {
                double x = thisStartX;
                double y = otherSlope * (x - otherStartX) + otherStartY;
                return new Point(x, y);
            }
        } else if (otherStartX == otherEnd.getX()) {
            double x = otherStartX;
            double y = thisSlope * (x - thisStartX) + thisStartY;
            return new Point(x, y);

        } else if (thisSlope != otherSlope) {
            double slopPoint = (otherSlope - thisSlope);
            double intersection = (((thisSlope * (-1) * thisStartX) + thisStartY)
                    - ((otherSlope * (-1) * otherStartX) + otherStartY));
            double x = intersection / slopPoint;
            double y = (thisSlope * (x - thisStartX)) + thisStartY;
            //return found intersection point
            return new Point(x, y);
        }
        //if the lines have the same slop they aren't intersecting - return false
        return null;
    }

    /**
     * @param other - the other line
     * @return equals -- return true if the lines are equal, false otherwise
     */
    public boolean equals(Line other) {
        //compare lines - if they're equal return true
        //otherwise, return false
        return (this.start().getX() == other.start().getX()) && (this.end().getY() == other.end().getY());
    }

    /**
     * @return slope of the line
     */
    public double slope() {
        return (this.start().getY() - this.end().getY()) / (this.start().getX() - this.end().getX());

    }

    // If this line does not intersect with the rectangle, return null.
    // Otherwise, return the closest intersection point to the
    // start of the line.

    /**
     * @param rect - get rectangle to compere with the line
     * @return geometry.Point - the point of the closet intersection to the start of the line
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        List a = rect.intersectionPoints(this);
        if (a.isEmpty()) {
            return null;
        }
        int lengthArrInt = a.size();
        Point minDist = (Point) a.get(0);
        for (int i = 1; i < lengthArrInt; i++) {
            if (this.start().distance((Point) a.get(i)) < this.start().distance(minDist)) {
                minDist = (Point) a.get(i);
            }
        }
        return minDist;
    }
}