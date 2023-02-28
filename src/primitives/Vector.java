package primitives;

/**
 * Class Vector is the basic class representing a vector starting at the origin in Cartesian
 * 3-Dimensional coordinate system.
 * @author Eli Hawk and Binyamin Klein
 * 563385586 & 576708589
 */
public class Vector extends Point {

    /**
     * Constructor to initialize Vector based on a Double3
     * @param endpoint the end of the vector
     */
    Vector(Double3 endpoint){
        super(endpoint);

        if (endpoint.equals(Double3.ZERO)){
            throw new IllegalArgumentException("Cannot create a zero vector.");
        }
    }

    /**
     * Constructor to initialize Vector based on three numbers of type double
     * @param num1 first point
     * @param num2 second point
     * @param num3 third point
     */
    public Vector(double num1, double num2, double num3){
        super(num1,num2,num3);

        if (getPoint().equals(Double3.ZERO)){
            throw new IllegalArgumentException("Cannot create a zero vector.");
        }
    }

    /**
     * Add two vectors
     * @param v second vector
     * @return new Vector which is the sum
     */
    public Vector add(Vector v) {
        return new Vector(v.point.add(this.point));
    }

    /**
     * Multiplies vector by a scalar
     * @param s scalar
     * @return new scaled vector
     */
    public Vector scale(double s){
        return new Vector(point.scale(s));
    }

    /**
     * Calculates dot product between two vectors using algebraic formula
     * @param v second vector
     * @return the dot product as a double
     */
    public double dotProduct(Vector v){
        Double3 prod = this.point.product(v.point);
        return (prod.d1 + prod.d2 + prod.d3);
    }

    /**
     * Calculates cross product between two vectors using algebraic formula
     * @param v second vector
     * @return the norm of the two vectors
     */
    public Vector crossProduct(Vector v){
        double a1 = this.point.d1;
        double a2 = this.point.d2;
        double a3 = this.point.d3;
        double b1 = v.point.d1;
        double b2 = v.point.d2;
        double b3 = v.point.d3;
        double term1 = (a2 * b3) - (a3 * b2);
        double term2 = -((a1 * b3) - (a3 * b1));
        double term3 = (a1 * b2) - (a2 * b1);
        return new Vector(term1,term2,term3);
    }

    /**
     * Gets distance squared from origin to endpoint of vector
     * @return the length of the vector, squared
     */
    public double lengthSquared(){
        return distanceSquared(new Point(Double3.ZERO));
    }

    /**
     * Square root of length squared
     * @return length of the vector
     */
    public double length(){
        return Math.sqrt(lengthSquared());
    }

    /**
     * Divides each component of vector by the length of the vector
     * @return a unit vector in the same direction as the vector
     */
    public Vector normalize(){
        double length = length();
        double term1 = point.d1/length;
        double term2 = point.d2/length;
        double term3 = point.d3/length;
        return new Vector(term1,term2,term3);
    }
}
