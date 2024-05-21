package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable{

    final private List<Intersectable> geometries=new LinkedList<>();

    public Geometries() {
    }

    public Geometries(Intersectable ...geometries){
        add(geometries);
    }
    public void add(Intersectable ...geometries){
        this.geometries.addAll(List.of(geometries));
    }
    @Override
    public List<Point> findIntsersections(Ray ray) {
        List<Point> intersections=null;
        for(Intersectable geometry: geometries){
            List<Point> currentIntersections=geometry.findIntsersections(ray);
            if(currentIntersections!=null){
                if(intersections==null)
                    intersections=new LinkedList<>();
                intersections.addAll(currentIntersections);
            }

        }
        return intersections;
    }
}
