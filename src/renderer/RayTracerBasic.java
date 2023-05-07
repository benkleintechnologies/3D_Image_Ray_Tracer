package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.List;
import geometries.Intersectable.GeoPoint;

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

    /**
     * Calculate the color of the pixel
     * @param p the point that we wish to get its color
     * @return the color of the point
     */
    private Color calcColor(GeoPoint p){
        return scene.ambientLight.getIntensity().add(p.geometry.getEmission());
    }

    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersectionsHelper(ray);

        if(intersections == null){
            return scene.background;
        }

        GeoPoint closest = ray.findClosestGeoPoint(intersections);
        return calcColor(closest);
    }
}
