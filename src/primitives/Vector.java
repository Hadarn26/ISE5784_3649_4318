package primitives;

import static primitives.Util.isZero;

/**
 * Represents a vector in three-dimensional space.
 * A vector is a directed line segment with magnitude and direction.
 *
 * <p>
 * This class extends the {@link Point} class, inheriting its properties and methods.
 * </p>
 *
 * <p>
 * Instances of this class are immutable once created.
 * </p>
 *
 * <p>
 * This class provides methods for creating vectors and ensures that the vector's coordinates
 * are not equal to zero.
 * </p>
 *
 * <p>
 * The class provides constructors for creating vectors from coordinates or from a {@link Double3} object.
 * If the coordinates or the {@code Double3} object are equal to zero, an {@link IllegalArgumentException} is thrown.
 * </p>
 *
 * <p>
 * Vectors are compared for equality based on their coordinates.
 * </p>
 *
 * @author Hadar Nagar & Elinoy Damari
 */
public class Vector extends Point {

    /**
     * Constructs a vector with the specified coordinates.
     * @param x The x-coordinate of the vector.
     * @param y The y-coordinate of the vector.
     * @param z The z-coordinate of the vector.
     * @throws IllegalArgumentException if any coordinate is equal to zero.
     */
    public Vector(double x,double y,double z){
        super(x,y,z);
        if(Double3.ZERO.equals(new Double3(x,y,z)))
            throw  new IllegalArgumentException("x,y,z can't be 0");
    }

    /**
     * Constructs a vector from a {@link Double3} object representing the coordinates.
     * @param xyz The coordinates of the vector.
     * @throws IllegalArgumentException if the coordinates are equal to zero.
     */
    public Vector(Double3 xyz) {
         super(xyz);
        if(Double3.ZERO.equals(xyz))
            throw  new IllegalArgumentException("can't be (0,0,0)");
    }

    /**
     * Computes the vector resulting from adding another vector to this vector.
     * @param v The vector to add.
     * @return The new vector resulting from adding the specified vector to this vector.
     */
    public Vector add(Vector v){
        return new Vector(xyz.add(v.xyz));
    }

    /**
     * Scales this vector by a scalar value.
     * @param num The scalar value to scale the vector by.
     * @return The new scaled vector.
     */
    public Vector scale(double num){
        return new Vector(xyz.scale(num));
    }

    /**
     * Computes the dot product of this vector with another vector.
     *
     * @param v The other vector.
     * @return The dot product of this vector and the other vector.
     */
    public double dotProduct(Vector v){
        return xyz.d1*v.xyz.d1 + xyz.d2*v.xyz.d2 + xyz.d3*v.xyz.d3;
    }

    /**
     * Computes the cross product of this vector with another vector.
     *
     * @param v The other vector.
     * @return The cross product of this vector and the other vector.
     */
    public  Vector crossProduct(Vector v){
        double x=(xyz.d2*v.xyz.d3)-(xyz.d3*v.xyz.d2);
        double y=(xyz.d3*v.xyz.d1)-(xyz.d1*v.xyz.d3);
        double z=(xyz.d1*v.xyz.d2)-(xyz.d2*v.xyz.d1);
        return new Vector(x,y,z);
    }

    /**
     * Computes the square of the length (magnitude) of this vector.
     *
     * @return The square of the length of this vector.
     */
    public double lengthSquared(){
       return dotProduct(this);
    }

    /**
     * Computes the length (magnitude) of this vector.
     *
     * @return The length of this vector.
     */
    public  double length(){
        return Math.sqrt(lengthSquared());
    }

    /**
     * Returns a new vector that is a normalized version of this vector.
     *
     * <p>
     * A normalized vector has the same direction as this vector, but its length (magnitude) is 1.
     * </p>
     *
     * @return The normalized vector.
     */
    public Vector normalize(){
        return scale(1/length());
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * <p>
     * Two vectors are considered equal if they have the same coordinates.
     * </p>
     *
     * @param obj The reference object with which to compare.
     * @return {@code true} if this vector is the same as the obj argument; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Vector other)
                && xyz.equals(other.xyz);
    }

    /**
     * Returns a string representation of this vector.
     *
     * @return A string representation of this vector in the format "(x, y, z)".
     */
    @Override
    public String toString() {
        return super.toString();
    }
}
