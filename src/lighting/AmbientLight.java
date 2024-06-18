package lighting;

import primitives.*;

/**
 * The AmbientLight class represents ambient lighting in a scene.
 * It is characterized by an intensity and can be scaled by a factor.
 * @author Hadar Nagar & Elinoy Damari
 */
public class AmbientLight extends Light {


    /**
     * A static instance of AmbientLight with no intensity (black color).
     */

    public static final AmbientLight NONE =new AmbientLight(Color.BLACK,0);

    /**
     * Constructs an AmbientLight with the given intensity and scale factor.
     *
     * @param iA the initial intensity color
     * @param kA the scale factor as a Double3 object
     */
    public AmbientLight(Color iA, Double3 kA) {
        super(iA.scale(kA));
    }

    /**
     * Constructs an AmbientLight with the given intensity and scale factor.
     *
     * @param iA the initial intensity color
     * @param kA the scale factor as a double
     */
    public AmbientLight(Color iA, double kA) {
        super(iA.scale(kA));
    }


}
