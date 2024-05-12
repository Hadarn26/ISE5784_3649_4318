package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VectorTests {

    @Test
    void testVector() {
        // =============== Boundary Values Tests ==================
        // TC01: Tests that zero vector throws exception
        assertThrows(IllegalArgumentException.class,()->new Vector(0,0,0),"ERROR: create new vector() does not throw an exception for sending 3 zeroes => zero vector");
    }

    @Test
    void testAdd() {
        Vector v1 = new Vector(1, 2, 3);
        // ============ Equivalence Partitions Tests ==============
        Vector v2 = new Vector(-2, -4, -6);
        // TC01: Test that the new vector is the right one
        assertEquals(new Vector(-1, -2, -3), v1.add(v2), "ERROR: add() does not work correctly");
        // =============== Boundary Values Tests ==================
        // TC01: Test opposite direction vector throws exception
        assertThrows(IllegalArgumentException.class, () -> v1.add(new Vector(-1, -2, -3)),
                "ERROR: does not throw exception for adding two vectors with opposite direction");
    }

    @Test
    void testScale() {
        Vector v1 = new Vector(1, 3, 5);
        // ============ Equivalence Partitions Tests ==============

        // TC01: Test that the new vector is the right one
        assertEquals(new Vector(2, 6, 10), v1.scale(2), "ERROR: scale() does not work correctly");

        // =============== Boundary Values Tests ==================

        // TC01: Test scaling by zero throws exception
        assertThrows(IllegalArgumentException.class, () -> v1.scale(0),
                "ERROR: scale() does not throw exception for scaling by zero");
    }

    @Test
    void testDotProduct() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Test vectors in acute angle
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(4, 5, 6);
        assertEquals(32, v1.dotProduct(v2), "ERROR: dotProduct() for acute angle does not work correctly");

        // TC02: Test vectors in obtuse angle
        Vector v3 = new Vector(-1, -1, -3);
        assertEquals(-12, v1.dotProduct(v3), "ERROR: dotProduct() for obtuse angle does not work correctly");

        // ================== Boundary Values Tests ==================
        // TC01: Test vectors in 90 degree angle
        Vector v4 = new Vector(1, 4, -3);
        assertEquals(0, v1.dotProduct(v4), "ERROR: dotProduct() for 90 degree angle does not work correctly");

        // TC02: Test vectors in 180 degree angle
        assertEquals(-14, v1.dotProduct(v1.scale(-1)),
                "ERROR: dotProduct() for 180 degree angle does not work correctly");

        // TC03: Text dotProduct when one of the vectors is the unit vector
        Vector v5 = new Vector(1, 0, 0);
        assertEquals(1, v1.dotProduct(v5), "ERROR: dotProduct() for unit vector does not work correctly");
    }

    @Test
    void testCrossProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, 3, -2);
        // ============ Equivalence Partitions Tests ==============
        Vector vr = new Vector(-13,2,3);
        // TC01:
        //
        assertEquals(vr,v1.crossProduct(v3), "ERROR: crossProduct() wrong result");
        // Test cross-product result orthogonality to its operands
        assertTrue(Util.isZero(vr.dotProduct(v1)) && Util.isZero(vr.dotProduct(v3)), "ERROR: crossProduct() result is not orthogonal to the operands");

        // =============== Boundary Values Tests ==================
        // TC01: Test zero vector from cross-product of co-lined vectors and two vectors in opposite directions and different length
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v2),
                "ERROR: does not throw exception for cross-product of co-lined vectors");

        // TC02: Test two vectors in same directions and different length


        // TC03: Test two vectors in opposite directions and same length

        // TC04: Test two vectors in same directions and same length

    }


    @Test
    void testLengthSquared() {
    }

    @Test
    void testLength() {
    }

    @Test
    void testNormalize() {
    }
}