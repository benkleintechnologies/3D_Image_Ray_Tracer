package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * Class AmbientLight represents the ambient light in the scene in a 3D space.
 * Inherits from the abstract class Light.
 * @author Eli Hawk and Binyamin Klein
 * 563385586 & 576708589
 */
public class AmbientLight extends Light {
    public static final AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

    /**
     * Constructor which calculates and sets the final value of the color
     * @param color the initial color of the light
     * @param attenuation the attenuation factor of the original light
     */
    public AmbientLight(Color color, Double3 attenuation){
        super(color.scale(attenuation));
    }

    /**
     * Default constructor which sets the color to be black
     */
    public AmbientLight(){
        super(Color.BLACK);
    }
}
