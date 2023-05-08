package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;


/**
 * Class LightSource represents the light source.
 * @author Eli Hawk and Binyamin Klein
 * 563385586 & 576708589
 */
public interface LightSource {
    /**
     * Get the intensity of the light
     * @param p the point to check the lighting for
     * @return the Color of that point
     */
    Color getIntensity(Point p);

    /**
     * Returns the vector from the light source to that point.
     * @param p the point to check its light intensity.
     * @return the Vector from the light source and the point p.
     */
    Vector getL(Point p);
}
