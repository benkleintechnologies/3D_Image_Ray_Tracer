package primitives;

public class Vector extends Point {

    public Vector(Double3 endpoint){
        super(endpoint);

        Double3 zero = new Double3(0,0,0);
        if (this.equals(zero)){
            throw new IllegalArgumentException("Cannot create a zero vector.");
        }
    }

    public Vector(double num1, double num2, double num3){
        super(num1,num2,num3);

        Double3 zero = new Double3(0,0,0);
        if (this.equals(zero)){
            throw new IllegalArgumentException("Cannot create a zero vector.");
        }
    }

}
