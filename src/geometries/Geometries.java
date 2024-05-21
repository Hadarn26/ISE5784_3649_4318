package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable{

    final private List<Intersectable> lst=new LinkedList<>();

    @Override
    public List<Point> findIntsersections(Ray ray) {
        return List.of();
    }
}
