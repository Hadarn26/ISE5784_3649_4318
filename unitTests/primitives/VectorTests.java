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
    }

    @Test
    void testScale() {
    }

    @Test
    void testDotProduct() {
    }

    @Test
    void testCrossProduct() {
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