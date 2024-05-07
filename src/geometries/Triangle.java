package geometries;

import primitives.Point;

/**
 * Represents a triangle in three-dimensional space.
 * This class extends Polygon, inheriting its properties and methods.
 *
 * @author Hadar&Elinoy
 * @version [Version number]
 */
public class Triangle extends Polygon{

    /**
     * Constructs a triangle with the specified vertices.
     *
     * @param vertices The vertices of the triangle.
     */

    public Triangle(Point... vertices) {
        super(vertices);
    }
}
