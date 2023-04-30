package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * Abstract Class RayTraceBase is the basis for ray tracing
 * @author Eli Hawk and Binyamin Klein
 * 563385586 & 576708589
 */
public abstract class RayTraceBase {
    protected Scene scene;

    /**
     * Constructor for RayTraceBase
     * @param scene the scene
     */
    public RayTraceBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * Traces the ray and returns the color
     * @param ray the ray
     * @return the color
     */
    public abstract Color traceRay(Ray ray);
}
