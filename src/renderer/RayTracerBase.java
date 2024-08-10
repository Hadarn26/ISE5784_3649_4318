//////////////////////////////////////////

package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

import java.util.List;

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
    public abstract Color traceRays(List<Ray> rays);
    public abstract Color AdaptiveSuperSamplingRec(Point centerP, double Width, double Height, double minWidth, double minHeight, Point cameraLoc, Vector Vright, Vector Vup, List<Point> prePoints);
    public abstract Color RegularSuperSampling(Point centerP, double Width, double Height, double minWidth, double minHeight, Point cameraLoc, Vector Vright, Vector Vup, List<Point> prePoints);

}
