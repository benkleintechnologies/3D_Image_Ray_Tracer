package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;
import java.util.List;
import geometries.Intersectable.GeoPoint;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Sphere class
 * @author Eli Hawk and Binyamin Klein
 * 563385586 & 576708589
 */
class SphereTest {

    /** Test method for {@link Sphere#getNormal(Point)} . */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Check that normal
        Sphere sph = new Sphere(5, new Point(0,0,0));
        // ensure there are no exceptions
        assertDoesNotThrow(() -> sph.getNormal(new Point(0, 0, 5)), "");
        // generate the test result
        Vector result = sph.getNormal(new Point(0, 0, 5));
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Sphere's normal is not a unit vector");
        // Check that vector returned is normal to the sphere at the point (i.e. given point minus center, normalized)
        assertEquals(new Point(0,0,1), result, "Result is not the normal to the sphere at the point");
    }

    /** Test method for {@link Sphere#getNormal(Point)} . */
    @Test
    public void testFindGeoIntersections() {
        Sphere sphere = new Sphere(1d,  new Point (1, 0, 0));

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findGeoIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0))),
                "Ray's line out of sphere");
        // TC02: Ray starts before and crosses the sphere (2 points)
        GeoPoint p1 = new GeoPoint(sphere, new Point(0.06515307716504659,0.35505102572168223,0.0));
        GeoPoint p2 = new GeoPoint(sphere, new Point(1.5348469228349528,0.8449489742783177,0.0));
        List<GeoPoint> result = sphere.findGeoIntersections(new Ray(new Point(-1, 0, 0),
                new Vector(3, 1, 0)));
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).point.getX() > result.get(1).point.getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "Ray crosses sphere");
        // TC03: Ray starts inside the sphere (1 point)
        Ray inner = new Ray(new Point(1.5, 0, 0), new Vector(1,0,0));
        result = sphere.findGeoIntersections(inner);
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(new GeoPoint(sphere, new Point(2,0,0)), result.get(0), "Ray inside sphere");
        // TC04: Ray starts after the sphere (0 points)
        Ray beyond = new Ray(new Point(3, 0, 0), new Vector(1,0,0));
        assertNull(sphere.findGeoIntersections(beyond), "Wrong number of points");

        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 point)
        Ray r = new Ray(new Point(0.552677017181699, -0.543077013329605, 0.710612064797336), new Vector(1, 0, 0));
        GeoPoint p = new GeoPoint(sphere, new Point(1.4473229828183019,-0.543077013329605,0.710612064797336));
        result = sphere.findGeoIntersections(r);
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(p, result.get(0), "Ray on sphere");
        // TC12: Ray starts at sphere and goes outside (0 points)
        Ray r2 = new Ray(new Point(0.552677017181699, -0.543077013329605, 0.710612064797336), new Vector(-1, 0, 0));
        assertNull(sphere.findGeoIntersections(r2), "Ray starts at sphere and goes outside");
        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        r = new Ray(new Point(-1, 0, 0), new Vector(1, 0, 0));
        result = sphere.findGeoIntersections(r);
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).point.getX() > result.get(1).point.getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(new GeoPoint(sphere, new Point(0,0,0)), new GeoPoint(sphere, new Point(2, 0, 0))), result, "Ray starts before the sphere");
        // TC14: Ray starts at sphere and goes inside (1 point)
        r = new Ray(new Point(0,0,0), new Vector(1,0,0));
        result = sphere.findGeoIntersections(r);
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(new GeoPoint(sphere, new Point(2,0,0)), result.get(0), "Ray starts at sphere and goes inside");
        // TC15: Ray starts inside (1 point)
        r = new Ray(new Point(1.5,0,0), new Vector(1,0,0));
        result = sphere.findGeoIntersections(r);
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(new GeoPoint(sphere, new Point(2,0,0)), result.get(0), "Ray starts inside");
        // TC16: Ray starts at the center (1 point)
        r = new Ray(new Point(1,0,0), new Vector(1,0,0));
        result = sphere.findGeoIntersections(r);
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(new GeoPoint(sphere, new Point(2,0,0)), result.get(0), "Ray starts at the center");
        // TC17: Ray starts at sphere and goes outside (0 points)
        r = new Ray(new Point(2,0,0), new Vector(1,0,0));
        assertNull(sphere.findGeoIntersections(r), "Ray starts at sphere and goes outside");
        // TC18: Ray starts after sphere (0 points)
        r = new Ray(new Point(2.5,0,0), new Vector(1,0,0));
        assertNull(sphere.findGeoIntersections(r), "Ray starts at sphere and goes outside");
        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        r = new Ray(new Point(0,-1,0), new Vector(0,1,0));
        assertNull(sphere.findGeoIntersections(r), "Ray starts before the tangent point");
        // TC20: Ray starts at the tangent point
        r = new Ray(new Point(0,0,0), new Vector(0,1,0));
        assertNull(sphere.findGeoIntersections(r), "Ray starts at the tangent point");
        // TC21: Ray starts after the tangent point
        r = new Ray(new Point(0,1,0), new Vector(0,1,0));
        assertNull(sphere.findGeoIntersections(r), "Ray starts after the tangent point");
        // **** Group: Special cases
        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        r = new Ray(new Point(-1,0,0), new Vector(0,1,0));
        assertNull(sphere.findGeoIntersections(r), "Ray's line is outside, ray is orthogonal to ray start to sphere's center line");
    }
}
