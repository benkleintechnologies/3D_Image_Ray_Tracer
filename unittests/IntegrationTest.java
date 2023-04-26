import geometries.*;
import primitives.*;
import renderer.Camera;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Integration Tests for the Camera and Geometry intersections
 * @author Eli Hawk and Binyamin Klein
 * 563385586 & 576708589
 */
public class IntegrationTest {

    /**
     * Helper function to create Camera object with 3x3 view plane at a distance of 1
     * @param p0 position of the camera
     * @return Camera object
     */
    private Camera createCamera(Point p0) {
        Camera camera = new Camera(p0, new Vector(0, 0, -1), new Vector(0, 1, 0));
        camera.setVPDistance(1);
        camera.setVPSize(3, 3);
        return camera;
    }

    /**
     * Helper function to create a list of rays from the camera to the view plane
     * @param camera the camera
     * @return a list of rays
     */
    private List<Ray> generateRays(Camera camera) {
        List<Ray> rays = new ArrayList<>();
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                rays.add(camera.constructRay(3, 3, y, x));
            }
        }
        return rays;
    }

    /**
     * Helper function to find intersections between a list of rays and a geometry
     * @param rays a list of rays
     * @param geometry the geometry
     * @return a list of intersections
     */
    private List<Point> intersections(List<Ray> rays, Geometry geometry) {
        List<Point> intersections = new ArrayList<>();
        for (Ray ray : rays) {
            if (geometry.findIntersections(ray) == null) {
                continue;
            }
            intersections.addAll(geometry.findIntersections(ray));
        }
        if (intersections.isEmpty()) {
            return null;
        }
        return intersections;
    }

    /**
     * Test method for integration of Camera and Sphere Intersections
     */
    @Test
    void testCameraSphereIntersections() {
        //=============== First Test Case ===============//
        //Create Camera at Origin with distance 1 from the view plane which is size 3x3
        Camera camera = createCamera(new Point(0, 0, 0));
        //Create a list of rays from the camera to the view plane at each pixel
        List<Ray> rays = generateRays(camera);
        //Create a sphere
        Sphere sphere = new Sphere(1, new Point(0, 0, -3));
        //Find intersections
        List<Point> intersections = intersections(rays, sphere);
        //Check the number of intersections
        assertEquals(2, intersections.size(), "Wrong number of intersections, First Camera-Sphere test");

        //=============== Second Test Case ===============//
        //Create Camera at Origin with distance 1 from the view plane which is size 3x3
        camera = createCamera(new Point(0, 0, .5));
        //Create a list of rays from the camera to the view plane at each pixel
        rays = generateRays(camera);
        //Create a sphere
        sphere = new Sphere(2.5, new Point(0, 0, -2.5));
        //Find intersections
        intersections = intersections(rays, sphere);
        //Check the number of intersections
        assertEquals(18, intersections.size(), "Wrong number of intersections, Second Camera-Sphere test");

        //=============== Third Test Case ===============//
        //Create a sphere
        sphere = new Sphere(2, new Point(0, 0, -2));
        //Find intersections
        intersections = intersections(rays, sphere);
        //Check the number of intersections
        assertEquals(10, intersections.size(), "Wrong number of intersections, Third Camera-Sphere test");

        //=============== Fourth Test Case ===============//
        //Create Camera at Origin with distance 1 from the view plane which is size 3x3
        camera = createCamera(new Point(0, 0, 0));
        //Create a list of rays from the camera to the view plane at each pixel
        rays = generateRays(camera);
        //Create a sphere
        sphere = new Sphere(4, new Point(0, 0, -1));
        //Find intersections
        intersections = intersections(rays, sphere);
        //Check the number of intersections
        assertEquals(9, intersections.size(), "Wrong number of intersections, Fourth Camera-Sphere test");

        //=============== Fifth Test Case ===============//
        //Create a sphere
        sphere = new Sphere(0.5, new Point(0, 0, 1));
        //Find intersections
        intersections = intersections(rays, sphere);
        //Check the number of intersections
        assertNull(intersections, "Should be no intersections, Fifth Camera-Sphere test");
    }

    /**
     * Test method for integration of Camera and Plane Intersections
     */
    @Test
    void testCameraPlaneIntersections() {
        //=============== First Test Case ===============//
        //Create Camera at Origin with distance 1 from the view plane which is size 3x3
        Camera camera = createCamera(new Point(0, 0, 0));
        //Create a list of rays from the camera to the view plane at each pixel
        List<Ray> rays = generateRays(camera);
        //Create a plane
        Plane plane = new Plane(new Point(0, 0, -5), new Vector(0, 0, 1));
        //Find intersections
        List<Point> intersections = intersections(rays, plane);
        //Check the number of intersections
        assertEquals(9, intersections.size(), "Wrong number of intersections, First Camera-Plane test");

        //=============== Second Test Case ===============//
        //Create a plane
        plane = new Plane(new Point(0, 0, -5), new Vector(0, -1, 3));
        //Find intersections
        intersections = intersections(rays, plane);
        //Check the number of intersections
        assertEquals(9, intersections.size(), "Wrong number of intersections, Second Camera-Plane test");

        //=============== Third Test Case ===============//
        //Create a plane
        plane = new Plane(new Point(0, 0, -5), new Vector(0, -5, 3));
        //Find intersections
        intersections = intersections(rays, plane);
        //Check the number of intersections
        assertEquals(6, intersections.size(), "Wrong number of intersections, Third Camera-Plane test");
    }

    /**
     * Test method for integration of Camera and Triangle Intersections
     */
    @Test
    void testCameraTriangleIntersections() {
        //=============== First Test Case ===============//
        //Create Camera at Origin with distance 1 from the view plane which is size 3x3
        Camera camera = createCamera(new Point(0, 0, 0));
        //Create a list of rays from the camera to the view plane at each pixel
        List<Ray> rays = generateRays(camera);
        //Create a triangle
        Triangle triangle = new Triangle(new Point(0, 1, -2), new Point(-1, -1, -2), new Point(1, -1, -2));
        //Find intersections
        List<Point> intersections = intersections(rays, triangle);
        //Check the number of intersections
        assertEquals(1, intersections.size(), "Wrong number of intersections, First Camera-Triangle test");

        //=============== Second Test Case ===============//
        //Create a triangle
        triangle = new Triangle(new Point(0, 20, -2), new Point(-1, -1, -2), new Point(1, -1, -2));
        //Find intersections
        intersections = intersections(rays, triangle);
        //Check the number of intersections
        assertEquals(2, intersections.size(), "Wrong number of intersections, Second Camera-Triangle test");
    }
}
