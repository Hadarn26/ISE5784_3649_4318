package lighting;

import primitives.Color;

/**
 * The Light class represents a general light source with a specific intensity.
 * This class serves as a base class for different types of light sources.
 * @author Hadar Nagar & Elinoy Damari
 */
public class Light {

    /**
     * The intensity (color) of the light.
     */
    protected Color intensity;

    /**
     * Constructs a new Light with the specified intensity.
     *
     * @param intensity the intensity (color) of the light
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * Returns the intensity of the light.
     *
     * @return the intensity of the light
     */
    public Color getIntensity() {
        return intensity;
    }
}
