package geometries;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
class TubeTest {

    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01:
        Tube t=new Tube(1,new Ray(new Point(1,1,1),new Vector(0,1,0)));
        Vector result=t.getNormal(new Point(1,3,2));
        assertEquals(1,result.length(),"ERROR: Triangle's normal is not a unit vector");
        assertEquals(new Vector(0,0,1),result,"ERROR: Tube's getNormal(Point p) does not work correctly");

        // =============== Boundary Values Tests ==================
        // TC01: Test that
        Vector result2=t.getNormal(new Point(1,1,2));
        assertEquals(new Vector(0,0,1),result2,"ERROR: Tube's getNormal(Point p) does not work correctly");

    }
  
}