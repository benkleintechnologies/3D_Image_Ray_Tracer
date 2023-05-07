package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Geometries class
 * @author Eli Hawk and Binyamin Klein
 * 563385586 & 576708589
 */
public class GeometriesTests {

    /** Test method for {@link Geometries#findGeoIntersections(Ray)}. */
    @Test
    void testFindIntersections() {
        Geometries G = new Geometries();

        // Create geometries to test on
        Sphere s1 = new Sphere(2, new Point(3,0,1));
        Sphere s2 = new Sphere(3, new Point(10, 0, 1));
        Triangle t1 = new Triangle(new Point(-2,-1,0), new Point(-2,-1,5), new Point(-2,5,0));
        Triangle t2 = new Triangle(new Point(-10,-4,0), new Point(-10,4,0), new Point(-10,0,5));
        Plane p1 = new Plane(new Point(-4,2,0), new Point(-4,4,0), new Point(-4,4,2));
        Plane p2 = new Plane(new Point(0,2,0), new Point(0,4,0), new Point(0,4,2));
        G.add(s1, s2, t1, t2, p1, p2);
        // ============ Equivalence Partitions Tests ==============
        // TC01: Several (but not all) geometries intersect
        Ray ray = new Ray(new Point(6,0,1), new Vector(-1,0,0));
        List<GeoPoint> result = G.findGeoIntersections(ray);
        assertEquals(6, result.size(), "Intersects several geometries");

        // ============ Boundary Value Tests ==============
        // TC11: Empty geometries collection
        Geometries empty = new Geometries();
        result = empty.findGeoIntersections(ray);
        assertNull(result, "Empty Geometries collection");
        // TC12: No intersection point
        ray = new Ray(new Point(6,0,0), new Vector(0,1,0));
        result = G.findGeoIntersections(ray);
        assertNull(result, "No intersection point");
        // TC13: Only one geometry intersects
        ray = new Ray(new Point(3,3,0),new Vector(0, -1, 0));
        result = G.findGeoIntersections(ray);
        assertEquals(2, result.size(), "Intersects only one geometry");
        // TC14: All geometries intersect
        ray = new Ray(new Point(20,0,1), new Vector(-1, 0, 0));
        result = G.findGeoIntersections(ray);
        assertEquals(8, result.size(), "All geometries intersect, wrong num intersections");

    }
}
