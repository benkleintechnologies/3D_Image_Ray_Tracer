package scene;

import lighting.AmbientLight;
import geometries.Geometries;
import primitives.Color;
import lighting.LightSource;

import java.util.LinkedList;
import java.util.List;

/**
 * Class Scene represents the scene in a 3D space.
 * @author Eli Hawk and Binyamin Klein
 * 563385586 & 576708589
 */
public class Scene {
    public String name;
    public Color background = Color.BLACK;
    public AmbientLight ambientLight = new AmbientLight();
    public Geometries geometries;
    public List<LightSource> lights = new LinkedList<LightSource>();

    /**
     * Constructor that receives the scene's name and initializes an empty collection of geometries
     * @param name the name of the scene
     */
    public Scene(String name){
        this.name = name;
        geometries = new Geometries();
    }

    /**
     * Setter for background color
     * @param color background color
     * @return this scene object
     */
    public Scene setBackground(Color color){
        background = color;
        return this;
    }

    /**
     * Setter for ambient light
     * @param light ambient light
     * @return this scene object
     */
    public Scene setAmbientLight(AmbientLight light){
        this.ambientLight = light;
        return this;
    }

    /**
     * Setter for geometries in the scene
     * @param geometries in the scene
     * @return this scene object
     */
    public Scene setGeometries(Geometries geometries){
        this.geometries = geometries;
        return this;
    }

    /**
     * Set the light sources for the scene
     * @param lights the list of light sources to add to the scene
     * @return the Scene object
     */
    public Scene setLights(List<LightSource> lights){
        this.lights = lights;
        return this;
    }
}
