package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The DirectionalLight class represents a light source with a fixed direction
 * and intensity. This light source illuminates objects uniformly from a specific
 * direction, similar to sunlight.
 * @author Hadar Nagar & Elinoy Damari
 */
public class DirectionalLight extends Light implements LightSource{
    private Vector direction;

    /**
     * Constructs a new DirectionalLight with the specified intensity and direction.
     * The direction vector is normalized upon initialization.
     *
     * @param intensity the intensity (color) of the light
     * @param direction the direction vector of the light
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();
    }

    /**
     * Returns the intensity of the light at a given point.
     * For a directional light, the intensity is constant and does not depend
     * on the point.
     *
     * @param p the point at which the intensity is calculated
     * @return the intensity of the light
     */
    @Override
    public Color getIntensity(Point p) {
        return super.getIntensity();
    }

    /**
     * Returns the direction of the light relative to a given point.
     * For a directional light, the direction is the same regardless of the point.
     *
     * @param p the point at which the direction is calculated
     * @return the direction vector of the light
     */
    @Override
    public Vector getL(Point p) {
        return direction;
    }

    @Override
    public double getDistance(Point p) {
        return Double.POSITIVE_INFINITY;
    }
}
