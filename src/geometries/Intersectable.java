package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * Intersectable Interface
 * @author Eli Hawk and Binyamin Klein
 * 563385586 & 576708589
 */
public interface Intersectable {

    /**
     * Find the points of intersection of a ray with the geometry
     * @param ray a ray which is intersecting the geometry
     * @return the points of intersection of ray with this geometry
     */
    List<Point> findIntsersections(Ray ray);
}
