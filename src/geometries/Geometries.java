package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * The Geometries class represents a collection of intersectable geometrical shapes.
 * It implements the Intersectable interface.
 *
 * @author  Hadar Nagar & Elinoy Damari
 */
public class Geometries implements Intersectable{

    /**
     * A list of geometrical shapes that can be intersected.
     */
    final private List<Intersectable> geometries=new LinkedList<>();

    /**
     * Constructs an empty Geometries object.
     */
    public Geometries() {
    }

    /**
     * Constructs a Geometries object with the specified intersectable geometries.
     *
     * @param geometries The geometries to add to the collection upon creation.
     */
    public Geometries(Intersectable ...geometries){
        add(geometries);
    }

    /**
     * Adds one or more intersectable geometries to the collection.
     *
     * @param geometries The geometries to add to the collection.
     */
    public void add(Intersectable ...geometries){
        this.geometries.addAll(List.of(geometries));
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> intersections=null;
        for(Intersectable geometry: geometries){
            List<Point> currentIntersections=geometry.findIntersections(ray);
            if(currentIntersections!=null){
                if(intersections==null)
                    intersections=new ArrayList<Point>();
                intersections.addAll(currentIntersections);
            }
        }
        return intersections;
    }
}
