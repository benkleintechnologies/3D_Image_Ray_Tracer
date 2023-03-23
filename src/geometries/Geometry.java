package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Geometry Interface which extends Intersectable
 * @author Eli Hawk and Binyamin Klein
 * 563385586 & 576708589
 */
public interface Geometry extends Intersectable {

    /**
     * Get the normal to the geometry at a point
     * @param p a point on the geometry
     * @return the vector perpendicular to the geometry at the point
     */
    public Vector getNormal(Point p);
}
