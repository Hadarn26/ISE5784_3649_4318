package lighting;
//
//import primitives.Color;
//import primitives.Point;
//import primitives.Util;
//import primitives.Vector;
//
//import static java.lang.Math.max;
//
//public class SpotLight extends PointLight{
//    private Vector direction;
//    private int narrowness = 1;
//
//    @Override
//    public PointLight setkC(double kC) {
//        return (SpotLight)super.setkC(kC);
//    }
//
//    @Override
//    public PointLight setkL(double kL) {
//        return (SpotLight)super.setkL(kL);
//    }
//
//    @Override
//    public PointLight setkQ(double kQ) {
//        return (SpotLight)super.setkQ(kQ);
//    }
//
//    @Override
//    public Color getIntensity(Point p) {
//        double dotProduct = Util.alignZero(direction.dotProduct(getL(p)));
//        return super.getIntensity().scale(dotProduct > 0 ? Math.pow(dotProduct, narrowness) : 0);
//    }
//
//    @Override
//    public Vector getL(Point p) {
//        return super.getL(p);
//    }
//
//    public SpotLight(Color intensity, Point position, Vector direction) {
//        super(intensity, position);
//        this.direction = direction.normalize();
//    }
//
//    public SpotLight setNarrowBeam(int narrowness) {
//        this.narrowness = narrowness;
//        return this;
//    }
//}

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
    public SpotLight setKL(double kL) {
        super.setKL(kL);
        return this;
    }

    /**
     * Sets the quadratic attenuation factor.
     * @param kQ the quadratic attenuation factor
     * @return the current SpotLight instance (for chaining)
     */
    @Override
    public SpotLight setKQ(double kQ) {
        super.setKQ(kQ);
        return this;
    }

    @Override
    public Color getIntensity(Point p) {
        Vector l = super.getL(p);
        if (l == null)
            return super.getIntensity();

        double directionDotL = Util.alignZero(direction.dotProduct(l));
        if (directionDotL <= 0)
            return Color.BLACK;

        return super.getIntensity(p).scale(directionDotL); // the denominator from the super!!
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
