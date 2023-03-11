package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for geometries.Sphere class
 * @author Eli Hawk and Binyamin Klein
 * 563385586 & 576708589
 */
class SphereTest {

    /** Test method for {@link Sphere#getRadius()} . */
    @Test
    void getRadius() {
    }

    /** Test method for {@link Sphere#getCenter()} . */
    @Test
    void getCenter() {
    }

    /** Test method for {@link Sphere#toString()} . */
    @Test
    void testToString() {
    }

    /** Test method for {@link Sphere#getNormal(Point)} . */
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Check that normal
        Sphere sph = new Sphere(new Point(0,0,0), 5);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> sph.getNormal(new Point(0, 0, 5)), "");
        // generate the test result
        Vector result = sph.getNormal(new Point(0, 0, 5));
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Sphere's normal is not a unit vector");
        // Check that vector returned is normal to the sphere at the point (i.e. given point minus center, normalized)
        assertEquals(new Point(0,0,1), result, "Result is not the normal to the sphere at the point");
    }
}