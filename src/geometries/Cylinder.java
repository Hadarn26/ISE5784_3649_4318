package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.List;

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
        Point p0=axis.getHead();
        Vector direction=axis.getDirection();

        //if על הבסיס
        if(point.equals(p0))
            return (direction.scale(-1));
        double t=(point.subtract(p0)).dotProduct(direction);
        if (Util.isZero(t))
            return (direction).scale(-1);
        if(Util.isZero(t-height))
            return direction;

        ///////////////////////////////////////////////////////////////
        if (Util.isZero(direction.dotProduct(point.subtract(p0))))
            return direction;
        ///////////////////////////////////////////////////////////////

        return super.getNormal(point);
    }
   // @Override
//    public List<Point> findIntsersections(Ray ray) {
//        return null;
//    }
}
