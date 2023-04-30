package primitives;

/**
 * Class Point is the basic class representing a point in Cartesian
 * 3-Dimensional coordinate system.
 * @author Eli Hawk and Binyamin Klein
 * 563385586 & 576708589
 */
public class Point {
    public static final Point ZERO = new Point(new Double3(0));
    /** Point of type Double3 */
    final Double3 xyz;

    /**
     * Constructor to initialize Point based on Double3
     * @param xyz in 3-dimensional space
     */
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    /**
     * Constructor to initialize Point based on three numbers of type double
     * @param num1 first point
     * @param num2 second point
     * @param num3 third point
     */
    public Point(double num1, double num2, double num3){
        this.xyz = new Double3(num1, num2, num3);
    }

    /**
     * Subtract one point from another
     * @param p second point
     * @return Vector from second point to this point
     */
    public Vector subtract(Point p){
        return new Vector(xyz.subtract(p.xyz));
    }

    /**
     * Add a point to this point
     * @param p second point
     * @return new Point which is the sum of these two points
     */
    public Point add(Point p){
        return new Point(p.xyz.add(this.xyz));
    }

    /**
     * Calculates the distance between two points, squared
     * @param p second point
     * @return distance squared as double
     */
    public double distanceSquared(Point p){
        Double3 dif = xyz.subtract(p.xyz);
        Double3 prod = dif.product(dif);
        return (prod.d1 + prod.d2 + prod.d3);
    }

    /**
     * Calculates the distance between two points
     * @param p second point
     * @return distance as double
     */
    public double distance(Point p){
        return Math.sqrt(distanceSquared(p));
    }

    /**
     * Getter for x-coordinate
     * @return the x-coordinate of the point
     */
    public double getX(){
        return xyz.d1;
    }

    /**
     * Getter for y-coordinate
     * @return the y-coordinate of the point
     */
    public double getY(){
        return xyz.d2;
    }

    /**
     * Getter for z-coordinate
     * @return the z-coordinate of the point
     */
    public double getZ(){
        return xyz.d3;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Point)) return false;
        Point other = (Point) obj;
        return this.xyz.equals(other.xyz);
    }

    @Override
    public String toString() {
        return xyz.toString();
    }
}
