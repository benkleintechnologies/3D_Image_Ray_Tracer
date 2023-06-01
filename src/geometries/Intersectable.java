package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;

/**
 * Intersectable Interface
 * @author Eli Hawk and Binyamin Klein
 * 563385586 & 576708589
 */
public abstract class Intersectable {

    /**
     * Static inner class GeoPoint which contains a geometry and a point
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point point;

        /**
         * Constructor for GeoPoint
         * @param geometry of the GeoPoint
         * @param point of the GeoPoint
         */
        public GeoPoint(Geometry geometry, Point point){
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return geoPoint.geometry.equals(this.geometry) && geoPoint.point.equals(this.point);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }

    /**
     * Find the points of intersection of a ray with the geometry
     * @param ray a ray which is intersecting the geometry
     * @return the points of intersection of ray with this geometry
     */
    public final List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }

    /**
     * Find the points of intersection of a ray with the geometry
     * @param ray a ray which is intersecting the geometry
     * @return the points of intersection of ray with this geometry
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);

    /**
     * Find the points of intersection of a ray with the geometry
     * @param ray a ray which is intersecting the geometry
     * @return the points of intersection of ray with this geometry
     */
    public List<GeoPoint> findGeoIntersections(Ray ray){
        return findGeoIntersectionsHelper(ray, Double.POSITIVE_INFINITY);
    }

    /**
     * Find the points of intersection of a ray with the geometry closer than a certain distance from the Ray
     * @param ray which is intersecting the geometry
     * @param maxDistance the maximum distance of a point to return
     * @return the points of intersection of ray with this geometry within the distance
     */
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance){
        return findGeoIntersectionsHelper(ray, maxDistance);
    }
}
