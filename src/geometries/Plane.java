package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.List;

import static primitives.Util.*;


/**
 * The Plane class represents a plane in three-dimensional space.
 * It extends the {@code Geometry} class and provides methods to construct a plane
 * and calculate intersections with rays.
 * author Hadar Nagar & Elinoy Damari
 */
public class Plane extends Geometry {

    /**
     * The base point of the plane.
     */
    final Point q;

    /**
     * The normal vector to the plane.
     */
    final Vector normal;

    /**
     * Constructs a plane from three points on the plane.
     * The normal vector is computed from the cross product of two vectors formed by the given points.
     *
     * @param p1 The first point on the plane.
     * @param p2 The second point on the plane.
     * @param p3 The third point on the plane.
     */
    public Plane(Point p1, Point p2, Point p3) {
        if (p1.equals(p2) || p2.equals(p3) || p1.equals(p3))
            throw new IllegalArgumentException("can't create plane with less than 3 different points");

        Vector v1 = p2.subtract(p1);
        Vector v2 = p3.subtract(p1);
        q = p1;

        try {
            normal = (v1.crossProduct(v2)).normalize();
        } catch (IllegalArgumentException zeroVectorIgnore) {
            throw new IllegalArgumentException("can't create plane with 3 points on the same line");
        }

    }


    /**
     * Constructs a plane from a point on the plane and its normal vector.
     *
     * @param q      The point on the plane.
     * @param normal The normal vector to the plane.
     */
    public Plane(Point q, Vector normal) {
        this.q = q;
        this.normal = normal.normalize();
    }

    /**
     * Retrieves the normal vector at a given point on the plane.
     *
     * @param point the point on the plane (not used in the calculation as the normal is constant)
     * @return the normal vector to the plane
     */
    @Override
    public Vector getNormal(Point point) {
        return normal;
    }

    /**
     * Retrieves the normal vector to the plane.
     *
     * @return the normal vector to the plane
     */
    public Vector getNormal() {
        return normal;
    }

    /**
     * Finds the geometric intersections of a ray with the plane.
     *
     * @param ray the ray to intersect with the plane
     * @return a list of geometric intersection points, or null if no intersections are found
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        Point rayHead = ray.getHead();
        if (q.equals(ray.getHead()))
            return null;
        Vector v = ray.getDirection();
        double nv = normal.dotProduct(v);
        if (isZero(nv))
            return null;
        double t = alignZero((normal.dotProduct((q.subtract(ray.getHead())))) / (nv));
        return t <= 0 || Util.alignZero(t - maxDistance) >= 0 ? null : List.of(new GeoPoint(this, ray.getPoint(t)));
    }
}




