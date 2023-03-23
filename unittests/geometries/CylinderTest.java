package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for geometries.Cylinder class
 * @author Eli Hawk and Binyamin Klein
 * 563385586 & 576708589
 */
class CylinderTest {

    /** Test method for {@link Cylinder#getHeight()}. */
    @Test
    void testGetHeight() {
    }

    /** Test method for {@link Cylinder#toString()}. */
    @Test
    void testToString() {
    }

    /** Test method for {@link Cylinder#getNormal(Point)}. */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Check normal on side
        Cylinder cyl = new Cylinder(new Ray(new Point(0,0,0), new Vector(0,0,1)), 5, 5);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> cyl.getNormal(new Point(0, 5, 3)), "");
        // generate the test result
        Vector result = cyl.getNormal(new Point(0, 5, 3));
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Cylinder's normal is not a unit vector");
        // Check that vector returned is normal to tube
        assertEquals(new Point(0,1,0), result, "Result is not the normal to the Cylinder at the point on its side");

        // TC02: Check normal on top of cylinder
        // ensure there are no exceptions
        assertDoesNotThrow(() -> cyl.getNormal(new Point(0, 1, 5)), "");
        // generate the test result
        result = cyl.getNormal(new Point(0, 1, 5));
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Cylinder's normal is not a unit vector");
        // Check that vector returned is normal to top of the tube (because normal dotted with two vectors representing circle on top are zero)
        assertTrue(isZero(result.dotProduct(new Vector(0,-1,0))) && isZero(result.dotProduct(new Vector(-1,0,0))), "Result is not the normal to the Cylinder at the point on its top");

        // TC03: Check normal on bottom of cylinder
        // ensure there are no exceptions
        assertDoesNotThrow(() -> cyl.getNormal(new Point(0, 1, 0)), "");
        // generate the test result
        result = cyl.getNormal(new Point(0, 1, 0));
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Cylinder's normal is not a unit vector");
        // Check that vector returned is normal to top of the tube (because normal dotted with two vectors representing circle on bottom are zero)
        assertTrue(isZero(result.dotProduct(new Vector(0,1,0))) && isZero(result.dotProduct(new Vector(1,0,0))), "Result is not the normal to the Cylinder at the point on its bottom");

        // ============ Boundary Value Tests ==============
        // TC04: Check normal at center of top of cylinder
        // ensure there are no exceptions
        assertDoesNotThrow(() -> cyl.getNormal(new Point(0, 1, 5)), "");
        // generate the test result
        result = cyl.getNormal(new Point(0, 0, 5));
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Cylinder's normal is not a unit vector");
        // Check that vector returned is normal to top of the tube (because normal dotted with two vectors representing circle on top are zero)
        assertTrue(isZero(result.dotProduct(new Vector(0,-1,0))) && isZero(result.dotProduct(new Vector(-1,0,0))), "Result is not the normal to the Cylinder at the point on its top");

        // TC05: Check normal at center of bottom of cylinder
        // ensure there are no exceptions
        assertDoesNotThrow(() -> cyl.getNormal(new Point(0, 1, 0)), "");
        // generate the test result
        result = cyl.getNormal(new Point(0, 0, 0));
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Cylinder's normal is not a unit vector");
        // Check that vector returned is normal to top of the tube (because normal dotted with two vectors representing circle on bottom are zero)
        assertTrue(isZero(result.dotProduct(new Vector(0,1,0))) && isZero(result.dotProduct(new Vector(1,0,0))), "Result is not the normal to the Cylinder at the point on its bottom");
    }
}