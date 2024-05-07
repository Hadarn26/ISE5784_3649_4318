package primitives;

import static primitives.Util.isZero;

public class Vector extends Point {


    public Vector(double x,double y,double z){
        super(x,y,z);
        if(Double3.ZERO.equals(new Double3(x,y,z)))
            throw  new IllegalArgumentException("x,y,z can't be 0");
    }

    public Vector(Double3 xyz) {

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
