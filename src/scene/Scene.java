package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import primitives.Color;

public class Scene {

    public String name;
    public Color backGround=Color.BLACK;
    public AmbientLight ambientLight=AmbientLight.NONE;
    public Geometries geometries=new Geometries();

    public Scene(String name) {
        this.name = name;
    }

    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    public Scene setBackGround(Color backGround) {
        this.backGround = backGround;
        return this;
    }
}
