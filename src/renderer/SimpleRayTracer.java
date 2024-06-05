/////////////////////////////////////////////



package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;
import java.util.List;

/**
 * The SimpleRayTracer class extends RayTracerBase and provides a basic implementation
 * for tracing rays in a scene. It calculates the color of the closest intersection point
 * or returns the background color if no intersections are found.
 */
public class SimpleRayTracer extends  RayTracerBase {

    /**
     * Constructs a SimpleRayTracer with the specified scene.
     *
     * @param scene the scene to be rendered.
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {

        List<Point> intersections=scene.geometries.findIntersections(ray);
        if(intersections==null)
            return scene.backGround;
        return calcColor(ray.findClosestPoint(intersections)) ;
    }

    /**
     * Calculates the color at a given point.
     * In this simple implementation, it returns the ambient light intensity of the scene.
     *
     * @param p the point at which to calculate the color.
     * @return the color at the given point.
     */
    private Color calcColor(Point p)
    {
        return scene.ambientLight.getIntensity();
    }
}
