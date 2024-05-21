package geometries;

import primitives.Point;
import primitives.Ray;
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
    public List<Point> findIntsersections(Ray ray) {
        Vector u=center.subtract(ray.getHead());
        double tm=(ray.getDirection()).dotProduct(u);
        double d=Math.sqrt(u.dotProduct(u)-(ray.getDirection()).dotProduct((ray.getDirection())));
        if(d>=radius)
            return null;
        //d<radius
        double th=Math.sqrt(radius*radius-d*d);
        double t1,t2;
        t1=tm+th;
        t2=tm-th;
        if (t1>0 & t2>0)
            return List.of(ray.getHead().add(ray.getDirection().scale(t1)),ray.getHead().add(ray.getDirection().scale(t2)));
        if((isZero(t1) || t1<0) && t2>0)
            return List.of(ray.getHead().add(ray.getDirection().scale(t2)));
        return List.of(ray.getHead().add(ray.getDirection().scale(t1)));
    }
}
