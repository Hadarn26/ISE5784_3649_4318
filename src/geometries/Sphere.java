package geometries;

import primitives.Point;

/**
 * Represents a sphere in three-dimensional space.
 * This class extends RadialGeometry, inheriting its properties and methods.
 * @author Hadar Nagar & Elinoy Damari
 */
public class Sphere extends RadialGeometry{

    /** The center point of the sphere. */
    final Point center;

    /**
     * Constructs a sphere with the specified radius and center point.
     * @param radius The radius of the sphere.
     * @param center The center point of the sphere.
     */
    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }
}
