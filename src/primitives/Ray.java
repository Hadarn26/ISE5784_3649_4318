package primitives;

/**
 * Represents a ray in three-dimensional space.
 * A ray is defined by its starting point (head) and direction.
 *
 * <p>
 * The direction vector represents the direction in which the ray extends from its head.
 * It is normalized to have unit length.
 * </p>
 *
 * <p>
 * Instances of this class are immutable once created.
 * </p>
 *
 * <p>
 * This class provides methods for creating rays and comparing them for equality.
 * </p>
 *
 * <p>
 * The {@code toString} method returns a string representation of the ray
 * in the format "head= (x, y, z) direction= (dx, dy, dz)".
 * </p>
 * @author Hadar Nagar & Elinoy Damari
 */
public class Ray {

    /** The starting point (head) of the ray. */
    final Point head;
    /** The direction vector of the ray. */
    final Vector direction;

    /**
     * Constructs a ray with the specified starting point and direction.
     * @param p The starting point (head) of the ray.
     * @param v The direction vector of the ray.
     */
    public Ray(Point p, Vector v){
        head=p;
        direction=v.normalize();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Ray other)&&this.head.equals(other.head)&&this.direction.equals(other.direction);
    }


    @Override
    public String toString() {
        return "head= "+head.toString()+" direction= "+direction.toString();
    }
}
