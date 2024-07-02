package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The PointLight class represents a point light source, which emits light from a single point
 * in space. The intensity of the light decreases with distance from the light source.
 *
 * @author Hadar Nagar & Elinoy Damari
 */
public class PointLight extends Light implements LightSource {

    /**
     * The position of the point light in 3D space.
     */
    private Point position;
    /**
     * The constant attenuation factor.
     */
    private double kC = 1;
    /**
     * The linear attenuation factor.
     */
    private double kL = 0;
    /**
     * The quadratic attenuation factor.
     */
    private double kQ = 0;

    /**
     * Constructs a new PointLight with the specified intensity and position.
     *
     * @param intensity the intensity (color) of the light
     * @param position  the position of the light in 3D space
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    /**
     * Sets the constant attenuation factor.
     *
     * @param kC the constant attenuation factor
     * @return the current instance of PointLight (for method chaining)
     */
    public PointLight setkC(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * Sets the linear attenuation factor.
     *
     * @param kL the linear attenuation factor
     * @return the current instance of PointLight (for method chaining)
     */
    public PointLight setkL(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * Sets the quadratic attenuation factor.
     *
     * @param kQ the quadratic attenuation factor
     * @return the current instance of PointLight (for method chaining)
     */
    public PointLight setkQ(double kQ) {
        this.kQ = kQ;
        return this;
    }

    /**
     * Returns the intensity of the light at a given point. The intensity decreases
     * with the distance from the light source, according to the attenuation factors.
     *
     * @param p the point at which the intensity is calculated
     * @return the intensity of the light at the specified point
     */
    @Override
    public Color getIntensity(Point p) {
        double d = position.distance(p);
        return super.getIntensity().scale(1d / (kC + kL * d + kQ * d * d));
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
        return p.subtract(position).normalize();
    }

    @Override
    public double getDistance(Point p) {
        return position.distance(p);
    }

}
