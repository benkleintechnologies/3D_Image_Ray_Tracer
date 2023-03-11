package geometries;

import org.junit.jupiter.api.Test;
import primitives.Double3;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for geometries.Plane class
 * @author Eli Hawk and Binyamin Klein
 * 563385586 & 576708589
 */
class PlaneTest {

    /** Test method for {@link Plane#getQ0()}. */
    @Test
    void getQ0() {
    }

    /** Test method for {@link Plane#getNormal()}. */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Check that normal returns a normalized vector perpendicular to the plane
        Plane p = new Plane(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
        // ensure there are no exceptions
        assertDoesNotThrow(() -> p.getNormal(new Point(0, 0, 1)), "");
        // generate the test result
        Vector result = p.getNormal(new Point(0, 0, 1));
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Plane's normal is not a unit vector");
        // Check that vector returned for normal is perpendicular to two vectors representing the plane
        assertTrue(isZero(result.dotProduct(new Vector(-1,0,1))) && isZero(result.dotProduct(new Vector(0,-1,1))), "Result is not the normal to the triangle");
    }

    /** Test method for {@link Plane#toString()} )}. */
    @Test
    void testToString() {
    }
}