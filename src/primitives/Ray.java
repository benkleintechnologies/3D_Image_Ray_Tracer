package primitives;

import java.util.List;
import geometries.Intersectable.GeoPoint;

/**
 * Class Ray is the basic class representing a ray starting at a point in Cartesian
 * 3-Dimensional coordinate system.
 * @author Eli Hawk and Binyamin Klein
 * 563385586 & 576708589
 */
public class Ray {
    final Point point;
    final Vector direction;

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
     * Finds the closest point to the ray's head
     * @param points list of points
     * @return closest point
     */
    public Point findClosestPoint(List<Point> points) {
        Point closestPoint = null;
        double minDistance = Double.MAX_VALUE;

        //loop through points and find closest to head
        for (Point point : points) {
            double distance = point.distance(this.point);
            if (distance < minDistance) {
                minDistance = distance;
                closestPoint = point;
            }
        }
        return closestPoint;
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
