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

        if (xyz.equals(Double3.ZERO)){
            throw new IllegalArgumentException("Cannot create a zero vector.");
        }
    }

    /**
     * Add two vectors
     * @param v second vector
     * @return new Vector which is the sum
     */
    public Vector add(Vector v) {
        return new Vector(v.xyz.add(this.xyz));
    }

    /**
     * Multiplies vector by a scalar
     * @param s scalar
     * @return new scaled vector
     */
    public Vector scale(double s){
        return new Vector(xyz.scale(s));
    }

    /**
     * Calculates dot product between two vectors using algebraic formula
     * @param v second vector
     * @return the dot product as a double
     */
    public double dotProduct(Vector v){
        //Need to change not to use product of Double3 which creates new object
        //Double3 prod = this.point.product(v.point);
        //return (prod.d1 + prod.d2 + prod.d3);

        double term1 = this.xyz.d1 * v.xyz.d1;
        double term2 = this.xyz.d2 * v.xyz.d2;
        double term3 = this.xyz.d3 * v.xyz.d3;
        return term1 + term2 + term3;
    }

    /**
     * Calculates cross product between two vectors using algebraic formula
     * @param v second vector
     * @return the norm of the two vectors
     */
    public Vector crossProduct(Vector v){
        return new Vector(
                (this.xyz.d2 * v.xyz.d3) - (this.xyz.d3 * v.xyz.d2),
                -((this.xyz.d1 * v.xyz.d3) - (this.xyz.d3 * v.xyz.d1)),
                (this.xyz.d1 * v.xyz.d2) - (this.xyz.d2 * v.xyz.d1));
    }

    /**
     * Gets distance squared using formula: v⋅v = |v|^2
     * @return the length of the vector, squared
     */
    public double lengthSquared(){
        //I think we aren't supposed to do this
        //return distanceSquared(new Point(Double3.ZERO));

        return this.dotProduct(this);
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
        double term1 = xyz.d1/length;
        double term2 = xyz.d2/length;
        double term3 = xyz.d3/length;
        return new Vector(term1,term2,term3);
    }

    /**
     * Creates a vector orthogonal to the vector
     * @return the vector
     */
    public Vector getNormal(){
        return new Vector(-xyz.d2,xyz.d1,0);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Vector)) return false;
        Vector other = (Vector) obj;
        return this.xyz.equals(other.xyz);
    }
}
