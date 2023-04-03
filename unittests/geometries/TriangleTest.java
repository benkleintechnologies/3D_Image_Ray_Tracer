package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for geometries.Triangle class
 * @author Eli Hawk and Binyamin Klein
 * 563385586 & 576708589
 */
class TriangleTest {

    /** Test method for {@link Triangle#getNormal(Point)} . */
    @Test
    void testGetNormal(){
        // ============ Equivalence Partitions Tests ==============
        // TC01: Check that normal returns a normalized vector perpendicular to the triangle
        Triangle t = new Triangle(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
        // ensure there are no exceptions
        assertDoesNotThrow(() -> t.getNormal(new Point(0, 0, 1)), "");
        // generate the test result
        Vector result = t.getNormal(new Point(0, 0, 1));
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Triangle's normal is not a unit vector");
        // Check that vector returned for normal is perpendicular to each edge of the triangle
        assertTrue(isZero(result.dotProduct(new Vector(-1,0,1))) && isZero(result.dotProduct(new Vector(0,-1,1))) && isZero(result.dotProduct(new Vector(1,-1,0))), "Result is not the normal to the triangle");
    }

    /** Test method for {@link Triangle#findIntersections(Ray)}. */
    @Test
    void testFindIntersection(){
        Triangle t = new Triangle(new Point(0, 0, 0), new Point(1, 0, 0), new Point(0, 1, 0));

        // ============ Equivalence Partitions Tests ==============
        // **** Group: Intersects Triangle
        // TC01: Ray intersects inside the triangle
        Ray ray = new Ray(new Point(.1,.1,-1), new Vector(0,0,1));
        List<Point> result = t.findIntersections(ray);
        assertEquals(new Point(.1,.1,0), result.get(0), "Ray intersects inside the triangle");
        // **** Group: Does not intersect Triangle
        // TC02: Ray does not intersect the plane
        ray = new Ray(new Point(0,0,-1), new Vector(1,1,0));
        result = t.findIntersections(ray);
        assertNull(result, "Ray does not intersect the plane");
        // TC03: Ray hits point next to side of triangle
        ray = new Ray(new Point(1,1,-1), new Vector(0,0,1));
        result = t.findIntersections(ray);
        assertNull(result, "Ray hits point next to side of triangle");
        // TC04: Ray hits point between edges of triangle
        ray = new Ray(new Point(-1,-1,-1), new Vector(0,0,1));
        result = t.findIntersections(ray);
        assertNull(result, "Ray hits point between edges of triangle");

        // ============ Boundary Values Tests ==============
        // **** Group: Intersects Triangle
        // TC11: Ray hits edge of triangle
        ray = new Ray(new Point(0,.5,-1), new Vector(0,0,1));
        result = t.findIntersections(ray);
        assertNull(result, "Ray hits edge of triangle");
        // TC12: Ray hits corner of triangle
        ray = new Ray(new Point(0,0,-1), new Vector(0,0,1));
        result = t.findIntersections(ray);
        assertNull(result, "Ray hits corner of triangle");
        // **** Group: Does not intersect Triangle
        // TC13: Ray hits extension of side of triangle
        ray = new Ray(new Point(-1,2,-1), new Vector(0,0,1));
        result = t.findIntersections(ray);
        assertNull(result, "Ray hits extension of side of triangle");
    }

}