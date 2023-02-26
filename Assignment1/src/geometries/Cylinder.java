package geometries;

import primitives.Ray;

public class Cylinder extends Tube{
    double height;

    public Cylinder(Ray axis, double r, double h) {
        super(axis, r);
        height = h;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return "Cylinder: " + "axis ray = " + axisRay + ", radius = " + radius + ", height = " + height;
    }
}
