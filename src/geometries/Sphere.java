package geometries;

import primitives.Point;

public class Sphere extends RadialGeometry{
    private final Point center;

    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }
}
