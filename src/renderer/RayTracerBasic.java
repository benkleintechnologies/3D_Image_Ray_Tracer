package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * Class RayTracerBasic which inherits from RayTracerBase
 * @author Eli Hawk and Binyamin Klein
 * 563385586 & 576708589
 */
public class RayTracerBasic extends RayTracerBase {

    /**
     * Constructor for RayTracerBase
     * @param scene the scene
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        return null;
    }
}
