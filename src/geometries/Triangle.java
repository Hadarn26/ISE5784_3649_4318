package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;
import static primitives.Util.*;
import java.util.List;

/**
 * Represents a triangle in three-dimensional space.
 * This class extends Polygon, inheriting its properties and methods.
 * @author Hadar Nagar & Elinoy Damari
 */
public class Triangle extends Polygon{

    /**
     * Constructs a triangle with the specified vertices.
     * @param vertices The vertices of the triangle.
     */
    public Triangle(Point... vertices) {
        super(vertices);
    }

//    @Override
//    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
//        List<GeoPoint> intersections=plane.findGeoIntersections(ray,maxDistance);
//        if(intersections==null)
//            return null;
//        Vector v1= vertices.get(0).subtract(ray.getHead());
//        Vector v2= vertices.get(1).subtract(ray.getHead());
//        Vector v3= vertices.get(2).subtract(ray.getHead());
//        Vector n1=(v1.crossProduct(v2)).normalize();
//        Vector n2=(v2.crossProduct(v3)).normalize();
//        Vector n3=(v3.crossProduct(v1)).normalize();
//        double vn1=ray.getDirection().dotProduct(n1);
//        double vn2=ray.getDirection().dotProduct(n2);
//        double vn3=ray.getDirection().dotProduct(n3);
//
//        if(isZero(vn1)||isZero(vn2)||isZero(vn3))
//            return null;
//        if((vn1>0&&vn2>0&&vn3>0)||(vn1<0&&vn2<0&&vn3<0))
//            return intersections;
//        return null;
//    }
@Override
public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
    List<GeoPoint> intersections = plane.findGeoIntersectionsHelper(ray, maxDistance);
    if (intersections == null)
        return null;
    intersections = List.of(new GeoPoint(this, intersections.get(0).point));
    Point rayP0 = ray.getHead();
    Vector rayVec = ray.getDirection();
    Vector v1 = vertices.get(0).subtract(rayP0);
    Vector v2 = vertices.get(1).subtract(rayP0);
    double t1 = Util.alignZero(rayVec.dotProduct(v1.crossProduct(v2)));
    if (t1 == 0)
        return null;

    Vector v3 = vertices.get(2).subtract(rayP0);
    double t2 = Util.alignZero(rayVec.dotProduct(v2.crossProduct(v3)));
    if (t1 * t2 <= 0)
        return null;

    double t3 = Util.alignZero(rayVec.dotProduct(v3.crossProduct(v1)));
    return t1 * t3 <= 0 ? null : intersections;
}
}
