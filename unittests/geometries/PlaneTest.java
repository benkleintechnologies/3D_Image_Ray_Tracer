package geometries;

import org.junit.jupiter.api.Test;
import primitives.Double3;
import primitives.Point;
import primitives.Ray;
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
    void testGetQ0() {
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

    /** Test method for {@link Plane#findIntersections(Ray)} ()} )}. */
    void testFindIntersections(){

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray intersects the plane
        // TC02: Ray does not intersect the plane


        // =============== Boundary Values Tests ==================
        // TC11: Ray is parallel to the plane and in the plane
        // TC12: Ray is parallel to the plane and not in the plane
        // TC13: Ray is orthoginal to the plane, starting before the plane
        // TC14: Ray is orthoginal to the plane, starting in the plane
        // TC15: Ray is orthoginal to the plane, starting after the plane
        // TC16: Ray begins at plane and is neither orthofianl or parallel to the plane
        // TC17: Ray begins at point Q,the reference point of the plane, and not parallel or orthoginal


    }
}