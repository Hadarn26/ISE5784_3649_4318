package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * The Geometry interface represents geometric shapes in a three-dimensional space.
 * It provides a method to retrieve the normal vector at a given point on the surface of the shape.
 * Implementing classes must override the {@code getNormal} method to provide specific behavior
 * for calculating the normal vector.
 *
 * @author Hadar Nagar & Elinoy Damari
 */
public interface Geometry extends Intersectable {

    /**
     * Retrieves the normal vector at a given point on the surface of the geometric shape.
     *
     * @param point The point on the surface of the shape.
     * @return The normal vector at the specified point.
     */
    Vector getNormal(Point point);
}
