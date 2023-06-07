package renderer;

import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.LinkedList;
import java.util.List;
import geometries.Intersectable.GeoPoint;

import static primitives.Util.*;

/**
 * Class RayTracerBasic which inherits from RayTracerBase
 * @author Eli Hawk and Binyamin Klein
 * 563385586 & 576708589
 */
public class RayTracerBasic extends RayTracerBase {
    //Maximal level of recursion for calculating the color
    private static final int MAX_CALC_COLOR_LEVEL = 5;
    //Minimal transparency that's considered significant to be calculating
    private static final double MIN_CALC_COLOR_K = 0.01;
    //The starting transparency factor
    private static final double INITIAL_K = 1.0;
    //Boolean to determine whether to use adaptive super sampling
    private boolean SS = false;
    //Number of rays for super sampling
    private int numRays = 0;
    //Boolean to determine whether to use adaptive super sampling
    private boolean adaptiveSS = false;

    /**
     * Constructor for RayTracerBase
     * @param scene the scene
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * Calculates Transparency through a geometry from a light source (above a minimal level)
     * @param gp point on geometry
     * @param light light source
     * @param l vector from light
     * @param n normal to geometry
     * @return level of transparency as Double3
     */
    private Double3 transparency(GeoPoint gp, LightSource light, Vector l, Vector n){
        Vector lightDirection = l.scale(-1); // from point to light source

        Ray lightRay = new Ray(gp.point, lightDirection, n);

        double lightDistance = light.getDistance(gp.point);

        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, lightDistance);
        if (intersections == null) return Double3.ONE;
        Double3 ktr = Double3.ONE;
        //if there are points in the intersections list that are closer to the point than light source (and kT==0) – return false
        //otherwise – return true
        for (GeoPoint p : intersections) {
            ktr = ktr.product(p.geometry.getMaterial().kT);
            if (ktr.lowerThan(MIN_CALC_COLOR_K)){
                return Double3.ZERO;
            }
        }
        return ktr;
    }

    /**
     * Function to calculate the reflective ray
     * @param gp the geoPoint
     * @param v the vector from the camera to the point
     * @param n the normal vector
     * @return the reflective ray
     */
    private Ray findReflectedRay(GeoPoint gp, Vector v, Vector n) {
        //𝒓=𝒗−𝟐∙ 𝒗∙𝒏 ∙𝒏
        Vector r = v.subtract(n.scale(2 * v.dotProduct(n)));
        //𝒓𝒂𝒚=𝒑𝒐𝒊𝒏𝒕+𝒌∙𝒓
        return new Ray(gp.point, r, n);
    }

    /**
     * Function to calculate the refracted ray
     * @param gp the geoPoint
     * @param v the vector from the camera to the point
     * @param n the normal vector
     * @return the refracted ray
     */
    private Ray findRefractedRay(GeoPoint gp, Vector v, Vector n) {
        return new Ray(gp.point, v, n);
    }

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
     * Calculates local effects (like diffusion,specular,transparency) of ray on a point of a geometry
     * @param gp point on geometry
     * @param ray that intersects the point
     * @param k transparency factor
     * @return color of the point
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
        Color color = gp.geometry.getEmission(); //iE
        Vector v = ray.getDirection().normalize();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = alignZero(n.dotProduct(v));
        if (isZero(nv)) return color;
        Material material = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (checkSign(nl, nv)) { // sign(nl) == sign(nv)
                Double3 ktr = transparency(gp, lightSource, l, n);
                if (ktr.product(k).greaterThan(MIN_CALC_COLOR_K)) {
                    Color iL = lightSource.getIntensity(gp.point).scale(ktr);
                    color = color.add(iL.scale(calcDiffusive(material, nl)), iL.scale(calcSpecular(material, n, l, nl, v)));
                }
            }
        }
        return color;
    }

    /**
     * Calculates the global effects (reflection/refraction) of the ray on a point, recursively
     * @param gp point on geometry
     * @param ray intersecting geometry
     * @param level of recursion
     * @param k transparency factor
     * @return color of the point
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Vector v = ray.getDirection();
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        Ray reflectedRay = findReflectedRay(gp, v, n);
        Ray refractedRay = findRefractedRay(gp, v, n);

        //Find target sizes for reflected and refracted rays
        double reflectedTargetSize = material.nGlossiness * gp.point.distance(scene.getCamera().getP0()) / 10000;
        double refractedTargetSize = material.nBlur * gp.point.distance(scene.getCamera().getP0());

        if (adaptiveSS) { //Adaptive super sampling is on. Perform calculations for glossy and diffusion)
            Color reflectedColor = calcAdaptiveSS(reflectedRay, reflectedTargetSize, material.kR, level, k);
            Color refractedColor = calcAdaptiveSS(refractedRay, refractedTargetSize, material.kT, level, k);
            //Add Reflected and refracted colors
            return refractedColor.add(reflectedColor);

        }else if (SS) { //Super sampling is on. Perform calculations for glossy and diffusion
            return calcSuperSamplingColor(material, level, k, reflectedRay, reflectedTargetSize, refractedRay, refractedTargetSize);
        }

        return calcColorGLobalEffect(reflectedRay, level, k, material.kR).add(calcColorGLobalEffect(refractedRay, level, k, material.kT));
    }

    /**
     * Calculates the color of the point with global effects (reflection/refraction)
     * @param material the material of the geometry
     * @param level of recursion
     * @param k transparency factor
     * @param reflectedRay the reflected ray
     * @param reflectedTargetSize the target size of the reflected ray
     * @param refractedRay the refracted ray
     * @param refractedTargetSize the target size of the refracted ray
     * @return the color of the point
     */
    private Color calcSuperSamplingColor(Material material, int level, Double3 k, Ray reflectedRay, double reflectedTargetSize, Ray refractedRay, double refractedTargetSize) {
        Color color = Color.BLACK;

        //Calculate reflected and refracted ray beams
        List<Ray> reflectedRays = reflectedRay.createRaysBeam(reflectedRay.getPoint().add(reflectedRay.getDirection().scale(100)), numRays, reflectedTargetSize);
        List<Ray> refractedRays = refractedRay.createRaysBeam(refractedRay.getPoint().add(refractedRay.getDirection().scale(100)), numRays, refractedTargetSize);

        //Calculate the color of the average of reflected rays
        for (Ray r : reflectedRays) {
            color = color.add(calcColorGLobalEffect(r, level, k, material.kR).reduce(reflectedRays.size()));
        }

        //calculate the color of the average of refracted rays
        Color refractedColor = Color.BLACK;
        for (Ray r : refractedRays) {
            refractedColor = refractedColor.add(calcColorGLobalEffect(r, level, k, material.kT).reduce(refractedRays.size()));
        }

        //Add Reflected and refracted colors
        color = color.add(refractedColor);

        return color;
    }

    /**
     * Calculates the color of the point with global effects (reflection/refraction) using adaptive super sampling
     * @param ray the ray to calculate the color for
     * @param sideLength the side length of the square
     * @param kX the transparency factor
     * @param level of recursion
     * @param k transparency factor
     * @return the color of the point
     */
    private Color calcAdaptiveSS(Ray ray, double sideLength, Double3 kX, int level, Double3 k){
        List<Ray> rays = ray.createRaySquare(ray.getPoint().add(ray.getDirection().scale(100)), sideLength);
        List<Color> tempColors = new LinkedList<>();
        //Add 4 colors to temporary list
        for (Ray r : rays) {
            tempColors.add(calcColorGLobalEffect(r, level, k, kX));
        }

        // Stop at maximum recursion level
        if (level <= 1) {
            // return average of the four colors
            return tempColors.get(0).add(tempColors.get(1), tempColors.get(2), tempColors.get(3)).reduce(4);
        }

        //Compare the colors
        Color tempColor = tempColors.get(0);
        boolean different = false;
        for (Color c : tempColors) {
            if (!c.equals(tempColor)){
                different = true;
                break;
            }
        }

        //If the colors are different, divide the square into 4 and calculate the color of each square
        if (different)
            return calcAdaptiveSS(rays.get(0), sideLength/2, kX, level, k).add(
                    calcAdaptiveSS(rays.get(1), sideLength/2, kX, level, k),
                    calcAdaptiveSS(rays.get(2), sideLength/2, kX, level, k),
                    calcAdaptiveSS(rays.get(3), sideLength/2, kX, level, k)).reduce(4);

        //All the same, so return the color
        return tempColor;
    }

    /**
     * Helper function for calcGlobalEffects to perform repetitive calculation for reflection and refraction
     * @param ray intersecting the point
     * @param level of recursion
     * @param k transparency factor
     * @param kx reflection or transparency coefficient
     * @return color of the point
     */
    private Color calcColorGLobalEffect(Ray ray, int level, Double3 k, Double3 kx) {
        Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) return Color.BLACK;
        GeoPoint gp = findClosestIntersection(ray);
        if (gp == null) return scene.background;//.scale(kx);
        level--;
        return isZero(gp.geometry.getNormal(gp.point).dotProduct(ray.getDirection())) ? Color.BLACK : calcColor(gp, ray, level, kkx).scale(kx);
    }

    /**
     * Calculate the color of the pixel
     * @param intersection the point that we wish to get its color
     * @param ray the ray that intersects the point
     * @param k the transparency factor
     * @return the color of the point
     */
    private Color calcColor(GeoPoint intersection, Ray ray, int level, Double3 k){
        Color color = calcLocalEffects(intersection, ray, k);
        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray, level, k));
    }

    /**
     * Calculate the color of the pixel
     * @param intersection the point that we wish to get its color
     * @param ray the ray that intersects the point
     * @return the color of the point
     */
    private Color calcColor(GeoPoint intersection, Ray ray){
        return scene.ambientLight.getIntensity().add(calcColor(intersection, ray, MAX_CALC_COLOR_LEVEL, new Double3(INITIAL_K)));
    }

    /**
     * Setter for the adaptive super sampling
     * @param SS the new value of the adaptive super sampling
     * @return the ray tracer
     */
    public RayTracerBasic setSS(boolean SS) {
        this.SS = SS;
        return this;
    }

    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? scene.background : calcColor(closestPoint, ray);
    }

    /**
     * Function to find the closest intersection point of the ray with the scene
     * @param ray the ray that we want to find its closest intersection point
     * @return the closest intersection point of the ray with the scene
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null) return null;
        return ray.findClosestGeoPoint(intersections);
    }

    /**
     * Setter for number of rays of super sampling
     * @param numRays number of rays
     * @return this ray tracer
     */
    public RayTracerBasic setNumRays(int numRays) {
        this.numRays = numRays;
        return this;
    }
}
