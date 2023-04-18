package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

/**
 * Geometries Class which implements Intersectable
 * @author Eli Hawk and Binyamin Klein
 * 563385586 & 576708589
 */
public class Geometries implements Intersectable{
    private List<Intersectable> geometries;

    /**
     * Default Constructor for Geometries which initializes empty list
     */
    public Geometries() {
        this.geometries = new LinkedList<>();
    }

    /**
     * Constructor for Geometries which initializes list of geometries
     * @param geometries to initialize list
     */
    public Geometries(Intersectable... geometries) {
        this.geometries = new LinkedList<>(List.of(geometries));
    }

    /**
     * Add geometries to the list
     * @param geometries to add
     */
    public void add(Intersectable... geometries) {
        this.geometries.addAll(List.of(geometries));
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> pointList = new LinkedList<>();
        for (Intersectable g: geometries) {
            List<Point> shape = g.findIntersections(ray);
            if (shape != null) {
                pointList.addAll(shape);
            }
        }
        if (pointList.isEmpty()){
            return null;
        }
        return pointList;
    }
}
