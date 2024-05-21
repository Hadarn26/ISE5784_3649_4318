package geometries;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class TriangleTest {

    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01:
        Point[] pts =
                { new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0) };
        Triangle tr = new Triangle(pts);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> tr.getNormal(new Point(0, 0, 1)), "");
        // generate the test result
        Vector result = tr.getNormal(new Point(0, 0, 1));
        // ensure |result| = 1
        assertEquals(1, result.length(),  "Triangle's normal is not a unit vector");
        // ensure the result is orthogonal to all the edges
        for (int i = 0; i < 3; ++i)
            assertTrue(Util.isZero( result.dotProduct(pts[i].subtract(pts[i == 0 ? 2 : i - 1]))),
                    "Triangle's normal is not orthogonal to one of the edges");
    }

    @Test
    void testFindIntsersections() {
        // all tests assume a point on the plane in which the triangle is on and check
        // if the function identifies whether the point is inside the triangle or not

        Triangle triangle = new Triangle(new Point(0, 0, 1), new Point(0, 1, 0), new Point(1, 0, 0));

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray intersects the triangle
        List<Point> result = triangle.findIntsersections(new Ray(new Point(0.5, 0.5, 1), new Vector(-0.5, -1, -1)));
        assertEquals(1, result.size(), "ERROR: findIntersections() did not return the right number of points");
        assertEquals(List.of(new Point(0.3, 0.1, 0.6)), result, "Incorrect intersection points");

        // TC02: Ray outside against edge
        assertNull(triangle.findIntsersections(new Ray(new Point(0.5, 0.5, 1), new Vector(-2, -0.5, -1))),
                "ERROR: findIntersections() did not return null");

        // TC03: Ray outside against vertex
        assertNull(triangle.findIntsersections(new Ray(new Point(0.5, 0.5, 1), new Vector(1, -0.5, -1))),
                "ERROR: findIntersections() did not return null");

        // =============== Boundary Values Tests ==================
        // TC04: Ray on edge
        assertNull(triangle.findIntsersections(new Ray(new Point(0.5, 0.5, 1), new Vector(-0.5, -0.1, -0.4))),
                "ERROR: findIntersections() did not return null");

        // TC05: Ray on vertex
        assertNull(triangle.findIntsersections(new Ray(new Point(0.5, 0.5, 1), new Vector(-0.5, 0.5, -1))),
                "ERROR: findIntersections() did not return null");

        // TC06: Ray on edge's continuation
        assertNull(triangle.findIntsersections(new Ray(new Point(0.5, 0.5, 1), new Vector(-0.5, -1, 0.5))),
                "ERROR: findIntersections() did not return null");
    }
}