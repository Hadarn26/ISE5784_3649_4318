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
    public List<Point> findIntersections(Ray ray) { //(-1,0,0) (3,1,0)
        // if the ray starts at the center of the sphere
        double tm = 0;
        double d = 0;
        if (!center.equals(ray.getHead())){ // if the ray doesn't start at the center of the sphere
            Vector L = center.subtract(ray.getHead());
            tm = L.dotProduct(ray.getDirection());
            d =L.lengthSquared() - tm * tm; // d = (|L|^2 - tm^2)
            if (d < 0)
                d = -d;
            d = Math.sqrt(d);
        }
        if (d > radius) // if the ray doesn't intersect the sphere
            return null;
        // computing the distance from the ray's start point to the intersection points
        double th = Math.sqrt(radius * radius - d * d);
        double t1 = tm - th;
        double t2 = tm + th;
        if (t1 <= 0 && t2 <= 0)
            return null;
        if (Util.alignZero(t2) == 0) // if the ray is tangent to the sphere
            return null;
        if (th == 0)
            return null;
        if (t1 <= 0){ // if the ray starts inside the sphere or the ray starts after the sphere
            return List.of(ray.getPoint(t2));
        }
        if (t2 <= 0) { //if the ray starts after the sphere
            return List.of(ray.getPoint(t1));
        }
        return List.of(ray.getPoint(t1), ray.getPoint(t2)); // if the ray intersects the sphere twice
//        Point p0 = ray.getHead();
//        Vector dir = ray.getDirection();
//
//        // Deals with case where ray starts from the center of the sphere
//        if (p0.equals(center))
//            return List.of(ray.getPoint(this.radius));
//
//        // Finding the hypotenuse, base and perpendicular of the triangle formed by
//        // ray's starting point, the center of the sphere and the intersection point of
//        // the ray and the perpendicular line crosing the sphere's center.
//        Vector hypotenuse = this.center.subtract(p0);
//        double base = dir.dotProduct(hypotenuse);
//        double perpendicular = Math.sqrt(hypotenuse.dotProduct(hypotenuse) - base*base);
//
//        // Dealing with a case in which the ray is perpendicular to the sphere at the
//        // intersection point.
//        if (perpendicular == this.radius)
//            return null;
//
//        // Returning intersection points, ensuring that only those intersected by the
//        // ray are returned.
//        double inside = Math.sqrt(Math.pow(this.radius, 2) - Math.pow(perpendicular, 2));
//        if (alignZero(base - inside) > 0 && alignZero(base + inside)>0)
//            return List.of(ray.getPoint(base - inside), ray.getPoint(base + inside));
//        else if (base - inside > 0)
//            return List.of(ray.getPoint(base - inside));
//        else if (base + inside > 0)
//            return List.of(ray.getPoint(base + inside));
//       return null;
//        if(ray.getHead().equals(center))
//            return List.of(ray.getPoint(radius));
//
//        Vector u=center.subtract(ray.getHead()); //(1,0,0)-(-1,0,0)=(2,0,0)
//        double tm=(ray.getDirection()).dotProduct(u); //(3,1,0)*(2,0,0)=6
//        double dSquared=u.lengthSquared()-tm*tm;//4-36=-32
//        double th=radius*radius-dSquared*dSquared;//33
//                // 4-10
//        if(alignZero(th)<=0)
//            return null;
//        //d<radius
//
//
//
//        th=Math.sqrt(th); // 5.744562647
//        double t1,t2;
//        t1=alignZero(tm+th);
//        t2=alignZero(tm-th);
//        if (t1>0 && t2>0)
//            return List.of(ray.getPoint(t1),ray.getPoint(t2));
//        if(((isZero(t1) || t1<0)) && t2>0)
//            return List.of(ray.getPoint(t2));
//        return List.of(ray.getPoint(t1));
    }
}
