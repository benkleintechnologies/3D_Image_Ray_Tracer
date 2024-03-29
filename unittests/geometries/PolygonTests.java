package geometries;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

import org.junit.jupiter.api.Test;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import java.util.List;
import geometries.Intersectable.GeoPoint;


/** Testing Polygons
 * @author Dan */
public class PolygonTests {

    /** Test method for {@link geometries.Polygon#Polygon(primitives.Point...)}. */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct concave quadrangular with vertices in correct order
        try {
            new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct polygon");
        }

        // TC02: Wrong vertices order
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(0, 1, 0), new Point(1, 0, 0), new Point(-1, 1, 1)), //
                "Constructed a polygon with wrong order of vertices");

        // TC03: Not in the same plane
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 2, 2)), //
                "Constructed a polygon with vertices that are not in the same plane");

        // TC04: Concave quadrangular
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                        new Point(0.5, 0.25, 0.5)), //
                "Constructed a concave polygon");

        // =============== Boundary Values Tests ==================

        // TC10: Vertex on a side of a quadrangular
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                        new Point(0, 0.5, 0.5)),
                "Constructed a polygon with vertix on a side");

        // TC11: Last point = first point
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1)),
                "Constructed a polygon with vertice on a side");

        // TC12: Co-located points
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 1, 0)),
                "Constructed a polygon with vertice on a side");

    }

    /** Test method for {@link geometries.Polygon#getNormal(primitives.Point)}. */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here - using a quad
        Point[] pts =
                { new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1) };
        Polygon pol = new Polygon(pts);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> pol.getNormal(new Point(0, 0, 1)), "");
        // generate the test result
        Vector result = pol.getNormal(new Point(0, 0, 1));
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Polygon's normal is not a unit vector");
        // ensure the result is orthogonal to all the edges
        for (int i = 0; i < 3; ++i)
            assertTrue(isZero(result.dotProduct(pts[i].subtract(pts[i == 0 ? 3 : i - 1]))),
                    "Polygon's normal is not orthogonal to one of the edges");
    }

    /** Test method for {@link Polygon#findGeoIntersections(Ray)}.  */
    @Test
    public void testFindGeoIntersections() {
        Polygon poly = new Polygon(new Point(1,0,0), new Point(-1,0,0), new Point(-1,0,2), new Point(1,0,2));
        // ============ Equivalence Partitions Tests ==============
        // TC01: Intersection inside Polygon
        Ray ray = new Ray(new Point(0,-1,1), new Vector(0,1,0));
        List<GeoPoint> result = poly.findGeoIntersections(ray);
        assertEquals(1, result.size(), "Ray intersection inside Polygon, wrong number of intersections.");
        assertEquals(new GeoPoint(poly, new Point(0,0,1)).point, result.get(0).point, "Ray intersection inside Polygon");
        // TC02: Intersection outside Polygon next to one edge
        ray = new Ray(new Point(-2,-1,1), new Vector(0,1,0));
        result = poly.findGeoIntersections(ray);
        assertNull(result, "Ray intersection outside Polygon next to one edge");
        // TC03: Intersection outside Polygon on two edges
        ray = new Ray(new Point(-2,-1,3), new Vector(0,1,0));
        result = poly.findGeoIntersections(ray);
        assertNull(result, "Ray intersection outside Polygon between two edge");
        // TC04: Ray parallel to Polygon
        ray = new Ray(new Point(0,-1,1), new Vector(1,0,0));
        result = poly.findGeoIntersections(ray);
        assertNull(result, "Ray parallel to Polygon");
        // TC05: Ray starts after Polygon
        ray = new Ray(new Point(0,1,1), new Vector(0,1,0));
        result = poly.findGeoIntersections(ray);
        assertNull(result, "Ray starts after Polygon");

        // ============= Boundary Values Tests ==================
        // TC10: Ray intersects Polygon edge
        ray = new Ray(new Point(-1,-1,1), new Vector(0,1,0));
        result = poly.findGeoIntersections(ray);
        assertNull(result, "Ray intersects Polygon edge");
        // TC11: Ray intersects Polygon corner
        ray = new Ray(new Point(-1,-1,2), new Vector(0,1,0));
        result = poly.findGeoIntersections(ray);
        assertNull(result, "Ray intersects Polygon corner");
        // TC12: Ray starts on Polygon edge
        ray = new Ray(new Point(-1,0,1), new Vector(0,1,0));
        result = poly.findGeoIntersections(ray);
        assertNull(result, "Ray starts on Polygon edge");
        // TC13: Ray starts on Polygon corner
        ray = new Ray(new Point(-1,0,2), new Vector(0,1,0));
        result = poly.findGeoIntersections(ray);
        assertNull(result, "Ray starts on Polygon corner");
    }
}
