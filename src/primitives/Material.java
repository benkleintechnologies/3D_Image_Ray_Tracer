package primitives;

/**
 * Material class represents the material of a geometry in the scene
 * @author Eli Hawk and Binyamin Klein
 * 563385586 & 576708589
 */
public class Material {
    //kD - diffuse attenuation coefficient
    public Double3 kD = new Double3(0);
    //kS - specular attenuation coefficient
    public Double3 kS = new Double3(0);
    //nShininess - shininess level
    public int nShininess = 0;

    /**
     * Setter for kD which receives Double3
     * @param kD diffuse attenuation coefficient
     * @return this material object
     */
    public Material setKd(Double3 kD){
        this.kD = kD;
        return this;
    }

    /**
     * Setter for kD which receives Double
     * @param kD diffuse attenuation coefficient
     * @return this material object
     */
    public Material setKd(Double kD){
        this.kD = new Double3(kD);
        return this;
    }

    /**
     * Setter for kS which receives Double3
     * @param kS specular attenuation coefficient
     * @return this material object
     */
    public Material setKs(Double3 kS){
        this.kS = kS;
        return this;
    }

    /**
     * Setter for kS which receives Double
     * @param kS specular attenuation coefficient
     * @return this material object
     */
    public Material setKs(Double kS){
        this.kS = new Double3(kS);
        return this;
    }

    /**
     * Setter for nShininess
     * @param nShininess shininess level
     * @return this material object
     */
    public Material setShininess(int nShininess){
        this.nShininess = nShininess;
        return this;
    }
}
