package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;
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
        Vector v1= vertices.get(0).subtract(ray.getHead());
        Vector v2= vertices.get(1).subtract(ray.getHead());
        Vector v3= vertices.get(2).subtract(ray.getHead());
        Vector n1=(v1.crossProduct(v2)).normalize();
        Vector n2=(v2.crossProduct(v3)).normalize();
        Vector n3=(v3.crossProduct(v1)).normalize();

        return null;
    }
}
