package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Testing Points
 * @author Hadar Nagar & Elinoy Damari
 */
class PointTest {

    @Test
    void testSubtract() {
        Point  p1 = new Point(1, 2, 3);
        // ============ Equivalence Partitions Tests ==============
        Point  p2 = new Point(2, 4, 6);
        // TC01: Test that the new point is the right one
        assertEquals(new Point(1,2,3), p2.subtract(p1),"ERROR: substract() does not work correctly");

        // =============== Boundary Values Tests ==================
        // TC01: Test that exception is thrown for zero vector
        assertThrows(IllegalArgumentException.class, () -> p1.subtract(p1),
                "ERROR: subtract() does not throw an exception for subtracting point from itself => zero vector");
    }

    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the new point is the right one
        Point  p1 = new Point(1, 2, 3);
        Vector v1 = new Vector(1, 2, 3);
        assertEquals(new Point(2,4,6), p1.add(v1),"ERROR: add() does not work correctly");
    }

    @Test
    void testDistanceSquared() {
        Point p1 = new Point(1, 2, 3);
        // ============ Equivalence Partitions Tests ==============
        Point p2 = new Point(2, 4, 6);

        // TC01: Test that the new point is the right one
        assertEquals(14, p1.distanceSquared(p2), "ERROR: distanceSquared() does not work correctly");

        // =============== Boundary Values Tests ==================
        // TC01: Tests that distanceSquared works for the distance of a point from itself
        assertTrue(Util.isZero(p1.distanceSquared(p1)), "ERROR:distanceSquared() does not work for distance between point and itself");
    }

    @Test
    void testDistance() {
        Point p1 = new Point(1, 2, 3);
        // ============ Equivalence Partitions Tests ==============
        Point p2 = new Point(1, 5, 7);

        // TC01: Test that the new point is the right one
        assertEquals(5, p1.distance(p2),"ERROR: distance() does not work correctly");

        // =============== Boundary Values Tests ==================
        // TC01: Tests that distance works for the distance of a point from itself
        assertTrue(Util.isZero(p1.distance(p1)), "ERROR:distance() does not work for distance between point and itself");
    }
}