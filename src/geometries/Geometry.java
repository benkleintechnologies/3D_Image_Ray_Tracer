package geometries;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Geometry Interface which extends Intersectable
 * @author Eli Hawk and Binyamin Klein
 * 563385586 & 576708589
 */
public abstract class Geometry extends Intersectable {
    protected Color emission = Color.BLACK;

    /**
     * Get the normal to the geometry at a point
     * @param p a point on the geometry
     * @return the vector perpendicular to the geometry at the point
     */
    public abstract Vector getNormal(Point p);

    /**
     * Getter for emission color
     * @return emission
     */
    public Color getEmission(){
        return emission;
    }

    /**
     * Setter for emission
     * @param color emission color
     */
    public Geometry setEmission(Color color){
        this.emission = color;
        return this;
    }
}
