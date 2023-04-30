package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * Abstract Class RayTracerBase is the basis for ray tracing
 * @author Eli Hawk and Binyamin Klein
 * 563385586 & 576708589
 */
public abstract class RayTracerBase {
    protected Scene scene;

    /**
     * Constructor for RayTracerBase
     * @param scene the scene
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * Traces the ray and returns the color
     * @param ray the ray
     * @return the color
     */
    public abstract Color traceRay(Ray ray);
}
