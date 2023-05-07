package geometries;

/**
 * Class RadialGeometry is an abstract class which implements Geometry and will be a parent of all
 * shapes with a radius such as a sphere and a tube
 * @author Eli Hawk and Binyamin Klein
 * 563385586 & 576708589
 */
public abstract class RadialGeometry extends Geometry{
    protected double radius;

    /**
     * Constructor for radial geometry
     * @param r radius
     */
    RadialGeometry(double r){
        radius = r;
    }
}
