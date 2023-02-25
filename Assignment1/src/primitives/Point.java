package primitives;

/**
 * Class Point is the basic class representing a point in Cartesian
 * 3-Dimensional coordinate system.
 * @author Eli Hawk and Binyamin Klein
 * 563385586 & 576708589
 */
public class Point {
    /** Point of type Double3 */
    final Double3 point;

    /**
     * Constructor to initialize Point based on Double3
     * @param point in 3-dimensional space
     */
    public Point(Double3 point) {
        this.point = point;
    }

    /**
     * Constructor to initialize Point based on three numbers of type double
     * @param num1 first point
     * @param num2 second point
     * @param num3 third point
     */
    public Point(double num1, double num2, double num3){
        this.point = new Double3(num1, num2, num3);
    }

    /**
     * Getter for point variable
     * @return point
     */
    public Double3 getPoint() {
        return point;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Point)) return false;
        Point other = (Point) obj;
        return this.point.equals(other.point);
    }

    @Override
    public String toString() {
        return point.toString();
    }
}
