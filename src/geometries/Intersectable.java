package geometries;
import primitives.*;
import java.util.List;
import java.util.Objects;

/**
 * Interface representing an intersectable geometrical shape.
 * Classes implementing this interface can be intersected by a ray,
 * and the intersections can be found and returned as a list of points.
 *
 * @author  Hadar Nagar & Elinoy Damari
 */
public abstract class Intersectable {
    public static class GeoPoint {
        public Geometry geometry;
        public Point point;

        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GeoPoint geoPoint)) return false;
            return geometry==geoPoint.geometry && point.equals(geoPoint.point);
        }

    }
    /**
     * Finds intersections of a ray with the shape.
     *
     * @param ray The ray to intersect with the shape.
     * @return A list of intersection points, or null if no intersections are found.
     */
   public List<Point> findIntersections(Ray ray);

}
