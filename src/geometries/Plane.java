package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;
import static primitives.Util.*;

/**
 * Class Plane is the basic class of Geometry representing a plane in Cartesian 3-Dimensional coordinate system.
 * @author Eli Hawk and Binyamin Klein
 * 563385586 & 576708589
 */
public class Plane extends Geometry{
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
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        //Return null if ray starts in plane
        if (q0.equals(ray.getPoint())){
            return null;
        }
        //Calculate distance from start of ray to the plane
        double t = normal.dotProduct(q0.subtract(ray.getPoint())) / normal.dotProduct(ray.getDirection());
        //If the point is in front of the ray, return the point
        if (alignZero(t) > 0 && Double.isFinite(t) && alignZero(t - maxDistance) <= 0){
            Point p = ray.getPoint().add(ray.getDirection().scale(t));
            return List.of(new GeoPoint(this,p));
        }
        return null;
    }
}
