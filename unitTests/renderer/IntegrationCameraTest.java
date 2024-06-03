package renderer;

import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Integration Camera Class
 * @author Hadar Nagar & Elinoy Damari
 */
class IntegrationCameraTest {

    private final Camera.Builder cameraBuilder = Camera.getBuilder().
            setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0)).
            setVpSize(3d, 3d);

    /**
     * Test intersections of rays from the camera with Sphere geometries.
     */
    @Test
    void testSphere() {

        //01: Small Sphere centered at (0,0,-3) with radius 1
        Camera camera1=cameraBuilder.setLocation( new Point(0, 0, 0)).setVpDistance(1d).build();
        Sphere sphere=new Sphere(1, new Point(0,0,-3));
        checkFunc(sphere, camera1, Point.ZERO, 2);

        //02: Large Sphere centered at (0,0,-2.5) with radius 2.5
        Camera camera2 =cameraBuilder.setLocation( new Point(0, 0, 0.5)).setVpDistance(1d).build();
        sphere=new Sphere(2.5, new Point(0,0,-2.5));
        checkFunc(sphere, camera2, new Point(0,0,0.5), 18);

        //03: Medium Sphere centered at (0,0,-2) with radius 2
        sphere=new Sphere(2, new Point(0,0,-2));
        checkFunc(sphere, camera2, new Point(0,0,0.5), 10);

        //04: Large Sphere centered at (0,0,-1) with radius 4
        sphere=new Sphere(4, new Point(0,0,-1));
        checkFunc(sphere, camera2, Point.ZERO, 9);

        //05: Small Sphere centered at (0,0,1) with radius 0.5
        sphere=new Sphere(0.5, new Point(0,0,1));
        checkFunc(sphere, camera1, Point.ZERO , 0);


    }

    /**
     * Test intersections of rays from the camera with Triangle geometries.
     */
    @Test
    void testTriangle() {
        Camera camera = cameraBuilder.setLocation( new Point(0, 0, 0)).setVpDistance(1d).build();

        //01: Small Triangle with vertices at (0,1,-2), (1,-1,-2), (-1,-1,-2)
        Triangle triangle=new Triangle(new Point(0,1,-2),new Point(1,-1,-2),new Point(-1,-1,-2));
        checkFunc(triangle, camera, Point.ZERO , 1);

        //02: Large Triangle with vertices at (0,20,-2), (1,-1,-2), (-1,-1,-2)
        triangle=new Triangle(new Point(0,20,-2),new Point(1,-1,-2),new Point(-1,-1,-2));
        checkFunc(triangle, camera, Point.ZERO , 2);

    }

    /**
     * Test intersections of rays from the camera with Plane geometries.
     */
    @Test
    void testPlane() {

        Camera camera =cameraBuilder.setLocation( new Point(0, 0, 0)).setVpDistance(1d).build();

        //01: Plane with point (0, 0, -3) and normal vector (0, 0, 1)
        Plane plane=new Plane(new Point(1, 1, -3), new Point(2,1,-3),  new Point(1,2,-3));
        checkFunc(plane, camera, Point.ZERO , 9);

        //02: Plane with point (0, 0, -3) and normal vector (0, 0.2, -1)
        plane=new Plane(new Point(0, 0, -3), new Point(1, 0, -3),new Point(0, 1, -2.5));
        checkFunc(plane, camera, Point.ZERO , 9);

        //03: Plane with point (0, 0, -3) and normal vector (0, 1, -1)
        plane=new Plane(new Point(0, 0, -3), new Point(1, 0, -3), new Point(0, 1, -2));
        checkFunc(plane, camera, Point.ZERO , 6);

    }

    /**
     * Helper method to check the number of intersection points for a given geometry.
     *
     * @param obj                 the intersectable geometry
     * @param cameraLocation      the location of the camera
     * @param numOfIntersections  the expected number of intersections
     */

    void checkFunc(Intersectable obj, Camera camera, Point cameraLocation, int numOfIntersections){

        //Camera camera=Camera.getBuilder().setLocation(cameraLocation).setDirection(new Vector(0,0,-1),new Vector(0,1,0)).setVpSize(3d,3d).setVpDistance(1d).build();

        Ray ray;

        int counter = 0;
        for(int i=0; i<3; i++)
            for (int j=0; j<3; j++) {
                ray = camera.constructRay(3, 3, j, i);
                List<Point> temp=obj.findIntsersections(ray);
                if(temp!=null)
                    counter+=temp.size();
            }
        assertEquals( numOfIntersections,counter,"constructRay does not work correctly");
    }
}
