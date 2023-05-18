package renderer;

import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;
import geometries.Intersectable.GeoPoint;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Class RayTracerBasic which inherits from RayTracerBase
 * @author Eli Hawk and Binyamin Klein
 * 563385586 & 576708589
 */
public class RayTracerBasic extends RayTracerBase {
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final double INITIAL_K = 1.0;


    /**
     * Constructor for RayTracerBase
     * @param scene the scene
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * Checks if a point is unshaded
     * @param gp geoPoint to check if it is shaded
     * @param light the light source we are checking it with
     * @param l vector going from the light source to the point
     * @param n the normal vector
     * @return true if the point is unshaded, false otherwise
     */
    private boolean unshaded(GeoPoint gp, LightSource light, Vector l, Vector n) {

        Vector lightDirection = l.scale(-1); // from point to light source

        Ray lightRay = new Ray(gp.point, lightDirection, n);

        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null) return true;

        //if there are points in the intersections list that are closer to the point than light source (and kT==0) – return false
        //otherwise – return true
        double lightDistance = light.getDistance(gp.point);
        for (GeoPoint p : intersections) {
            if (alignZero(p.point.distance(gp.point) - lightDistance) <= 0 && p.geometry.getMaterial().kT.equals(Double3.ZERO))
                return false;
        }

        return true;
    }


    private Double3 transparency(GeoPoint gp, LightSource light, Vector l, Vector n){
        Vector lightDirection = l.scale(-1); // from point to light source

        Ray lightRay = new Ray(gp.point, lightDirection, n);

        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null) return Double3.ONE;
        Double3 ktr = Double3.ONE;
        //if there are points in the intersections list that are closer to the point than light source (and kT==0) – return false
        //otherwise – return true
        double lightDistance = light.getDistance(gp.point);
        for (GeoPoint p : intersections) {
            if (alignZero(p.point.distance(gp.point) - lightDistance) <= 0){
                ktr = ktr.product(p.geometry.getMaterial().kT);
                if (ktr.lowerThan(MIN_CALC_COLOR_K)){
                        return Double3.ZERO;
                }
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
        /*//Refraction indexes
        double n1 = 1;
        double n2 = 1;
        //Cosine of the angle between the normal and the vector from the camera to the point
        double cosThetaI = v.dotProduct(n) / (v.length()*n.length());

        // Check for total internal reflection
        if (cosThetaI > 1 / n2)
            return null;

        double sinThetaI = Math.sqrt(1 - cosThetaI * cosThetaI);
        double sinThetaR = (n1/n2) * sinThetaI;
        double cosThetaR = Math.sqrt(1 - sinThetaR * sinThetaR);
        //𝒓 = (𝐜𝐨𝐬𝜽i −𝒄𝒐𝒔𝜽r)∙𝒏 − 𝒗
        if (isZero(cosThetaI - cosThetaR)){
            return new Ray(gp.point, v, n);
        }
        //Vector r = n.scale(cosThetaI-cosThetaR).scale(n1/n2).subtract(v.scale(n1/n2));
        Vector r = v.scale(n1/n2).subtract(n.scale(cosThetaR - cosThetaI));
        //𝒓𝒂𝒚=𝒑𝒐𝒊𝒏𝒕+𝒌∙𝒓
        return new Ray(gp.point, r, n);
        //return new Ray(gp.point, v, n);*/
        return new Ray(gp.point, v, n);
        //TODO:Fix issue with refracted ray
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


    private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
        Color color = gp.geometry.getEmission(); //iE
        Vector v = ray.getDirection();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = alignZero(n.dotProduct(v));
        if (isZero(nv)) return color;
        Material material = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sign(nv)
                Double3 ktr = transparency(gp, lightSource, l, n);
                if (ktr.product(k).greaterThan(MIN_CALC_COLOR_K)) {
                    Color iL = lightSource.getIntensity(gp.point).scale(ktr);
                    color = color.add(iL.scale(calcDiffusive(material, nl)), iL.scale(calcSpecular(material, n, l, nl, v)));
                }
            }
        }
        return color;
    }

    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = Color.BLACK;
        Vector v = ray.getDirection();
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        return calcColorGLobalEffect(findReflectedRay(gp, v, n), level, k, material.kR).add(calcColorGLobalEffect(findRefractedRay(gp, v, n), level, k, material.kT));
    }

    private Color calcColorGLobalEffect(Ray ray, int level, Double3 k, Double3 kx) {
        Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) return Color.BLACK;
        GeoPoint gp = findClosestIntersection(ray);
        if (gp == null) return scene.background.scale(kx);
        level--;
        return isZero(gp.geometry.getNormal(gp.point).dotProduct(ray.getDirection())) ? Color.BLACK : calcColor(gp, ray, level, kkx);
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
}
