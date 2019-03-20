package geometry;

/**
 * geometry.Point - creates a point (x coordinate, y coordinate).
 */
public class Point {

    private double x;
    private double y;

    /**
     * @param x - x coordinate
     * @param y - y coordinate
     */
    // constructor
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @param other - the other point
     * @return distance between two points
     */
    // distance -- return the distance of this point to the other point
    public double distance(Point other) {
        double dx = this.x - other.getX();
        double dy = this.y - other.getY();
        return Math.sqrt((dx * dx) + (dy * dy));
    }

    /**
     * @param other - the other point
     * @return if two points are equal
     */
    // equals -- return true if the points are equal, false otherwise
    public boolean equals(Point other) {
        return (this.x == other.getX() && this.y == other.getY());
    }

    /**
     * @return x coordinate
     */
    // Return the x and y values of this point
    public double getX() {
        return this.x;
    }

    /**
     * @return y coordinate
     */
    public double getY() {
        return this.y;
    }
}
