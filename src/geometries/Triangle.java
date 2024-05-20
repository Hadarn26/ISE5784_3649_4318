package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * Represents a triangle in three-dimensional space.
 * This class extends Polygon, inheriting its properties and methods.
 * @author Hadar Nagar & Elinoy Damari
 */
public class Triangle extends Polygon{

    /**
     * Constructs a triangle with the specified vertices.
     * @param vertices The vertices of the triangle.
     */

    public Triangle(Point... vertices) {
        super(vertices);
    }

    @Override
    public List<Point> findIntsersections(Ray ray) {
        return null;
    }
}
