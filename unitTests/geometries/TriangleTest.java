package geometries;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Util;
import primitives.Vector;

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
    }
}