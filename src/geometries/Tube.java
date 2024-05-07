package geometries;
import primitives.Ray;

/**
 * Represents a tube in three-dimensional space.
 * This class extends RadialGeometry, inheriting its properties and methods.
 *
 * @author Hadar&Elinoy
 * @version [Version number]
 */
public class Tube extends  RadialGeometry{

    /**
     * The axis of the tube.
     */
    protected final Ray axis;

    /**
     * Constructs a tube with the specified radius and axis.
     *
     * @param radius The radius of the tube.
     * @param axis The axis of the tube.
     */
    public Tube(double radius, Ray axis) {
        super(radius);
        this.axis = axis;
    }
}
