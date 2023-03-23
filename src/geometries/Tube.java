package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Class Sphere is the basic class representing a Tube in the Cartesian
 * 3-Dimensional coordinate system.
 * @author Eli Hawk and Binyamin Klein
 * 563385586 & 576708589
 */
public class Tube extends RadialGeometry{
    Ray axisRay;
    //double radius;

    /**
     * Constructor for the Tube class, constructing a tube with a
     * ray and radius
     * @param axis the center axis of the tube
     * @param r the radius of the Tube
     */
    public Tube(Ray axis, double r){
        super(r);
        axisRay = axis;
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
        //Find point on axis ray closest to P. We'll call it O
        double t = axisRay.getDirection().dotProduct(p.subtract(axisRay.getPoint()));
        Point O;
        //If point is 90 degrees from ray head, the closest point is the ray head
        if (t>0){
            O = axisRay.getPoint().add(axisRay.getDirection().scale(t));
        }else{
            O = axisRay.getPoint();
        }
        //Normal is vector from O to p, normalized
        return p.subtract(O).normalize();
    }

    @Override
    public List<Point> findIntsersections(Ray ray) {
        return null;
    }
}
