package geometries;

import primitives.Point;
import primitives.Util;
import primitives.Vector;


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
       if(Util.isZero(new Vector(p1.xyz)))
        normal=null;
        q=p1;

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
        return null;
    }

    /**
     * Retrieves the normal vector to the plane.
     * @return The normal vector to the plane.
     */
    public Vector getNormal() {
        return normal;
    }
}
