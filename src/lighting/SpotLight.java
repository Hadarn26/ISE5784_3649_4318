package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import static java.lang.Math.max;

public class SpotLight extends PointLight{
    private Vector direction;

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
        return super.getIntensity(p).scale(max(0,direction.dotProduct(super.getL(p))));
    }

//    @Override
//    public Vector getL(Point p) {
//        return super.getL(p);
//    }

    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction;
    }
}
