package renderer;

import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;
import geometries.Intersectable.GeoPoint;

import static primitives.Util.alignZero;

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

    /*
    private boolean unshaded(GeoPoint gp, LightSource light, Vector l, Vector n, double nv) {

        Vector lightDirection = l.scale(-1); // from point to light source

        Vector epsVector = n.scale(EPS);
        Point point = gp.point.add(epsVector);

        Ray lightRay = new Ray(gp.point, lightDirection);

        List<Point> intersections = scene.geometries.findIntersections(lightRay);
        if (intersections == null) return true;

        //if there are points in the intersections list that are closer to the point than light source – return false
        //otherwise – return true
        double lightDistance = light.getDistance(gp.point);
        for (Point p : intersections) {
            if (alignZero(p.distance(gp.point) - lightDistance) <= 0)
                return false;
        }

        return true;
    }
     */

    /**
     * Function to calculate the diffusive component of the color
     * @param material the material of the geometry
     * @param nl the dot product of the normal and the light vector
     * @return the diffusive component of the color
     */
    private Double3 calcDiffusive(Material material, double nl) {
        //𝒌𝑫∙|𝒍𝒊∙𝒏|
        return material.kD.scale(Math.abs(nl));
    }

    /**
     * Function to calculate the specular component of the color
     * @param material the material of the geometry
     * @param n the normal vector
     * @param l the light vector
     * @param nl the dot product of the normal and the light vector
     * @param v the vector from the camera to the point
     * @return the specular component of the color
     */
    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
        //𝒓=𝒍−𝟐∙ 𝒍∙𝒏 ∙𝒏
        Vector r = l.subtract(n.scale(2 * nl));
        //𝒌𝑺∙(𝒎𝒂𝒙(𝟎,−𝒗∙𝒓))^𝒏𝒔𝒉𝒊𝒏𝒊𝒏𝒆𝒔𝒔
        double minusVR = -alignZero(v.dotProduct(r));
        if (minusVR <= 0) return new Double3(0, 0, 0);
        return material.kS.scale(Math.pow(minusVR, material.nShininess));
    }

    /**
     * Function to calculate the local effects of the light on the geometry
     * @param gp the point on the geometry
     * @param ray the ray that intersects the point
     * @return the color of the point
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        Color color = gp.geometry.getEmission(); //iE
        Vector v = ray.getDirection();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return color;
        Material material = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sign(nv)
                Color iL = lightSource.getIntensity(gp.point);
                color = color.add(iL.scale(calcDiffusive(material, nl)), iL.scale(calcSpecular(material, n, l, nl, v)));
            }
        }
        return color;
    }

    /**
     * Calculate the color of the pixel
     * @param intersection the point that we wish to get its color
     * @param ray the ray that intersects the point
     * @return the color of the point
     */
    private Color calcColor(GeoPoint intersection, Ray ray){
        return scene.ambientLight.getIntensity().add(calcLocalEffects(intersection, ray));
    }

    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersectionsHelper(ray);

        if(intersections == null){
            return scene.background;
        }

        GeoPoint closest = ray.findClosestGeoPoint(intersections);
        return calcColor(closest, ray);
    }
}
