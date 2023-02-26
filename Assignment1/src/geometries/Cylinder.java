package geometries;

import primitives.Ray;

/**
 * Class Cylinder is the class representing a cylinder (as a type of Tube) in Cartesian 3-Dimensional coordinate system.
 * @author Eli Hawk and Binyamin Klein
 * 563385586 & 576708589
 */
public class Cylinder extends Tube{
    double height;

    /**
     * Constructor for Cylinder which calls super constructor and sets height
     * @param axis of tube
     * @param r radius of the tube
     * @param h height
     */
    public Cylinder(Ray axis, double r, double h) {
        super(axis, r);
        height = h;
    }

    /**
     * Getter for height
     * @return height
     */
    public double getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return "Cylinder: " + "axis ray = " + axisRay + ", radius = " + radius + ", height = " + height;
    }
}
