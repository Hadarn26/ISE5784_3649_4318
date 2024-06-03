package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.*;


/**
 * Represents a plane in three-dimensional space.
 * This class implements the Geometry interface to provide methods for interacting with planes.
 *
 * @author Hadar Nagar & Elinoy Damari
 */
public class Plane implements Geometry {

    /** The base point of the plane. */
    final Point q;

    /** The normal vector to the plane.*/
    final Vector normal;

    /**
     * Constructs a plane from three points on the plane.
     * The normal vector is computed from the cross product of two vectors formed by the given points.
     * @param p1 The first point on the plane.
     * @param p2 The second point on the plane.
     * @param p3 The third point on the plane.
     */
    public Plane(Point p1, Point p2, Point p3){
       if(p1.equals(p2)||p2.equals(p3)||p1.equals(p3))
           throw new IllegalArgumentException("can't create plane with less than 3 different points");

       Vector v1=p2.subtract(p1);
       Vector v2=p3.subtract(p1);
       q=p1;

       try {
           normal=(v1.crossProduct(v2)).normalize();
       } catch (IllegalArgumentException zeroVectorIgnore) {
           throw new IllegalArgumentException("can't create plane with 3 points on the same line");
       }

    }



    /**
     * Constructs a plane from a point on the plane and its normal vector.
     * @param q The point on the plane.
     * @param normal The normal vector to the plane.
     */
    public Plane(Point q, Vector normal) {
        this.q = q;
        this.normal = normal.normalize();
    }

    @Override
    public Vector getNormal(Point point) {
        return normal;
    }

    /**
     * Retrieves the normal vector to the plane.
     * @return The normal vector to the plane.
     */
    public Vector getNormal() {
        return normal;
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        Vector v=ray.getDirection();
        double nv=normal.dotProduct(v);
        if (isZero(nv))
            return null;
        if(q.equals(ray.getHead()))
            return null;
        double t=alignZero((normal.dotProduct((q.subtract(ray.getHead()))))/(nv));
        if(t>0)
            return List.of(ray.getPoint(t));
        return null;
    }
}
