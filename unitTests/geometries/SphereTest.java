package geometries;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Util;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
class SphereTest {

    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01:
        Sphere sp=new Sphere(1, Point.ZERO);
        Vector result=sp.getNormal(new Point(0,0,1));
        assertEquals(1,result.length(),"ERROR: Sphere's normal is not a unit vector ");
        assertEquals(new Vector(0,0,1),result,"ERROR: Sphere's getNormal(Point p) does not work correctly");



    }
  
}