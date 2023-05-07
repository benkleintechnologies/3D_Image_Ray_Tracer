package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import java.util.List;
import geometries.Intersectable.GeoPoint;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for geometries.Plane class
 * @author Eli Hawk and Binyamin Klein
 * 563385586 & 576708589
 */
class PlaneTest {

    /** Test method for {@link Plane#Plane(Point p1, Point p2, Point p3)}. */
    @Test
    void testPlaneConstructor() {
        // ============ Boundary Value Tests ==============
        // TC11: Ensures first two points are not equal
        assertThrows(IllegalArgumentException.class, ()-> new Plane(new Point(0,0,1), new Point(0,0,1), new Point(1,0,0)));
        //TC12: Ensures all points are not on same line
        Point[] points = {new Point(1, 0 ,0), new Point(2, 0, 0), new Point(3, 0, 0)};
        Vector v1 = points[1].subtract(points[0]);
        Vector v2 = points[2].subtract(points[1]);
        assertThrows(IllegalArgumentException.class, ()->v1.crossProduct(v2), "Should throw error when points on same line" );
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

    /** Test method for {@link Plane#findGeoIntersections(Ray)}. */
    @Test
    void testFindGeoIntersection(){
        Plane plane = new Plane(new Point(0,0,0), new Vector(0,0,1)); //Plane is the x-y axis

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray intersects the plane
        Ray ray = new Ray(new Point(0,0,-1), new Vector(1,1,1));
        List<GeoPoint> result = plane.findGeoIntersections(ray);
        assertEquals(new GeoPoint(plane, new Point(1,1,0)), result.get(0), "Ray intersects the plane");
        // TC02: Ray does not intersect the plane
        ray = new Ray(new Point(0,0,-1), new Vector(1,1,0));
        result = plane.findGeoIntersections(ray);
        assertNull(result, "Ray does not intersect the plane");

        // =============== Boundary Values Tests ==================
        // **** Group: Parallel Ray
        // TC11: Ray is parallel to the plane and in the plane
        ray = new Ray(new Point(1,0,0), new Vector(1,1,0));
        result = plane.findGeoIntersections(ray);
        assertNull(result, "Ray is parallel to the plane and in the plane");
        // TC12: Ray is parallel to the plane and not in the plane
        ray = new Ray(new Point(0,1,1), new Vector(1,1,0));
        result = plane.findGeoIntersections(ray);
        assertNull(result, "Ray is parallel to the plane and not in the plane");
        // **** Group: Orthogonal Ray
        // TC13: Ray is orthogonal to the plane, starting before the plane
        ray = new Ray(new Point(0,0,-1), new Vector(0,0,1));
        result = plane.findGeoIntersections(ray);
        assertEquals(new GeoPoint(plane, new Point(0,0,0)), result.get(0), "Ray is orthogonal to the plane, starting before the plane");
        // TC14: Ray is orthogonal to the plane, starting in the plane
        ray = new Ray(new Point(0,0,0), new Vector(0,0,1));
        result = plane.findGeoIntersections(ray);
        assertNull(result, "Ray is orthogonal to the plane, starting in the plane");
        // TC15: Ray is orthogonal to the plane, starting after the plane
        ray = new Ray(new Point(0,0,1), new Vector(0,0,1));
        result = plane.findGeoIntersections(ray);
        assertNull(result, "Ray is orthogonal to the plane, starting after the plane");
        // **** Group: Not Parallel or Orthogonal Ray
        // TC16: Ray begins at plane and is neither orthogonal nor parallel to the plane
        ray = new Ray(new Point(0,1,0), new Vector(0,1,1));
        result = plane.findGeoIntersections(ray);
        assertNull(result, "Ray begins at plane and is neither orthogonal nor parallel to the plane");
        // TC17: Ray begins at point Q,the reference point of the plane, and not parallel or orthogonal
        ray = new Ray(new Point(0,0,0), new Vector(0,1,1));
        result = plane.findGeoIntersections(ray);
        assertNull(result, "Ray begins at point Q,the reference point of the plane, and not parallel or orthogonal");
    }
}