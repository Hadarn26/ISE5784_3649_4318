package primitives;

public class Ray {
    private final Point head;
    private final Vector direction;

    public Ray(Point p, Vector v){
        head=p;
        direction=v.normalize();
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Ray other)&&this.head.equals(other.head)&&this.direction.equals(other.direction);
    }

    @Override
    public String toString() {
        return "head= "+head.toString()+" direction= "+direction.toString();
    }
}
