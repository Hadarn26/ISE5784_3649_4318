package geometries;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class SphereTest {

    private final Point p001 = new Point(0, 0, 1);
    private final Point p100 = new Point(1, 0, 0);
    private final Vector v001 = new Vector(0, 0, 1);


    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01:
        Sphere sp=new Sphere(1, Point.ZERO);
        Vector result=sp.getNormal(new Point(0,0,1));
        assertEquals(1,result.length(),"ERROR: Sphere's normal is not a unit vector ");
        assertEquals(new Vector(0,0,1),result,"ERROR: Sphere's getNormal(Point p) does not work correctly");
    }

    @Test
    void testFindIntsersections() {
        Sphere sphere = new Sphere( 1d, p100);
        final Point gp1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        final Point gp2 = new Point(1.53484692283495, 0.844948974278318, 0);
        var exp = List.of(gp1, gp2);
        final Vector v310 = new Vector(3, 1, 0);
        final Vector v110 = new Vector(1, 1, 0);
        final Vector v100=new Vector(1,0,0);
        final Point p01 = new Point(-1, 0, 0);
        final Point p02 = new Point(0.5, 0, 0);
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntsersections(new Ray(p01, v110)), "Ray's line out of sphere");
        // TC02: Ray starts before and crosses the sphere (2 points)
        final var result1 = sphere.findIntsersections(new Ray(p01, v310))
                .stream().sorted(Comparator.comparingDouble(p-> p.distance(p01))).toList();
        assertEquals(2, result1.size(), "Wrong number of points");
        assertEquals(exp, result1, "Ray crosses sphere");
        // TC03: Ray starts inside the sphere (1 point)
        exp=List.of(new Point(1.89,0.46,0));
        final var result2 = sphere.findIntsersections(new Ray(p02, v310))
                .stream().sorted(Comparator.comparingDouble(p-> p.distance(p02))).toList();
        assertEquals(1, result2.size(), "Wrong number of points");
        assertEquals(exp, result2, "Ray crosses sphere and start within the sphere");
        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findIntsersections(new Ray(new Point(0,-0.5,0), v110.scale(-1))), "Ray's line out of sphere and the opposite vector intersect with the sphere");
        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC05: Ray starts at sphere and goes inside (1 point)
        Ray r5=new Ray(new Point(1.61,0.61,0.51),v310.scale(-1));
        exp=List.of(new Point(0.15,0.12,0.51));
        final var result5 = sphere.findIntsersections(r5)
                .stream().sorted(Comparator.comparingDouble(p-> p.distance(new Point(1.61,0.61,0.51)))).toList();
        assertEquals(1, result5.size(), "Wrong number of points");
        assertEquals(exp, result5, "Ray starts at sphere and goes inside");
        // TC06: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntsersections(new Ray(new Point(1.61,0.61,0.51), v310)), "Ray starts at sphere and goes outside");

        // **** Group: Ray's line goes through the center
        // TC07: Ray starts before the sphere (2 points)
        exp=List.of(Point.ZERO,new Point(2,0,0));
        final var result7 = sphere.findIntsersections(new Ray(p01,v100 ))
                .stream().sorted(Comparator.comparingDouble(p-> p.distance(p01))).toList();
        assertEquals(2, result7.size(), "Wrong number of points");
        assertEquals(exp, result7, "Ray crosses sphere and start before the sphere");
        // TC08: Ray starts at sphere and goes inside (1 point)
        Ray r0=new Ray(Point.ZERO,v100);
        exp=List.of(new Point(2,0,0));
        final var result8 = sphere.findIntsersections(r0)
                .stream().sorted(Comparator.comparingDouble(p-> p.distance(Point.ZERO))).toList();
        assertEquals(1, result8.size(), "Wrong number of points");
        assertEquals(exp, result8, "Ray starts at sphere and goes inside");
        // TC09: Ray starts inside (1 point)
        Ray r9=new Ray(new Point(1.5,0,0),v100);
        exp=List.of(new Point(2,0,0));
        final var result9 = sphere.findIntsersections(r9)
                .stream().sorted(Comparator.comparingDouble(p-> p.distance(new Point(1.5,0,0)))).toList();
        assertEquals(1, result9.size(), "Wrong number of points");
        assertEquals(exp, result9, "Ray starts inside");
        // TC10: Ray starts at the center (1 point)
        Ray r100=new Ray(p100,v310);
        exp=List.of(new Point(1.95,0.32,0));
        final var result4 = sphere.findIntsersections(r100)
                .stream().sorted(Comparator.comparingDouble(p-> p.distance(p100))).toList();
        assertEquals(1, result4.size(), "Wrong number of points");
        assertEquals(exp, result4, "Ray crosses sphere and start within the sphere");
        // TC11: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntsersections(new Ray(new Point(2,0,0), v100)), "Ray starts at sphere and goes outside");
        // TC12: Ray starts after sphere (0 points)
        assertNull(sphere.findIntsersections(new Ray(new Point(3,0,0), v100)), "Ray starts after sphere");

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC13: Ray starts before the tangent point
        assertNull(sphere.findIntsersections(new Ray(new Point(0,0,1), v100)), "Ray starts before the tangent point");
        // TC14: Ray starts at the tangent point
        assertNull(sphere.findIntsersections(new Ray(new Point(1,0,1), v100)), "Ray starts at the tangent point");
        // TC15: Ray starts after the tangent point
        assertNull(sphere.findIntsersections(new Ray(new Point(3,0,1), v100)), "Ray starts after the tangent point");

        // **** Group: Special cases
        // TC16: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertNull(sphere.findIntsersections(new Ray(new Point(3,0,0), new Vector(0,0,1))), "Ray's line is outside, ray is orthogonal to ray start to sphere's center line");
    }
}