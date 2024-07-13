package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;
import static primitives.Util.*;
import java.util.List;


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


    @Override
    public Vector getNormal(Point point) {
        return (point.subtract(center)).normalize();
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        Point rayHead=ray.getHead();
        if (center.equals(rayHead))
            return List.of(new GeoPoint(this, ray.getPoint(radius)));

        // if the ray starts at the center of the sphere
        double tm = 0;
        double d = 0;
        double dSquared;
        Vector rayDirection=ray.getDirection();
        Vector l = center.subtract(rayHead);
        tm = l.dotProduct(rayDirection);
        d =Math.sqrt(Math.abs(l.lengthSquared() - tm * tm)); // d = (|L|^2 - tm^2)^0.5
        if (alignZero(d-radius)>=0) // d > radius->if the ray doesn't intersect the sphere
            return null;

        // computing the distance from the ray's start point to the intersection points
        double th = Math.sqrt(radius * radius - d * d);
        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);
        if (t2 <= 0 || Util.alignZero(t1 - maxDistance) >= 0)
            return null;
        if (Util.alignZero(t2 - maxDistance) >= 0)
            return t1 > 0 ? List.of(new GeoPoint(this, ray.getPoint(t1))) : null;
        return t1 > 0
                ? List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2)))
                : List.of(new GeoPoint(this, ray.getPoint(t2)));

    }

}
