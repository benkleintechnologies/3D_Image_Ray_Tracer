package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for geometries.Sphere class
 * @author Eli Hawk and Binyamin Klein
 * 563385586 & 576708589
 */
class testSphereTest {

    /** Test method for {@link Sphere#getRadius()} . */
    @Test
    void testGetRadius() {
    }

    /** Test method for {@link Sphere#getCenter()} . */
    @Test
    void testGetCenter() {
    }

    /** Test method for {@link Sphere#toString()} . */
    @Test
    void testToString() {
    }

    /** Test method for {@link Sphere#getNormal(Point)} . */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Check that normal
        Sphere sph = new Sphere(5, new Point(0,0,0));
        // ensure there are no exceptions
        assertDoesNotThrow(() -> sph.getNormal(new Point(0, 0, 5)), "");
        // generate the test result
        Vector result = sph.getNormal(new Point(0, 0, 5));
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Sphere's normal is not a unit vector");
        // Check that vector returned is normal to the sphere at the point (i.e. given point minus center, normalized)
        assertEquals(new Point(0,0,1), result, "Result is not the normal to the sphere at the point");
    }

    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(1d,  new Point (1, 0, 0));
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0))),
                "Ray's line out of sphere");
        // TC02: Ray starts before and crosses the sphere (2 points)
        Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);
        List<Point> result = sphere.findIntersections(new Ray(new Point(-1, 0, 0),
                new Vector(3, 1, 0)));
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "Ray crosses sphere");
        // TC03: Ray starts inside the sphere (1 point)
        Ray inner = new Ray(new Point(1.5, 0, 0), new Vector(1,0,0));
        result = sphere.findIntersections(inner);
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(new Point(2,0,0), result, "Ray inside sphere");

        // TC04: Ray starts after the sphere (0 points)
        Ray beyond = new Ray(new Point(3, 0, 0), new Vector(1,0,0));
        result = sphere.findIntersections(inner);
        assertEquals(0, result.size(), "Wrong number of points");
        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        // TC12: Ray starts at sphere and goes outside (0 points)
        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        // TC14: Ray starts at sphere and goes inside (1 points)
        // TC15: Ray starts inside (1 points)
        // TC16: Ray starts at the center (1 points)
        // TC17: Ray starts at sphere and goes outside (0 points)
        // TC18: Ray starts after sphere (0 points)
        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        // TC20: Ray starts at the tangent point
        // TC21: Ray starts after the tangent point
        // **** Group: Special cases
        // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
    }
}