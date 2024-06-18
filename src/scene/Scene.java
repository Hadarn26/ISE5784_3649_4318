/////////////////////////////////////////////

package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

/**
 * The Scene class represents a scene in a 3D space, containing geometries,
 * ambient light, and a background color.
 */
public class Scene {

    /** The name of the scene. */
    public String name;
    /** The background color of the scene, default is black. */
    public Color backGround=Color.BLACK;
    /** The ambient light of the scene, default is none. */
    public AmbientLight ambientLight=AmbientLight.NONE;
    /** The geometries contained in the scene. */
    public Geometries geometries=new Geometries();
    public List<LightSource> lights=new LinkedList<>();

    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }

    /**
     * Constructs a Scene with the specified name.
     *
     * @param name the name of the scene.
     */
    public Scene(String name) {
        this.name = name;
    }

    /**
     * Sets the geometries for the scene.
     *
     * @param geometries the geometries to be set.
     * @return the scene instance.
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    /**
     * Sets the ambient light for the scene.
     *
     * @param ambientLight the ambient light to be set.
     * @return the scene instance.
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Sets the background color for the scene.
     *
     * @param backGround the background color to be set.
     * @return the scene instance.
     */
    public Scene setBackGround(Color backGround) {
        this.backGround = backGround;
        return this;
    }
}
