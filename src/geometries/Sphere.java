package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static java.util.Objects.isNull;
import static primitives.Util.*;

import java.util.List;

/**
 * Class Sphere is the basic class representing a sphere in the Cartesian
 * 3-Dimensional coordinate system.
 * @author Eli Hawk and Binyamin Klein
 * 563385586 & 576708589
 */
public class Sphere extends RadialGeometry{
    Point center;

    /**
     * Constructor for Sphere class that receives the circles center and radius
     * to construct the circle object
     * @param p the center of the circle
     * @param r the radius of the circle
     */
    public Sphere(double r, Point p){
        super(r);
        center = p;
    }

    /**
     * Getter for the radius attribute of a sphere
     * @return radius of the sphere
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Getter for the center point attribute of a sphere
     * @return center point of the sphere
     */
    public Point getCenter() {
        return center;
    }

    @Override
    public String toString() {
        return "Sphere: " + "center = " + center + ", radius = " + radius;
    }

    @Override
    public Vector getNormal(Point p) {
        // Normal is vector from center to p, normalized
        return p.subtract(center).normalize();
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        //Ray starts at center, so intersection is on the ray at the radius
        if (center.equals(ray.getPoint())){
            return List.of(new GeoPoint(this, ray.getPoint(radius)));
        }

        //Calculate vector pointing from the beginning of the ray to the center of the sphere
        Vector u = center.subtract(ray.getPoint());
        //Calculate distance to center of sphere in direction of the ray
        double tm = alignZero(u.dotProduct(ray.getDirection()));
        //Distance from center of sphere to the point reach by continuing the vector above or below center of sphere
        double d = Math.sqrt(Math.abs(u.lengthSquared() - Math.pow(tm,2)));
        //Calculates distance from intersection point to middle of sphere on ray
        double th = Math.sqrt(Math.abs(Math.pow(radius,2) - Math.pow(d,2)));
        //Calculate distance to first point
        double t1 = tm - th;
        //Calculate distance to second point
        double t2 = tm + th;
        //Calculate first intersection point
        Point p1 = null;
        if (alignZero(t1) > 0  && alignZero(t1 - maxDistance) <= 0){
            p1 = ray.getPoint(t1);
        }
        //Calculate second intersection point
        Point p2 = null;
        if (alignZero(t2) > 0 && alignZero(t2 - maxDistance) <= 0) {
            p2 = ray.getPoint(t2);
        }

        //Make sure there is intersection and return those points
        if (d < radius){
            if (!isNull(p1) && isZero(p1.distance(center) - radius)){ // |𝑃1−𝑂|^2 − 𝑟^2 = 0 and P1 is in front og the ray
                if (!isNull(p2) && isZero(p2.distance(center) - radius)){ // |𝑃2−𝑂|^2 − 𝑟^2 = 0 and P2 is in front of the ray
                    return List.of(new GeoPoint(this, p1),new GeoPoint(this, p2));
                }
                return List.of(new GeoPoint(this, p1));
            }else if (!isNull(p2) && isZero(p2.distance(center) - radius)) { // |𝑃2−𝑂|^2 − 𝑟^2 = 0
                return List.of(new GeoPoint(this, p2));
            }
        }
        return null;
    }
}
