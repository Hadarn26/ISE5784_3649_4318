package geometries;
import primitives.*;
import java.util.List;

/**
 * Interface representing an intersectable geometrical shape.
 * Classes implementing this interface can be intersected by a ray,
 * and the intersections can be found and returned as a list of points.
 *
 * @author  Hadar Nagar & Elinoy Damari
 */
public interface Intersectable {

    /**
     * Finds intersections of a ray with the shape.
     *
     * @param ray The ray to intersect with the shape.
     * @return A list of intersection points, or null if no intersections are found.
     */
    List<Point> findIntsersections(Ray ray);

}
