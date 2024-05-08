package geometries;

import primitives.Ray;

/**
 * Represents a cylinder, a three-dimensional geometric shape with a circular base and a certain height.
 * This class extends Tube, inheriting its properties and methods.
 *
 * @author  Hadar Nagar & Elinoy Damari
 */
public class Cylinder extends Tube{

    private final double height;

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
}
