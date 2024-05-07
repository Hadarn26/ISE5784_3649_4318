package geometries;
import primitives.Ray;

public class Tube extends  RadialGeometry{
    protected final Ray axis;

    public Tube(double radius, Ray axis) {
        super(radius);
        this.axis = axis;
    }
}
