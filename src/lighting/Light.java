package lighting;

import primitives.Color;


/**
 * Class Light is the basic class of Light representing a color.
 * @author Eli Hawk and Binyamin Klein
 * 563385586 & 576708589
 */
public abstract class Light {
    private Color intensity;

    /**
     * Protected constructor for the Light class
     * @param color the color to initialize intensity to
     */
    protected Light(Color color){
        intensity = color;
    }

    /**
     * Get for the intensity field
     * @return the intensity of the Light
     */
    public Color getIntensity(){
        return intensity;
    }


}
