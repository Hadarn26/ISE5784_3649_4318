package geometries;

import primitives.Color;
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
public abstract class Geometry extends Intersectable {
    protected Color emission=Color.BLACK;

    public Color getEmission() {
        return emission;
    }

    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * Retrieves the normal vector at a given point on the surface of the geometric shape.
     *
     * @param point The point on the surface of the shape.
     * @return The normal vector at the specified point.
     */
    public abstract Vector getNormal(Point point);
}
