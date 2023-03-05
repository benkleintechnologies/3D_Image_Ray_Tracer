package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Unit tests for primitives.Point class
 * @author Eli Hawk and Binyamin Klein
 * 563385586 & 576708589
 */
class PointTest {

    /**
     * Test method for {@link primitives.Point#subtract(primitives.Point)}.
     */
    @Test
    void subtract() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Both points have positive coordinates
        Point testPoint1 = new Point(3,4,6);
        Point testPoint2 = new Point(1,5,2);
        assertEquals(new Point(2,-1,4), testPoint1.subtract(testPoint2), "Error in subtracting positive points");

        // TC02: Both points have negative coordinates
        testPoint1 = new Point(-3,-4,-6);
        testPoint2 = new Point(-1,-5,-2);
        assertEquals(new Point(-2,1,-4), testPoint1.subtract(testPoint2), "Error in subtracting negative points");

        // TC03: One point is positive and the other is negative
        testPoint1 = new Point(-3,-4,-6);
        testPoint2 = new Point(1,5,2);
        assertEquals(new Point(-4,-9,-8), testPoint1.subtract(testPoint2), "Error in subtracting positive and negative points");

        // =============== Boundary Values Tests ==================
        // TC04: First point is zero
        testPoint1 = new Point(0,0,0);
        testPoint2 = new Point(-1,5,-2);
        assertEquals(new Point(1,-5,2), testPoint1.subtract(testPoint2), "Point subtracted from zero point should be negative of itself");

        // TC05: Second point is zero
        testPoint1 = new Point(-1,5,-2);
        testPoint2 = new Point(0,0,0);
        assertEquals(testPoint1, testPoint1.subtract(testPoint2), "Zero point subtracted from aother point should give the original point");

        // TC06: Both points are zero
        testPoint1 = new Point(0,0,0);
        testPoint2 = new Point(0,0,0);
        assertEquals(new Point(0,0,0), testPoint1.subtract(testPoint2), "Subtracting zero point from zero point should give zero point");
    }

    /**
     * Test method for {@link primitives.Point#add(primitives.Point)}.
     */
    @Test
    void add() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Both points have positive coordinates
        Point testPoint1 = new Point(3,4,6);
        Point testPoint2 = new Point(1,5,2);
        assertEquals(new Point(4,9,8), testPoint1.add(testPoint2), "Error in adding positive points");

        // TC02: Both points have negative coordinates
        testPoint1 = new Point(-3,-4,-6);
        testPoint2 = new Point(-1,-5,-2);
        assertEquals(new Point(-4,-9,-8), testPoint1.add(testPoint2), "Error in adding negative points");

        // TC03: One point is positive and the other is negative
        testPoint1 = new Point(-3,-4,-6);
        testPoint2 = new Point(1,5,2);
        assertEquals(new Point(-2,1,-4), testPoint1.add(testPoint2), "Error in adding positive and negative points");

        // =============== Boundary Values Tests ==================
        // TC04: One point is zero
        testPoint1 = new Point(0,0,0);
        testPoint2 = new Point(-1,5,-2);
        assertEquals(testPoint2, testPoint1.add(testPoint2), "Point added to zero point should be itself");
    }

    /**
     * Test method for {@link primitives.Point#add(primitives.Point)}.
     */
    @Test
    void distanceSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Both points have positive coordinates
        Point testPoint1 = new Point(1,2,6);
        Point testPoint2 = new Point(1,4,2);
        assertEquals(21, testPoint1.distanceSquared(testPoint2), "Error in calculating distance between positive points");

        // TC02: Both points have negative coordinates
        testPoint1 = new Point(-3,-4,-6);
        testPoint2 = new Point(-1,-5,-2);
        assertEquals(21, testPoint1.distanceSquared(testPoint2), "Error in calculating distance between negative points");

        // TC03: One point is positive and the other is negative
        testPoint1 = new Point(-3,-4,-6);
        testPoint2 = new Point(1,5,2);
        assertEquals(161, testPoint1.distanceSquared(testPoint2), "Error in calculating distance between positive and negative points");

        // =============== Boundary Values Tests ==================
        // TC04: One point is zero
        testPoint1 = new Point(0,0,0);
        testPoint2 = new Point(-1,5,-2);
        assertEquals(30, testPoint1.distanceSquared(testPoint2), "Error in calculating distance from zero");

        //TC05: Both points are zero
        testPoint1 = new Point(0,0,0);
        testPoint2 = new Point(0,0,0);
        assertEquals(0, testPoint1.distanceSquared(testPoint2), "Distance from zero to zero should be zero");
    }

    /**
     * Test method for {@link primitives.Point#distance(primitives.Point)}.
     */
    @Test
    void distance() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Both points have positive coordinates
        Point testPoint1 = new Point(1,2,6);
        Point testPoint2 = new Point(1,4,2);
        assertEquals(Math.sqrt(21), testPoint1.distance(testPoint2), "Error in calculating distance between positive points");

        // TC02: Both points have negative coordinates
        testPoint1 = new Point(-3,-4,-6);
        testPoint2 = new Point(-1,-5,-2);
        assertEquals(Math.sqrt(21), testPoint1.distance(testPoint2), "Error in calculating distance between negative points");

        // TC03: One point is positive and the other is negative
        testPoint1 = new Point(-3,-4,-6);
        testPoint2 = new Point(1,5,2);
        assertEquals(Math.sqrt(161), testPoint1.distance(testPoint2), "Error in calculating distance between positive and negative points");

        // =============== Boundary Values Tests ==================
        // TC04: One point is zero
        testPoint1 = new Point(0,0,0);
        testPoint2 = new Point(-1,5,-2);
        assertEquals(Math.sqrt(30), testPoint1.distance(testPoint2), "Error in calculating distance from zero");

        //TC05: Both points are zero
        testPoint1 = new Point(0,0,0);
        testPoint2 = new Point(0,0,0);
        assertEquals(0, testPoint1.distanceSquared(testPoint2), "Distance from zero to zero should be zero");
    }

    /**
     * Test method for {@link primitives.Point#equals(Object)}.
     */
    @Test
    void testEquals() {
        Point testPoint1 = new Point(1,2,6);
        Point testPoint2 = new Point(4,5,2);
        // ============ Equivalence Partitions Tests ==============
        //TC01: Checking equivalence with itself
        assertEquals(testPoint1, testPoint1, "Point should return equal to itself");
        //TC02: Checking equivalence with Point with same values
        assertEquals(testPoint1, new Point(1,2,6), "Point should return equal to Point with same values");
        //TC03: Checking equivalence with Point with different values
        assertNotEquals(testPoint1, testPoint2, "Point should return not equal to different Point");

        // =============== Boundary Values Tests ==================
        //TC04: Checking equivalence with null
        assertNotEquals(testPoint1, null, "Point should return not equal to null");
        //TC05: Checking equivalence with object that is not a Point
        assertNotEquals(testPoint1, new Vector(1,2,6), "Point should return not equal to different type");
    }
}