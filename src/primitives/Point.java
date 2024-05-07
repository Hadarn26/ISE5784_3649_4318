package primitives;

import static primitives.Util.isZero;

public class Point {
    protected final Double3 xyz;
    public static final Point ZERO=Double3.ZERO;
    public Point (double x, double y, double z){
        xyz = new Double3(x, y, z);;
    }

    public Point(Double3 xyz){
        this.xyz= new Double3(xyz.d1, xyz.d2, xyz.d3);;
    }

    public Vector Substract(Point other){
        if(other==null)
            throw new NullPointerException("the other point is null");
        return other.xyz.subtract(xyz);
    }

    public Point Add(Vector v){
        return xyz.add(v.xyz);
    }

    public double distanceSquared(Point other){
        return (this.xyz.d1-other.xyz.d1)*(this.xyz.d1-other.xyz.d1) + (this.xyz.d2-other.xyz.d2)*(this.xyz.d2-other.xyz.d2) + (this.xyz.d3-other.xyz.d3)*(this.xyz.d3-other.xyz.d3);
    }

    public double distance(Point other){
        return Math.sqrt(distanceSquared(other));
    }

    @Override
    public String toString() {
        return xyz.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Point other)
                &&xyz.equals(other.xyz);
    }
}
