package renderer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import primitives.*;
import renderer.*;
import scene.Scene;

import java.util.List;
import java.util.stream.Collectors;
//import scene.Scene;

/**
 * Testing Camera Class
 * @author Dan
 */
class CameraTest {
    /** Camera builder for the tests */
    private final Camera.Builder cameraBuilder = Camera.getBuilder()
           .setRayTracer(new SimpleRayTracer(new Scene("Test")))
           .setImageWriter(new ImageWriter("Test", 1, 1))
            .setLocation(Point.ZERO)
            .setDirection(new Vector(0,0, -1), new Vector(0, -1, 0))
            .setVpDistance(10d);

    /**
     * Test method for
     * {@link renderer.Camera#constructRay(int, int, int, int)}.
     */
    @Test
    void testConstructRay() {
        final String badRay  = "Bad ray";

        // ============ Equivalence Partitions Tests ==============
        // EP01: 4X4 Inside (1,1)
        Camera camera1 = cameraBuilder.setVpSize(8d, 8d).build();
        assertEquals(new Ray(Point.ZERO, new Vector(1, -1, -10)),
                camera1.constructRay(4, 4, 1, 1), badRay);

        // =============== Boundary Values Tests ==================
        // BV01: 4X4 Corner (0,0)
        assertEquals(new Ray(Point.ZERO, new Vector(3, -3, -10)),
                camera1.constructRay(4, 4, 0, 0), badRay);

        // BV02: 4X4 Side (0,1)
        assertEquals(new Ray(Point.ZERO, new Vector(1, -3, -10)),
                camera1.constructRay(4, 4, 1, 0), badRay);

        // BV03: 3X3 Center (1,1)
        Camera camera2 = cameraBuilder.setVpSize(6d, 6d).build();
        assertEquals(new Ray(Point.ZERO, new Vector(0, 0, -10)),
                camera2.constructRay(3, 3, 1, 1), badRay);

        // BV04: 3X3 Center of Upper Side (0,1)
        assertEquals(new Ray(Point.ZERO, new Vector(0, -2, -10)),
                camera2.constructRay(3, 3, 1, 0), badRay);

        // BV05: 3X3 Center of Left Side (1,0)
        assertEquals(new Ray(Point.ZERO, new Vector(2, 0, -10)),
                camera2.constructRay(3, 3, 0, 1), badRay);

        // BV06: 3X3 Corner (0,0)
        assertEquals(new Ray(Point.ZERO, new Vector(2, -2, -10)),
                camera2.constructRay(3, 3, 0, 0), badRay);

    }
    @Test
    void testConstructRays() {
        List<Ray> result = cameraBuilder.setVpSize(100d,100d).setAntiAliasingFactor(3).build().constructRays(3, 3, 0, 1);
        assertEquals(9, result.size(), "ERROR: construstRays() did not return the right number of rays");
         result = cameraBuilder.setVpSize(100d,100d).setAntiAliasingFactor(1).build().constructRays(3, 3, 0, 1);
        assertEquals(1, result.size(), "ERROR: construstRays() did not return the right number of rays");
        assertEquals(cameraBuilder.build().constructRay(3,3,0,1), result.get(0), "ERROR: construstRays() did not return the right ray");
//        result = targetArea.constructRayBeamGrid().stream().filter(r -> r.getDirection().dotProduct(new Vector(0, 1, 0)) <= 0)
//                .collect(Collectors.toList());
//        assertEquals(6, result.size(), "ERROR: findIntersections() did not return the right number of reflected rays");
//
//        result = targetArea.constructRayBeamGrid().stream().filter(r -> r.getDirection().dotProduct(new Vector(0, 1, 0)) > 0)
//                .collect(Collectors.toList());
//        assertEquals(3, result.size(), "ERROR: findIntersections() did not return the right number of refracted rays");
    }

}

