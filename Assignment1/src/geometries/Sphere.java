package geometries;

import primitives.Point;
import primitives.Vector;

public class Sphere implements Geometry{
    Point center;
    double radius;

    public Sphere(Point p, double r){
        center = p;
        radius = r;
    }

    public double getRadius() {
        return radius;
    }

    public Point getCenter() {
        return center;
    }

    @Override
    public String toString() {
        return "Sphere: " + "center = " + center + ", radius = " + radius;
    }

    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}
