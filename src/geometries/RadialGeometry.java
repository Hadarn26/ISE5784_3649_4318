package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Represents a radial geometry, a geometric shape characterized by a radius.
 * This abstract class implements the Geometry interface and provides a base for specific radial geometries.
 *
 * @author Hadar&Elinoy
 * @version [Version number]
 */
public abstract class RadialGeometry implements Geometry{

    /**
     * The radius of the radial geometry.
     */
    protected final double radius;

    /**
     * Constructs a radial geometry with the specified radius.
     *
     * @param radius The radius of the radial geometry.
     */
    public RadialGeometry(double radius) {
        this.radius = radius;
    }

    /**
     * Retrieves the normal vector at a given point on the surface of the radial geometry.
     * This method is overridden from the Geometry interface.
     *
     * @param point The point on the surface of the geometry.
     * @return The normal vector at the specified point.
     */
    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
