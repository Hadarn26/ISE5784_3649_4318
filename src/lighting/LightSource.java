package lighting;

import primitives.Color;
import primitives.*;

/**
 * The LightSource interface defines the methods that any light source must implement.
 * This includes methods to get the light's intensity at a specific point and to get the
 * direction of the light relative to a specific point.
 * @author Hadar Nagar & Elinoy Damari
 */
public interface LightSource {

    /**
     * Returns the intensity of the light at a given point.
     *
     * @param p the point at which the intensity is calculated
     * @return the intensity of the light at the specified point
     */
    public Color getIntensity(Point p);

    /**
     * Returns the direction of the light relative to a given point.
     *
     * @param p the point at which the direction is calculated
     * @return the direction vector of the light relative to the specified point
     */
    public Vector getL(Point p);
}
