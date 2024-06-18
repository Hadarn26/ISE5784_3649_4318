package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

/**
 * The Geometry class represents geometric shapes in a three-dimensional space.
 * It provides methods to retrieve the normal vector at a given point on the surface of the shape,
 * as well as to manage the material properties and emission color of the shape.
 * Implementing classes must override the {@code getNormal} method to provide specific behavior
 * for calculating the normal vector.
 * <p>
 * This abstract class extends {@code Intersectable}, allowing geometric shapes to be used
 * in ray tracing and other 3D graphics applications.
 * </p>
 *
 * @author Hadar Nagar & Elinoy Damari
 */
public abstract class Geometry extends Intersectable {

    /**
     * The emission color of the geometric shape.
     */
    protected Color emission=Color.BLACK;
    /**
     * The material properties of the geometric shape.
     */
    private Material material=new Material();

    /**
     * Returns the emission color of the geometric shape.
     *
     * @return the emission color of the shape
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * Returns the material properties of the geometric shape.
     *
     * @return the material properties of the shape
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * Sets the material properties of the geometric shape.
     *
     * @param material the material properties to set
     * @return the current instance of Geometry (for method chaining)
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * Sets the emission color of the geometric shape.
     *
     * @param emission the emission color to set
     * @return the current instance of Geometry (for method chaining)
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * Retrieves the normal vector at a given point on the surface of the geometric shape.
     *
     * @param point The point on the surface of the shape.
     * @return The normal vector at the specified point.
     */
    public abstract Vector getNormal(Point point);
}
