package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Tube implements Geometry{
    Ray axisRay;
    double radius;

    public Tube(Ray axis, double r){
        axisRay = axis;
        radius = r;
    }

    public double getRadius() {
        return radius;
    }

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
