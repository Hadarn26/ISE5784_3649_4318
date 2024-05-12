package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class PointTest {

    @Test
    void testSubtract() {
        Point  p1 = new Point(1, 2, 3);
        Point  p2 = new Point(2, 4, 6);
        assertEquals(new Point(1,2,3), p2.subtract(p1),"ERROR: (point2 - point1) does not work correctly");
    }

    @Test
    void testAdd() {
        Point  p1 = new Point(1, 2, 3);
        Vector v1 = new Vector(1, 2, 3);
        assertEquals(new Point(2,4,6), p1.add(v1),"ERROR: (point + vector) = other point does not work correctly");
    }

    @Test
    void testDistanceSquared() {
    }

    @Test
    void testDistance() {
    }
}