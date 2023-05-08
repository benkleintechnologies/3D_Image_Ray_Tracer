package lighting;
import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Class DirectionalLight represents the directional light source.
 * @author Eli Hawk and Binyamin Klein
 * 563385586 & 576708589
 */
public class PointLight extends Light implements LightSource{
    private Point position;
    private double kC, kL, kQ;

    /**
     * Public constructor for the PointLight class
     * and set the default values for the attenuation factors.
     * @param color the color to initialize intensity to
     * @param position the location of the light
     */
    public PointLight(Color color, Point position) {
        super(color);
        this.position = position;
        kC = 1;
        kL = 0;
        kQ = 0;

    }

    /**
     * Set attenuation factor kC
     * @param kC one of the attenuation factors.
     * @return the current PointLight object
     */
    public PointLight setkC(double kC){
        this.kC = kC;
        return this;
    }

    /**
     * Set attenuation factor kL
     * @param kL one of the attenuation factors.
     * @return the current PointLight object
     */
    public PointLight setkL(double kL){
        this.kC = kL;
        return this;
    }

    /**
     * Set attenuation factor kQ
     * @param kQ one of the attenuation factors.
     * @return the current PointLight object
     */
    public PointLight setkQ(double kQ){
        this.kQ = kQ;
        return this;
    }

    @Override
    public Color getIntensity(Point p) {
        Color I0 = super.getIntensity();
        double d = p.distance(position);
        double d2 = p.distanceSquared(position);
        double scaleFactor = 1/(kC+kL*d+kQ*d2);
        return I0.scale(scaleFactor);
    }


    @Override
    public Vector getL(Point p) {
        return p.subtract(position);
    }
}
