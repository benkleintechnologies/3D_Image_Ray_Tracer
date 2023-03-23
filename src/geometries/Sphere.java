package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Class Sphere is the basic class representing a sphere in the Cartesian
 * 3-Dimensional coordinate system.
 * @author Eli Hawk and Binyamin Klein
 * 563385586 & 576708589
 */
public class Sphere extends RadialGeometry{
    Point center;
    double radius;

    /**
     * Constructor for Sphere class that receives the circles center and radius
     * to construct the circle object
     * @param p the center of the circle
     * @param r the radius of the circle
     */
    public Sphere(Point p, double r){
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
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
