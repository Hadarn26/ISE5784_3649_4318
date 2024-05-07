package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Represents a plane in three-dimensional space.
 * This class implements the Geometry interface to provide methods for interacting with planes.
 *
 * @author Hadar&Elinoy
 * @version [Version number]
 */
public class Plane implements Geometry {

    /**
     * A point on the plane.
     */
    private final Point q;

    /**
     * The normal vector to the plane.
     */
    private final Vector normal;

    /**
     * Constructs a plane from three points on the plane.
     * The normal vector is computed from the cross product of two vectors formed by the given points.
     *
     * @param p1 The first point on the plane.
     * @param p2 The second point on the plane.
     * @param p3 The third point on the plane.
     */
    public Plane(Point p1, Point p2, Point p3){
        normal=null;
        q=p1;
    }

    /**
     * Constructs a plane from a point on the plane and its normal vector.
     *
     * @param q The point on the plane.
     * @param normal The normal vector to the plane.
     */
    public Plane(Point q, Vector normal) {
        this.q = q;
        this.normal = normal.normalize();
    }

    /**
     * Retrieves the normal vector at a given point on the surface of the plane.
     * This method is overridden from the Geometry interface.
     *
     * @param point The point on the surface of the plane.
     * @return The normal vector at the specified point.
     */
    @Override
    public Vector getNormal(Point point) {
        return null;
    }

    /**
     * Retrieves the normal vector to the plane.
     *
     * @return The normal vector to the plane.
     */
    public Vector getNormal() {
        return normal;
    }
}
