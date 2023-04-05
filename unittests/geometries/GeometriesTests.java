package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Geometries class
 * @author Eli Hawk and Binyamin Klein
 * 563385586 & 576708589
 */
public class GeometriesTests {

    /** Test method for {@link Geometries#findIntersections(Ray)}. */
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

        // ============ Equivalence Partitions Tests ==============
        // TC01: Several (but not all) geometries intersect
        Ray ray = new Ray(new Point(0,0,-1), new Vector(1,1,1));
        Point pt1 = new Point(1,1,0);

        List<Point> result = G.findIntersections(ray);

        // ============ Boundary Value Tests ==============
        // TC11: Empty geometries collection

        // TC12: No intersection point
        result = G.findIntersections(ray);
        assertNull(result, "No intersection point");
        // TC13: Only one geometry intersects

        // TC14: Several (but not all) geometries intersect

        // TC15: All geometries intersect
        ray = new Ray(new Point(20,0,1), new Vector(-1, 0, 0));
        result = G.findIntersections(ray);
        assertEquals(8, result.size(), "All geometries intersect, wrong num intersections");
        //itersections with s2
        Point pt1 = new Point(13,0,1);
        Point pt2 = new Point(7,0,1);
        //itersections with s1
        Point pt3 = new Point(5,0,1);
        Point pt4 = new Point(1,0,1);
        //itersections with p1
        Point pt5 = new Point(0,0,1);
        //itersections with p2
        Point pt6 = new Point(-4,0,1);
        //itersections with t1
        Point pt7 = new Point(-2,0,1);
        //itersections with t1
        Point pt8 = new Point(-10,0,1);
        List<Point> points = List.of(pt1, pt2,pt3,pt4,pt5,pt6,pt7,pt8);
        //May need to be changed to ensure proper order
        assertEquals(result, points, "All geometries are intersected");
    }
}
