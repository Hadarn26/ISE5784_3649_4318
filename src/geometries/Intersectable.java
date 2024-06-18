package geometries;
import primitives.*;
import java.util.List;
import java.util.Objects;

/**
 * Abstract class representing an intersectable geometrical shape.
 * Classes extending this class can be intersected by a ray,
 * and the intersections can be found and returned as a list of points.
 * <p>
 * The {@code Intersectable} class provides a structure for finding intersections,
 * and it is intended to be extended by specific geometric shapes.
 * </p>
 * <p>
 * author Hadar Nagar & Elinoy Damari
 * </p>
 */
public abstract class Intersectable {

    /**
     * Inner class representing a geometric point of intersection.
     */
    public static class GeoPoint {

        /**
         * The geometry that is intersected.
         */
        public Geometry geometry;
        /**
         * The point of intersection.
         */
        public Point point;

        /**
         * Constructs a GeoPoint with the specified geometry and point.
         *
         * @param geometry the geometry that is intersected
         * @param point    the point of intersection
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        /**
         * Checks whether this GeoPoint is equal to another object.
         *
         * @param o the object to compare with
         * @return true if the objects are equal, false otherwise
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GeoPoint geoPoint)) return false;
            return geometry==geoPoint.geometry && point.equals(geoPoint.point);
        }

        /**
         * Returns a string representation of the GeoPoint.
         *
         * @return a string representation of the GeoPoint
         */
        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }

    }

    /**
     * Finds the geometric intersections of a ray with the shape.
     * This method is final and calls the abstract {@code findGeoIntersectionsHelper} method.
     *
     * @param ray the ray to intersect with the shape
     * @return a list of geometric intersection points, or null if no intersections are found
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray){
       return findGeoIntersectionsHelper(ray);
    }

    /**
     * Helper method to be implemented by subclasses to find the geometric intersections of a ray with the shape.
     *
     * @param ray the ray to intersect with the shape
     * @return a list of geometric intersection points, or null if no intersections are found
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);

    /**
     * Finds the intersections of a ray with the shape.
     *
     * @param ray the ray to intersect with the shape
     * @return a list of intersection points, or null if no intersections are found
     */
   public List<Point> findIntersections(Ray ray){
       List<GeoPoint> geoList = findGeoIntersections(ray);
           return geoList == null ? null
                   : geoList.stream().map(gp -> gp.point).toList();
   }

}
