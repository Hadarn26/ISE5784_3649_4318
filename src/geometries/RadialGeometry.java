package geometries;


/**
 * Represents a radial geometry, a geometric shape characterized by a radius.
 * This abstract class implements the Geometry interface and provides a base for specific radial geometries.
 * @author Hadar Nagar & Elinoy Damari
 */
public abstract class RadialGeometry extends Geometry{

    /** The radius of the radial geometry.*/
    protected final double radius;

    /**
     * Constructs a radial geometry with the specified radius.
     * @param radius The radius of the radial geometry.
     */
    public RadialGeometry(double radius) {
        this.radius = radius;
    }

}
