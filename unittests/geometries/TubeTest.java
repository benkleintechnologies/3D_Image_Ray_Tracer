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
}