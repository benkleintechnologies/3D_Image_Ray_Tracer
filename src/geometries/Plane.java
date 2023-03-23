package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Class Plane is the basic class of Geometry representing a plane in Cartesian 3-Dimensional coordinate system.
 * @author Eli Hawk and Binyamin Klein
 * 563385586 & 576708589
 */
public class Plane implements Geometry{
    Point q0;
    Vector normal;

    /**
     * Constructor which calculates normal from three points on the plane and chooses a
     * point to represent the plane
     * @param p1 First point
     * @param p2 Second point
     * @param p3 Third point
     */
    public Plane(Point p1, Point p2, Point p3){
        Vector v1 = p1.subtract(p2);
        Vector v2 = p1.subtract(p3);
        //Calculate Normal Vector
        normal = v1.crossProduct(v2).normalize();
        q0 = p1;
    }

    /**
     * Constructor which assigns point and normal of plane
     * @param p point on plane
     * @param n normal to plane
     */
    public Plane(Point p, Vector n){
        q0 = p;
        normal = n.normalize();
    }

    /**
     * Getter for point on plane
     * @return point q0 on plane
     */
    public Point getQ0() {
        return q0;
    }

    /**
     * Getter for normal to the plane
     * @return normal
     */
    public Vector getNormal() {
        return normal;
    }

    @Override
    public String toString() {
        return "Point on plane: " + q0 + ", Normal to plane: " + normal;
    }

    @Override
    public Vector getNormal(Point p) {
        return normal;
    }

    @Override
    public List<Point> findIntsersections(Ray ray) {
        return null;
    }
}
