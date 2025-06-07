/**
 * Represents a 2D point with x and y coordinates.
 */
public class Point {
    private final double x;
    private final double y;

    /**
     * Constructor to initialize a point with given coordinates.
     * @param x The x-coordinate of the point
     * @param y The y-coordinate of the point
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the x-coordinate of the point.
     * @return The x-coordinate of the point
     */
    public double getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of the point.
     * @return The y-coordinate of the point
     */
    public double getY() {
        return y;
    }
}