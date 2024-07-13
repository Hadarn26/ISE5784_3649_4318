package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Util;
import primitives.Vector;

public class SpotLight extends PointLight {
    private Vector direction;
    private int narrowness = 1;
    /**
     * construct SpotLight using color, position, and direction vector
     *
     * @param intensity the light color
     * @param position  the light position
     * @param direction the direction vector
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    @Override
    public SpotLight setkC(double kC) {
        super.setkC(kC);
        return this;
    }

    @Override
    public SpotLight setkL(double kL) {
        super.setkL(kL);
        return this;
    }

    /**
     * Sets the quadratic attenuation factor.
     * @param kQ the quadratic attenuation factor
     * @return the current SpotLight instance (for chaining)
     */
    @Override
    public SpotLight setkQ(double kQ) {
        super.setkQ(kQ);
        return this;
    }



    @Override
    public Color getIntensity(Point p) {
        Vector l = super.getL(p);
        if (l == null)
            return super.getIntensity();
        double directionDotL = Util.alignZero(direction.dotProduct(l));
        return super.getIntensity(p).scale(directionDotL > 0 ? Math.pow(directionDotL, narrowness) :0); // the denominator from the super!!
//        double dotProduct = Util.alignZero(direction.dotProduct(getL(p)));
//        return super.getIntensity().scale(dotProduct > 0 ? Math.pow(dotProduct, narrowness) : 0);
    }
    @Override
    public Vector getL(Point p) {
        return super.getL(p);
    }


    /**
     * narrows the beam of the ray
     *
     * @param narrowness the narrowness of the beam
     * @return this object
     */
    public SpotLight setNarrowBeam(int narrowness) {
        this.narrowness = narrowness;
        return this;
    }
}
