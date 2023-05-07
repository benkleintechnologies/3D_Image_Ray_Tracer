package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * Class AmbientLight represents the ambient light in the scene in a 3D space.
 * @author Eli Hawk and Binyamin Klein
 * 563385586 & 576708589
 */
public class AmbientLight {
    Color intensity;
    public static final AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

    /**
     * Constructor which calculates and sets the final value of the color
     * @param color the initial color of the light
     * @param attenuation the attenuation factor of the original light
     */
    public AmbientLight(Color color, Double3 attenuation){
        intensity = color.scale(attenuation);
    }

    /**
     * Default constructor which sets the color to be black
     */
    public AmbientLight(){
        intensity = Color.BLACK;
    }

    /**
     * Getter for the color
     * @return intensity
     */
    public Color getIntensity(){
        return intensity;
    }

}
