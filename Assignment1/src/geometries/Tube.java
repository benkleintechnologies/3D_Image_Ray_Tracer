package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Class Sphere is the basic class representing a Tube in the Cartesian
 * 3-Dimensional coordinate system.
 * @author Eli Hawk and Binyamin Klein
 * 563385586 & 576708589
 */
public class Tube implements Geometry{
    Ray axisRay;
    double radius;

    /**
     * Constructor for the Tube class, constructing a tube with a
     * ray and radius
     * @param axis the center axis of the tube
     * @param r the radius of the Tube
     */
    public Tube(Ray axis, double r){
        axisRay = axis;
        radius = r;
    }

    /**
     * Getter for the Tube's radius
     * @return radius of the Tube
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Getter for the Tube's axis
     * @return axis of the Tube
     */
    public Ray getAxisRay() {
        return axisRay;
    }

    @Override
    public String toString() {
        return "Tube: " + "axis ray = " + axisRay + ", radius = " + radius;
    }

    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}
