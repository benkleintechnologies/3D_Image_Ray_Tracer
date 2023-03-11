package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

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

}