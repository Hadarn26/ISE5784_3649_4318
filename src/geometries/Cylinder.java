package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

/**
 * Represents a cylinder, a three-dimensional geometric shape with a circular base and a certain height.
 * This class extends Tube, inheriting its properties and methods.
 *
 * @author  Hadar Nagar & Elinoy Damari
 */
public class Cylinder extends Tube{

    /** The height of the cylinder. */
    final double height;

    /**
     * Constructs a new Cylinder object with the specified height, base ray, and radius.
     * @param height The height of the cylinder.
     * @param ray The base ray of the cylinder.
     * @param radius The radius of the cylinder.
     */
    public Cylinder(double height, Ray ray , double radius) {
        super(radius, ray);
        this.height=height;
    }

    @Override
    public Vector getNormal(Point point) {
        //if על הבסיס
        if(point.equals(axis.getHead()))
            return (axis.getDirection()).scale(-1);
        double t=(point.subtract(axis.getHead())).dotProduct(axis.getDirection());
        if (Util.isZero(t))
            return (axis.getDirection()).scale(-1);
        else if(t==height)
            return axis.getDirection();
        else
            return super.getNormal(point);
    }
}
