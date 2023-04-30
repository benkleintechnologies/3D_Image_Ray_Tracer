package primitives;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Ray class
 * @author Eli Hawk and Binyamin Klein
 * 563385586 & 576708589
 */
public class RayTest {

    /**
     * Test method for {@link primitives.Ray#findClosestPoint(List)}.
     */
    @Test
    void testFindClosestPoint(){
        Ray ray = new Ray(new Point(0,0,0), new Vector(1,0,0));

        // ============ Equivalence Partitions Tests ==============
        // TC01: Closest point is found in the middle of the list
        List<Point> points = List.of(
                new Point(1,0,0), new Point(2,0,0), new Point(0,0.5,0), new Point(3,0,0)
        );
        Point closestPoint = ray.findClosestPoint(points);
        assertEquals(new Point(0,0.5,0), closestPoint, "Error in finding closest point when point is in middle of list");

        // =============== Boundary Values Tests ==================
        // TC10: Empty List
        points = List.of();
        closestPoint = ray.findClosestPoint(points);
        assertNull(closestPoint, "Error in finding closest point when list is empty");

        // TC11: Closest point is the first point in the list
        points = List.of(
                new Point(1,0,0), new Point(2,0,0), new Point(0,2,0), new Point(0,0,3)
        );
        closestPoint = ray.findClosestPoint(points);
        assertEquals(new Point(1,0,0), closestPoint, "Error in finding closest point when point is first in list");

        // TC12: Closest point is the last point in the list
        points = List.of(
                new Point(2,0,0), new Point(0,2,0), new Point(0,0,3), new Point(1,0,0)
        );
        closestPoint = ray.findClosestPoint(points);
        assertEquals(new Point(1,0,0), closestPoint, "Error in finding closest point when point is last in list");
    }
}
