package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.List;

/**
 * Represents a tube in three-dimensional space.
 * This class extends RadialGeometry, inheriting its properties and methods.
 * @author Hadar Nagar & Elinoy Damari
 */
public class Tube extends  RadialGeometry{

    /** The axis of the tube.*/
    protected final Ray axis;

    /**
     * Constructs a tube with the specified radius and axis.
     * @param radius The radius of the tube.
     * @param axis The axis of the tube.
     */
    public Tube(double radius, Ray axis) {
        super(radius);
        this.axis = axis;
    }

    @Override
    public Vector getNormal(Point point) {
        Point  o;
        double t=(point.subtract(axis.getHead())).dotProduct(axis.getDirection());
        if (Util.isZero(t))
             o=axis.getHead();
        else
             o=axis.getPoint(t);
        return (point.subtract(o)).normalize();
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        return null;
    }
}
