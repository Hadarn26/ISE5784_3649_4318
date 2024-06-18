package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import static java.lang.Math.max;

/**
 * The SpotLight class represents a spotlight, which emits a cone of light in a specific direction
 * from a single point in space. The intensity of the light decreases both with distance and with
 * the angle from the spotlight's direction.
 * @author Hadar Nagar & Elinoy Damari
 */
public class SpotLight extends PointLight{

    /**
     * The direction in which the spotlight is pointing.
     */
    private Vector direction;

    /**
     * Constructs a new SpotLight with the specified intensity, position, and direction.
     * The direction vector is normalized upon initialization.
     *
     * @param intensity the intensity (color) of the light
     * @param position  the position of the light in 3D space
     * @param direction the direction in which the spotlight is pointing
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }
    /**
     * Sets the constant attenuation factor.
     *
     * @param kC the constant attenuation factor
     * @return the current instance of SpotLight (for method chaining)
     */
    @Override
    public PointLight setkC(double kC) {
        return (SpotLight)super.setkC(kC);
    }

    /**
     * Sets the linear attenuation factor.
     *
     * @param kL the linear attenuation factor
     * @return the current instance of SpotLight (for method chaining)
     */
    @Override
    public PointLight setkL(double kL) {
        return (SpotLight)super.setkL(kL);
    }


    /**
     * Sets the quadratic attenuation factor.
     *
     * @param kQ the quadratic attenuation factor
     * @return the current instance of SpotLight (for method chaining)
     */
    @Override
    public PointLight setkQ(double kQ) {
        return (SpotLight)super.setkQ(kQ);
    }

    /**
     * Returns the intensity of the light at a given point. The intensity decreases
     * with the distance from the light source and the angle between the light direction
     * and the direction to the point.
     *
     * @param p the point at which the intensity is calculated
     * @return the intensity of the light at the specified point
     */
    @Override
    public Color getIntensity(Point p) {
        return super.getIntensity(p).scale(max(0,direction.dotProduct(super.getL(p))));
    }

    /**
     * Returns the direction of the light relative to a given point. The direction
     * is a vector from the light source to the point.
     *
     * @param p the point at which the direction is calculated
     * @return the direction vector of the light relative to the specified point
     */
    @Override
    public Vector getL(Point p) {
        return super.getL(p);
    }

}
