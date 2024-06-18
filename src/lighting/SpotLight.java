package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Util;
import primitives.Vector;

import static java.lang.Math.max;

public class SpotLight extends PointLight{
    private Vector direction;
    private int narrowness = 1;

    @Override
    public PointLight setkC(double kC) {
        return (SpotLight)super.setkC(kC);
    }

    @Override
    public PointLight setkL(double kL) {
        return (SpotLight)super.setkL(kL);
    }

    @Override
    public PointLight setkQ(double kQ) {
        return (SpotLight)super.setkQ(kQ);
    }

    @Override
    public Color getIntensity(Point p) {
        double dotProduct = Util.alignZero(direction.dotProduct(getL(p)));
        return super.getIntensity().scale(dotProduct > 0 ? Math.pow(dotProduct, narrowness) : 0);
    }

    @Override
    public Vector getL(Point p) {
        return super.getL(p);
    }

    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    public SpotLight setNarrowBeam(int narrowness) {
        this.narrowness = narrowness;
        return this;
    }
}
