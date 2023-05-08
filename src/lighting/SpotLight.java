package lighting;
import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Class SpotLight represents the directional light source.
 * @author Eli Hawk and Binyamin Klein
 * 563385586 & 576708589
 */
public class SpotLight extends PointLight{
    private Vector direction;

    /**
     * Public constructor for the SpotLight class
     *
     * @param color the color to initialize intensity to
     * @param position the location of the light
     * @param direction the direction of the light
     */
    public SpotLight(Color color, Point position, Vector direction) {
        super(color, position);
        this.direction = direction;
    }

    @Override
    public Color getIntensity(Point p){
        Color pointIntensity = super.getIntensity(p);
        double factor = direction.dotProduct(getL(p));
        if (0 > factor){
            return pointIntensity.scale(0);
        }
        return pointIntensity.scale(factor);
    }
}
