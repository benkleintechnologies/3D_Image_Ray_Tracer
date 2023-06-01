package primitives;

import java.util.LinkedList;
import java.util.List;
import geometries.Intersectable.GeoPoint;
import lighting.LightSource;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Class Ray is the basic class representing a ray starting at a point in Cartesian
 * 3-Dimensional coordinate system.
 * @author Eli Hawk and Binyamin Klein
 * 563385586 & 576708589
 */
public class Ray {
    final Point point;
    final Vector direction;
    private static final double DELTA = 0.1;

    /**
     * Constructor for Ray
     * @param point head point of ray
     * @param direction direction of ray
     */
    public Ray(Point point, Vector direction) {
        this.point = point;
        this.direction = direction.normalize();
    }


    /**
     * Constructor for Ray which moves the head of the ray by a small amount in the direction of the normal
     * @param head head point of ray
     * @param direction direction of ray
     * @param normal normal vector of ray
     */
    public Ray(Point head, Vector direction, Vector normal) {
        double nv = normal.dotProduct(direction);
        Vector delta = normal.scale((nv > 0 ? DELTA : -DELTA));
        point = head.add(delta);
        this.direction = direction.normalize();
    }

    /**
     * Finds the closest point to the ray's head
     * @param points list of points
     * @return closest point
     */
    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }

    /**
     * Getter for point
     * @return starting point of ray
     */
    public Point getPoint() {
        return point;
    }

    /**
     * Calculates 𝑷 = 𝑷𝟎 + 𝒕∙𝒗, the point found by adding the vector v of the ray scaled by t
     * @param t factor to scale the ray
     * @return 𝑷 = 𝑷𝟎 + 𝒕∙𝒗
     */
    public Point getPoint(double t){
        return point.add(direction.scale(t));
    }

    /**
     * Getter for direction
     * @return direction of ray
     */
    public Vector getDirection() {
        return direction;
    }

    /**
     * Find the closest GeoPoint
     * @param points the points to search
     * @return GeoPoint
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> points){
        GeoPoint closestPoint = null;
        double minDistance = Double.MAX_VALUE;

        //loop through points and find closest to head
        for (GeoPoint point : points) {
            double distance = point.point.distance(this.point);
            if (distance < minDistance) {
                minDistance = distance;
                closestPoint = point;
            }
        }
        return closestPoint;
    }


    /**
     * Create a beam of rays from the original ray
     * @param centerPoint center point of the beam
     * @param numRays number of rays to create
     * @param radius radius of the circle
     * @return list of rays
     */
    public List<Ray> createRaysBeam(Point centerPoint, int numRays, double radius){
        List<Ray> rayList = new LinkedList<Ray>();
        rayList.add(this);                          // adding the original ray
        if (radius == 0) return rayList;            // if radius is 0, we return the original ray
        List<Point> pointList = createRandomPoints(centerPoint, radius, numRays);
        for (Point p : pointList)             //from every point in the point list we make a ray
            rayList.add(new Ray(point, p.subtract(point).normalize()));
        return rayList;
    }

    /**
     * Creates a list of random points in a circle around the center point on the plane defined by the direction vector
     * @param centerPoint center point of circle
     * @param radius radius of circle
     * @param numRays number of rays to create
     * @return list of random points
     */
    private List<Point> createRandomPoints(Point centerPoint, double radius, int numRays){
        List<Point> randomPoints = new LinkedList<Point>();
        Vector vX = direction.normalize().getNormal(), vY = vX.crossProduct(direction.normalize());
        double x, y;
        for (int i = 0; i < numRays; i++) {
            x = Math.random();
            y = Math.sqrt(1 - x * x);
            Point p = centerPoint;
            //get random number between -radius and radius
            double d = Math.random() * radius * 2 - radius;
            x = alignZero(x * d);
            y = alignZero(y * d);
            if (!isZero(x)) p = p.add(vX.scale(x));
            if (!isZero(y)) p = p.add(vY.scale(y));
            randomPoints.add(p);
        }
        return randomPoints;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Ray ray)) return false;
        return (ray.point.equals(this.point) && ray.direction.equals(this.direction));
    }

    @Override
    public String toString() {
        return "point = " + point + ", direction = " + direction;
    }
}
