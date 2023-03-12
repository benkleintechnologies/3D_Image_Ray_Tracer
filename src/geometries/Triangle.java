package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Class Triangle is the class representing a triangle (as a type of Polygon) in Cartesian 3-Dimensional coordinate system.
 * @author Eli Hawk and Binyamin Klein
 * 563385586 & 576708589
 */
public class Triangle extends Polygon{

    /**
     * Constructor for Triangle based on the coordinates of the three corners
     * @param p1 first corner
     * @param p2 second corner
     * @param p3 third corner
     */
    public Triangle(Point p1, Point p2, Point p3){
        super(p1,p2,p3);
    }

    //Getters from Polygon???

    @Override
    public String toString() {
        return "Triangle: {" + vertices.get(0) + "," + vertices.get(1) + "," + vertices.get(2) + "}";
    }

    //getNormal is inherited from Polygon
}
