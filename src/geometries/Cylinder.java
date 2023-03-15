package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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
    public Vector getNormal(Point p) {
        //Check if point is head of axisRay
        if (p.equals(axisRay.getPoint()))
            return axisRay.getDirection();

        //check if point is on base or side
        double t = axisRay.getDirection().dotProduct(p.subtract(axisRay.getPoint()));
        Point O;
        if (t > 0 && t < height) { // point is on the side of the cylinder
            O = axisRay.getPoint().add(axisRay.getDirection().scale(t));
            return p.subtract(O).normalize();
        }
        // point is on the top or bottom of the cylinder
        return axisRay.getDirection();
    }

    @Override
    public String toString() {
        return "Cylinder: " + "axis ray = " + axisRay + ", radius = " + radius + ", height = " + height;
    }
}
