package geometries;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Spheres
 * @author Hadar Nagar & Elinoy Damari
 */
class SphereTest {

    private final double DELTA = 0.00005;

    private final Point p001 = new Point(0, 0, 1);
    private final Point p100 = new Point(1, 0, 0);
    private final Vector v001 = new Vector(0, 0, 1);

    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: A point on the surface of the sphere
        Sphere sp=new Sphere(1, Point.ZERO);
        Vector result=sp.getNormal(new Point(0,0,1));
        // Check if the resulting normal vector is a unit vector
        assertEquals(1,result.length(),"ERROR: Sphere's normal is not a unit vector ");
        // Check if the normal vector is correctly calculated
        assertEquals(new Vector(0,0,1),result,
                "ERROR: Sphere's getNormal(Point p) does not work correctly");
    }

    @Test
    void testFindIntsersections() {
        Sphere sphere = new Sphere( 1d, p100);
        //usable points
        final Point gp1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        final Point gp2 = new Point(1.53484692283495, 0.844948974278318, 0);
        final Point p01 = new Point(-1, 0, 0);
        final Point p02 = new Point(0.5, 0, 0);
        final Point p200 = new Point(2, 0, 0);
        final Point p300 = new Point(3, 0, 0);
        final Point p110 = new Point(1, 1, 0);

        //usable vectors
        final Vector v310 = new Vector(3, 1, 0);
        final Vector v110 = new Vector(1, 1, 0);
        final Vector v100=new Vector(1,0,0);
        final Vector v001=new Vector(0,0,1);


        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(p01, v110)), "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        var exp = List.of(gp1, gp2);
        final var result2 = sphere.findIntersections(new Ray(p01, v310)).stream().sorted(Comparator
              .comparingDouble(p-> p.distance(p01))).toList();
        assertEquals(2, result2.size(), "Wrong number of points");
        assertEquals(exp, result2, "Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)
        exp=List.of(new Point(1.8867496997597595,0.4622498999199199,0));
        final var result3 = sphere.findIntersections(new Ray(p02, v310));
        /*.stream().sorted(Comparator
                .comparingDouble(p-> p.distance(p02))).toList();*/
        assertEquals(1, result3.size(), "Wrong number of points");
        assertEquals(exp, result3, "Ray crosses sphere and start within the sphere");

        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(0,-0.5,0), v110.scale(-1))),
                "Ray's line out of sphere and the opposite vector intersect with the sphere");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC05: Ray starts at sphere and goes inside (1 point)
//        exp=List.of(new Point(0.15,0.12,0.51));
//        final var result5 = sphere.findIntsersections(new Ray(new Point(1.61,0.61,0.51),
//                        v310.scale(-1)))/*
//        assertEquals(1, result5.size(), "Wrong number of points");
//        assertEquals(exp, result5, "Ray starts at sphere and goes inside");

        final var result = sphere.findIntersections(new Ray(Point.ZERO, new Vector(1, 1, 0))).stream().sorted(Comparator
                       .comparingDouble(p-> p.distance(Point.ZERO))).toList();
        assertEquals(1, result.size(), "There should be one intersection");
        assertEquals(List.of(p110), result, "Incorrect intersection point");
//        assertEquals(1, result.size(), DELTA, "Polygon's normal is not a unit vector");
        // TC06: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(1.61,0.61,0.51), v310)),
                "Ray starts at sphere and goes outside");

        // **** Group: Ray's line goes through the center
        // TC07: Ray starts before the sphere (2 points)
        exp=List.of(Point.ZERO,p200);
        final var result7 = sphere.findIntersections(new Ray(p01,v100 ))
                .stream().sorted(Comparator.comparingDouble(p-> p.distance(p01))).toList();
        assertEquals(2, result7.size(), "Wrong number of points");
        assertEquals(exp, result7, "Ray crosses sphere and start before the sphere");

        // TC08: Ray starts at sphere and goes inside (1 point)
        exp=List.of(p200);
        final var result8 = sphere.findIntersections(new Ray(Point.ZERO,v100))
                .stream().sorted(Comparator.comparingDouble(p-> p.distance(Point.ZERO))).toList();
        assertEquals(1, result8.size(), "Wrong number of points");
        assertEquals(exp, result8, "Ray starts at sphere and goes inside");

        // TC09: Ray starts inside (1 point)
        exp=List.of(p200);
        final var result9 = sphere.findIntersections(new Ray(new Point(1.5,0,0),v100))
                .stream().sorted(Comparator.comparingDouble(p-> p.distance(new Point(1.5,0,0)))).toList();
        assertEquals(1, result9.size(), "Wrong number of points");
        assertEquals(exp, result9, "Ray starts inside");

        // TC10: Ray starts at the center (1 point)
        exp=List.of(new Point(1.9486832980505138,0.31622776601683794,0));
        final var result10 = sphere.findIntersections(new Ray(p100,v310))
                .stream().sorted(Comparator.comparingDouble(p-> p.distance(p100))).toList();
        assertEquals(1, result10.size(), "Wrong number of points");
        assertEquals(exp, result10, "Ray crosses sphere and start within the sphere");

        // TC11: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(p200, v100)),
                "Ray starts at sphere and goes outside");

        // TC12: Ray starts after sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(p300, v100)),
                "Ray starts after sphere");

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC13: Ray starts before the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(0,0,1), v100)),
                "Ray starts before the tangent point");

        // TC14: Ray starts at the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(1,0,1), v100)),
                "Ray starts at the tangent point");

        // TC15: Ray starts after the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(3,0,1), v100)),
                "Ray starts after the tangent point");

        // **** Group: Special cases
        // TC16: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertNull(sphere.findIntersections(new Ray(p300, v001)),
                "Ray's line is outside, ray is orthogonal to ray start to sphere's center line");
    }

//    private void assertEquals(List<Point> p100, List<Point> result, double v, String incorrectIntersectionPoint) {
//
//        for(int i=0; i<result.size(); i++)
//        {
//            assertEquals(p100.get(i), result.get(i), 0.00001, "Incorrect intersection point");
//
//        }
//    }
}