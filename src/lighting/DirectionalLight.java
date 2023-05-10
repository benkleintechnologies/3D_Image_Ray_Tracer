package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;


/**
 * Class DirectionalLight represents the directional light source.
 * @author Eli Hawk and Binyamin Klein
 * 563385586 & 576708589
 */
public class DirectionalLight extends Light implements LightSource{
     private Vector direction;

    /**
     * public constructor for the DirectionalLight class
     *
     * @param color the color to initialize intensity to.
     * @param direction the direction the light travels in.
     */
    public DirectionalLight(Color color, Vector direction) {
        super(color);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point p) {
        return super.getIntensity();
    }

    @Override
    public Vector getL(Point p) {
        return direction;
    }

    @Override
    public double getDistance(Point p) {
        return Double.POSITIVE_INFINITY;
    }
}
