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
    //kT - transparency  coefficient
    public Double3 kT = new Double3(0);
    //kR - reflection coefficient
    public Double3 kR = new Double3(0);
    //nShininess - shininess level
    public int nShininess = 0;
    //nGlossiness - glossiness level
    public double nGlossiness = 0;
    //nBlur - diffusive level
    public double nBlur = 0;


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
     * Setter for kT which receives Double3
     * @param kT transparency coefficient
     * @return this material object
     */
    public Material setKt(Double3 kT){
        this.kT = kT;
        return this;
    }

    /**
     * Setter for kT which receives Double
     * @param kT transparency coefficient
     * @return this material object
     */
    public Material setKt(Double kT){
        this.kT = new Double3(kT);
        return this;
    }

    /**
     * Setter for kR which receives Double3
     * @param kR reflection coefficient
     * @return this material object
     */
    public Material setKr(Double3 kR){
        this.kR = kR;
        return this;
    }

    /**
     * Setter for kR which receives Double
     * @param kR reflection coefficient
     * @return this material object
     */
    public Material setKr(Double kR){
        this.kR = new Double3(kR);
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

    /**
     * Setter for nGlossiness
     * @param nGlossiness glossiness level
     * @return this material object
     */
    public Material setGlossiness(double nGlossiness) {
        this.nGlossiness = nGlossiness;
        return this;
    }

    /**
     * Setter for nBlur
     * @param nBlur diffusive level
     * @return this material object
     */
    public Material setBlur(double nBlur) {
        this.nBlur = nBlur;
        return this;
    }
}
