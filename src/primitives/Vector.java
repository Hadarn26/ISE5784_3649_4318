package primitives;

import static primitives.Util.isZero;

public class Vector extends Point {


    public Vector(double x,double y,double z){
        super(x,y,z);
        if(Double3.ZERO.equals(new Double3(x,y,z)))
            throw  new IllegalArgumentException("x,y,z can't be 0");
    }

    public Vector(Double3 xyz) {
         super(xyz);
        if(Double3.ZERO.equals(xyz))
            throw  new IllegalArgumentException("can't be (0,0,0)");
    }

    public Vector Add(Vector v){
        ///if(Double3.ZERO.equals(temp.xyz))
        ///    throw  new IllegalArgumentException("can't be (0,0,0)");
        return new Vector(super.Add(v).xyz);
    }

    public Vector scale(double num){
        return new Vector(xyz.scale(num));
    }

    public double dotProduct(Vector v){
        return xyz.d1*v.xyz.d1 + xyz.d2*v.xyz.d2 + xyz.d3*v.xyz.d3;
    }

    public  Vector crossProduct(Vector v){
        double x=(xyz.d2*v.xyz.d3)-(xyz.d3*v.xyz.d2);
        double y=(xyz.d3*v.xyz.d1)-(xyz.d1*v.xyz.d3);
        double z=(xyz.d1*v.xyz.d2)-(xyz.d2*v.xyz.d1);
        return new Vector(x,y,z);
    }

    public double lengthSquared(){
       return dotProduct(this);
    }

    public  double length(){
        return Math.sqrt(lengthSquared());
    }

    public Vector normalize(){
        return scale(1/length());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Vector other)
                && xyz.equals(other.xyz);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
