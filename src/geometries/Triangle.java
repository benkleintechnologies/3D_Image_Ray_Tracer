package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static java.util.Objects.isNull;

/**
 * Class Triangle is the class representing a triangle (as a type of Polygon) in Cartesian 3-Dimensional coordinate system.
 * @author Eli Hawk and Binyamin Klein
 * 563385586 & 576708589
 */
public class Triangle extends Polygon{

    /**
     * Constructor for Triangle based on the coordinates of the three corners
     * @param p1 first corner
     * @param p2 second corner
     * @param p3 third corner
     */
    public Triangle(Point p1, Point p2, Point p3){
        super(p1,p2,p3);
    }

    //Getters from Polygon???

    @Override
    public String toString() {
        return "Triangle: {" + vertices.get(0) + "," + vertices.get(1) + "," + vertices.get(2) + "}";
    }

    //getNormal is inherited from Polygon

    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> intersections = plane.findIntersections(ray);
        if (isNull(intersections)){
            return null;
        }

        Point intersection = intersections.get(0);
        // Vectors from starting point of the ray to each corner of the triangle
        Vector v1 = vertices.get(0).subtract(ray.getPoint());
        Vector v2 = vertices.get(1).subtract(ray.getPoint());
        Vector v3 = vertices.get(2).subtract(ray.getPoint());

        // Normals to the plane extended from the edges of the triangle
        Vector n1  = v1.crossProduct(v2).normalize();
        Vector n2  = v2.crossProduct(v3).normalize();
        Vector n3  = v3.crossProduct(v1).normalize();

        double t1 = ray.getDirection().dotProduct(n1);
        double t2 = ray.getDirection().dotProduct(n2);
        double t3 = ray.getDirection().dotProduct(n3);

        if(( t1 > 0 && t2 > 0 && t3 > 0) || ( t1 < 0 && t2 < 0 && t3 < 0)){
            return List.of(intersection);
        }
        return null;
    }
}
