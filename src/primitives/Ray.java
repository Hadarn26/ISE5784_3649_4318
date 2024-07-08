package primitives;

import java.util.List;
import geometries.Intersectable.GeoPoint;


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
    private static final double DELTA = 0.1;

    /**
     * Constructs a ray with the specified starting point and direction.
     * @param p The starting point (head) of the ray.
     * @param v The direction vector of the ray.
     */
    public Ray(Point p, Vector v){
        head=p;
        direction=v.normalize();
    }

    public Ray(Point p, Vector v, Vector normalToP){
        direction=v.normalize();
        double res = v.dotProduct(normalToP);

        head =  Util.isZero(res) ? p :p.add(normalToP.scale(Util.alignZero(res)<0? -DELTA:DELTA));

    }


    /**
     * Returns the direction vector of the ray.
     *
     * @return the direction vector of the ray.
     */
    public Vector getDirection(){
        return direction;
    }

    /**
     * Returns the starting point (head) of the ray.
     *
     * @return the starting point of the ray.
     */
    public Point getHead() {
        return head;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Ray other)&&this.head.equals(other.head)&&this.direction.equals(other.direction);
    }

    /**
     * Returns a point on the ray at a given distance from the head.
     *
     * @param t the distance from the head.
     * @return the point at the given distance.
     */
    public Point getPoint(double t){
        if(Util.isZero(t))
            return head;
        return  head.add(direction.scale(t));
    }

    @Override
    public String toString() {
        return "head= "+head.toString()+" direction= "+direction.toString();
    }

    /**
     * Finds and returns the closest point to the head of the ray from a list of points.
     *
     * @param points the list of points to search.
     * @return the closest point to the head of the ray, or null if the list is empty.
     */
    public Point findClosestPoint(List<Point> points){
        return points == null|| points.isEmpty() ? null
                : findClosestGeoPoint(points.stream()
                .map(p -> new GeoPoint(null, p)).toList()).point;
    }

    public GeoPoint findClosestGeoPoint(List<GeoPoint> geoPoints)
    {
        if (geoPoints.isEmpty())
            return null;
        GeoPoint closestPoint=geoPoints.getFirst();
        double minimumDistanceSquared=closestPoint.point.distanceSquared(head);
        double distanceSquared;
        for(GeoPoint geoPoint:geoPoints){
            distanceSquared=geoPoint.point.distanceSquared(head);
            if(distanceSquared<minimumDistanceSquared){
                minimumDistanceSquared=distanceSquared;
                closestPoint=geoPoint;
            }
        }
        return closestPoint;
    }
}
