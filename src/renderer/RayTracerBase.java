//////////////////////////////////////////

package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * The RayTracerBase class is an abstract class that defines the basic structure for a ray tracer.
 * It contains a reference to a scene and defines an abstract method for tracing rays.
 */
public abstract class RayTracerBase {

    /** The scene to be rendered by the ray tracer. */
    protected Scene scene;

    /**
     * Constructs a RayTracerBase with the specified scene.
     *
     * @param scene the scene to be rendered.
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * Traces a ray and returns the color of the intersected object.
     * This method needs to be implemented by subclasses.
     *
     * @param ray the ray to be traced.
     * @return the color resulting from tracing the ray.
     */
    public abstract Color traceRay(Ray ray);
}
