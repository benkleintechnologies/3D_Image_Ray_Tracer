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
    public Vector(Double3 endpoint){
        super(endpoint);

        Double3 zero = new Double3(0,0,0);
        if (this.equals(zero)){
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

        Double3 zero = new Double3(0,0,0);
        if (this.equals(zero)){
            throw new IllegalArgumentException("Cannot create a zero vector.");
        }
    }

}
