package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Tube class
 * @author Eli Hawk and Binyamin Klein
 * 563385586 & 576708589
 */
class TubeTest {

    /** Test method for {@link Tube#getNormal(Point)} . */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Check normal at random point
        Tube t1 = new Tube(new Ray(new Point(0,0,0), new Vector(0,0,1)), 5);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> t1.getNormal(new Point(0, 5, 3)), "");
        // generate the test result
        Vector result = t1.getNormal(new Point(0, 5, 3));
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Tube's normal is not a unit vector");
        // Check that vector returned is normal to tube
        assertEquals(new Point(0,1,0), result, "Result is not the normal to the Tube at the point");

        // ============ Boundary Value Tests ==============
        // TC02: Check that normal when ray head makes 90 degree angle with point
        Tube t2 = new Tube(new Ray(new Point(0,0,0), new Vector(0,0,1)), 5);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> t2.getNormal(new Point(0, 5, 0)), "");
        // generate the test result
        result = t2.getNormal(new Point(0, 5, 0));
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Tube's normal is not a unit vector");
        // Check that vector returned is normal to tube
        assertEquals(new Point(0,1,0), result, "Result is not the normal to the Tube at the point");
    }

    /** Test method for {@link Tube#findIntersections(Ray)} . */
    @Test
    void testFindIntersections() {

        // ============ Equivalence Partitions Tests ==============
        // **** Group: No intersections
        // TC01: Ray's line is outside the tube (0 points)
        // TC02: Ray's line is inside the tube, parallel to axis (0 point)
        // **** Group: Intersections
        // TC03: Ray starts inside and crosses the tube (1 point)
        // TC04: Ray starts before and crosses the tube (2 points)

        // ============ Boundary Value Tests ==============
        // **** Group: No intersections
        // TC11: Starts on side and goes out (0 points)
        // TC12: Ray tangent to Tube (0 points)
        // TC13: Ray is axis (0 points)
        // **** Group: 1 Intersection
        // TC14: Starts on side and goes in (1 point)
        // TC15: Starts on axis, orthogonal to it (1 point)
    }
}