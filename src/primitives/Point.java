package primitives;

import static primitives.Util.isZero;

/**
 * Represents a point in three-dimensional space.
 * This class encapsulates the coordinates of the point.
 *
 * <p>
 * The coordinates are stored as a {@link Double3} object, which represents
 * a triplet of double precision floating point numbers (x, y, z).
 * </p>
 *
 * <p>
 * Instances of this class are immutable once created.
 * </p>
 *
 * <p>
 * The class provides methods for creating points and performing vector operations
 * such as subtraction.
 * </p>
 *
 * <p>
 * This class also defines a constant {@code ZERO} representing the origin (0, 0, 0).
 * </p>
 *
 * @author Hadar Nagar & Elinoy Damari
 */
public class Point {

    /** The coordinates of the point.*/
    final Double3 xyz;

    /**Represents the origin point (0, 0, 0).*/
    public static final Point ZERO=new Point(0,0,0);

    /**
     * Constructs a point with the specified coordinates.
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     * @param z The z-coordinate of the point.
     */
    public Point (double x, double y, double z){
        xyz = new Double3(x, y, z);
    }

    /**
     * Constructs a point from a Double3 object representing the coordinates.
     *
     * @param xyz The coordinates of the point.
     */
    public Point(Double3 xyz){
        this.xyz= new Double3(xyz.d1, xyz.d2, xyz.d3);
    }

    /**
     * Computes the vector from this point to another point.
     * @param other The other point.
     * @return The vector representing the displacement from this point to the other point.
     * @throws NullPointerException if the other point is null.
     */
    public Vector subtract(Point other){
        if(other==null)
            throw new NullPointerException("the other point is null");
        return new Vector(xyz.subtract(other.xyz));
    }

    /**
     * Computes the point resulting from adding a vector to this point.
     * @param v The vector to add.
     * @return The new point resulting from adding the vector to this point.
     */
    public Point add(Vector v){
        return new Point(xyz.add(v.xyz));
    }

    /**
     * Computes the square of the distance between this point and another point.
     * @param other The other point.
     * @return The square of the distance between this point and the other point.
     */
    public double distanceSquared(Point other){
        return (this.xyz.d1-other.xyz.d1)*(this.xyz.d1-other.xyz.d1)
                + (this.xyz.d2-other.xyz.d2)*(this.xyz.d2-other.xyz.d2)
                + (this.xyz.d3-other.xyz.d3)*(this.xyz.d3-other.xyz.d3);
    }

    /**
     * Computes the distance between this point and another point.
     * @param other The other point.
     * @return The distance between this point and the other point.
     */
    public double distance(Point other){
        return Math.sqrt(distanceSquared(other));
    }

    /**
     * Returns a string representation of this point.
     * @return A string representation of this point in the format (x, y, z).
     */
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
