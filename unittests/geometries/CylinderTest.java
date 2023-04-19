package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for geometries.Cylinder class
 * @author Eli Hawk and Binyamin Klein
 * 563385586 & 576708589
 */
class CylinderTest {

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

    /** Test method for {@link Cylinder#findIntersections(Ray)}. */
    @Test
    void testFindIntersections() {
        /* Not Implemented yet

        Cylinder c = new Cylinder(new Ray(new Point(1,0,0), new Vector(1,0,0)), 1,2);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Outside Cylinder
        Ray r = new Ray(new Point(0,2,0), new Vector(1,0,0));
        assertNull(c.findIntersections(r), "Ray outside cylider");
        // **** Group: Starts in middle of cylinder
        // TC02: Starts in middle and goes out the side
        r = new Ray(new Point(2,0,0), new Vector(0,1,0));
        List<Point> result = c.findIntersections(r);
        assertEquals(1, result.size(), "Ray starts in cylinder, wrong number of intersections.");
        assertEquals(List.of(new Point(2,1,0)), result, "Ray starts in cylider goes out side");
        // TC03: Starts in middle and goes through top
        r = new Ray(new Point(2,0,0), new Vector(1,0,0));
        result = c.findIntersections(r);
        assertEquals(1, result.size(), "Ray starts in cylinder, goes out top, wrong number of intersections");
        assertEquals(List.of(new Point(3,0,0)), result, "Ray starts in cylinder, goes out top, hits wrong point");
        // TC04: Starts in middle and goes through bottom
        r = new Ray(new Point(2,0,0), new Vector(-1,0,0));
        result = c.findIntersections(r);
        assertEquals(1, result.size(), "Ray starts in cylinder, goes out top, wrong number of intersections");
        assertEquals(List.of(new Point(1,0,0)), result, "Ray starts in cylinder, goes out bottom, hits wrong point");
        // **** Group: Goes through bottom
        // TC05: Goes through bottom and side
        r = new Ray(new Point(.5,0,0), new Vector(1,0,1));
        Point p1 = new Point(1,0,.5);
        Point p2 = new Point(1.5, 0, 1);
        result = c.findIntersections(r);
        assertEquals(2, result.size(), "Number points returned in case 05");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "Wrong points going through bottom and side");
        // TC06: Goes through bottom and top
        r = new Ray (new Point(0,0,.5), new Vector(1,0,0));
        p1 = new Point(1,0,.5);
        p2 = new Point(3,0,.5);
        result = c.findIntersections(r);
        assertEquals(2, result.size(),"Number points returned in case 06");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "Wrong points going through top and bottom");
        // **** Group: Goes through top
        // TC07: Goes through top and side
        r = new Ray(new Point(3.5, 0, 0), new Vector(-1,0,1));
        p1 = new Point(3,0,.5);
        p2 = new Point(2.5, 0,1);
        result = c.findIntersections(r);
        assertEquals(2, result.size(),"Number points returned in case 07");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "Wrong points going through top and side");
        // **** Continues along surface
        // TC08: Starts in the bottom and continues along plane
        r = new Ray(new Point(1,0,.5), new Vector(0,0,1));
        result = c.findIntersections(r);
        assertNull(result, "Ray goes along bottom plane");
        // TC09: Starts in the top and continues along plane
        r = new Ray(new Point(3,0,.5), new Vector(0,0,1));
        result = c.findIntersections(r);
        assertNull(result, "Ray goes along top plane");
        // TC010: Starts on cylinder side and continues along cylinder side
        r = new Ray(new Point(1.5, 0, 1), new Vector(1,0,0));
        result = c.findIntersections(r);
        assertNull(result, "Ray goes along side of cylinder");
        // ============ Boundary Values Tests ==============
        // **** Group: Ray is axis
        // TC11: Ray goes through axis and starts at bottom
        r = new Ray(new Point(1,0,0), new Vector(1,0,0));
        p1 = new Point(3,0,0);
        result = c.findIntersections(r);
        assertEquals(1, result.size(), "Number of points returned starting at bottom and going along axis");
        assertEquals(List.of(p1), result, "Ray goes through axis and starts at bottom");
        // TC12: Ray goes through axis and starts at top
        r = new Ray(new Point(3,0,0), new Vector(-1,0,0));
        p1 = new Point(1,0,0);
        result = c.findIntersections(r);
        assertEquals(1, result.size(), "Number of points returned starting at top and going along axis");
        assertEquals(List.of(p1), result, "Ray goes through axis and starts at top");
        // TC13: Ray goes through axis and starts in middle
        r = new Ray(new Point(1.5,0,0), new Vector(1,0,0));
        p1 = new Point(3,0,0);
        result = c.findIntersections(r);
        assertEquals(1, result.size(), "Number of points returned starting on axis and going along axis");
        assertEquals(List.of(p1), result, "Ray goes through axis and starts at top");
        // **** Group: Ray hits corner
        // TC14: Ray goes through cylinder and hits a corner
        r = new Ray(new Point(2,0,0), new Vector(1,0,1));
        p1 = new Point(3,0,1);
        result = c.findIntersections(r);
        assertEquals(1, result.size(), "Ray goes through cylinder and hits a corner");
        assertEquals(List.of(p1), result, "Ray goes through cylinder and hits a corner");
        // TC15: Ray hits a corner on top from outside
        r = new Ray(new Point(4,0,0), new Vector(-1,0,1));
        result = c.findIntersections(r);
        assertNull(result, "Ray hits a corner on top from outside");
        // TC16: Ray hits a corner on bottom from outside
        r = new Ray(new Point(.5,0,0), new Vector(.5,0,1));
        result = c.findIntersections(r);
        assertNull(result, "Ray hits a corner on top from outside");

        *************************/
    }
}