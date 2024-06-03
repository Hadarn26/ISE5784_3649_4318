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

    /**
     * Test intersections of rays from the camera with Sphere geometries.
     */
    @Test
    void testSphere() {

//        //01: Small Sphere centered at (0,0,-3) with radius 1
        Sphere sphere=new Sphere(1, new Point(0,0,0));
       checkFunc(sphere, Point.ZERO, 9);

        //02: Large Sphere centered at (0,0,-2.5) with radius 2.5
        sphere=new Sphere(2.5, new Point(0,0,-2.5));
        checkFunc(sphere, new Point(0,0,0.5), 18);

        //03: Medium Sphere centered at (0,0,-2) with radius 2
        sphere=new Sphere(2, new Point(0,0,-2));
        checkFunc(sphere, new Point(0,0,0.5), 10);

        //04: Large Sphere centered at (0,0,-1) with radius 4
        sphere=new Sphere(4, new Point(0,0,-1));
        checkFunc(sphere, Point.ZERO, 9);

        //05: Small Sphere centered at (0,0,1) with radius 0.5
        sphere=new Sphere(50, new Point(0,0,1));
        checkFunc(sphere, Point.ZERO , 9);


    }

    /**
     * Test intersections of rays from the camera with Triangle geometries.
     */
    @Test
    void testTriangle() {

        //01: Small Triangle with vertices at (0,1,-2), (1,-1,-2), (-1,-1,-2)
        Triangle triangle=new Triangle(new Point(0,1,-2),new Point(1,-1,-2),new Point(-1,-1,-2));
        checkFunc(triangle, Point.ZERO , 1);

        //02: Large Triangle with vertices at (0,20,-2), (1,-1,-2), (-1,-1,-2)
        triangle=new Triangle(new Point(0,20,-2),new Point(1,-1,-2),new Point(-1,-1,-2));
        checkFunc(triangle, Point.ZERO , 2);

    }

    /**
     * Test intersections of rays from the camera with Plane geometries.
     */
    @Test
    void testPlane() {

        //01: Plane with point (0, 0, -3) and normal vector (0, 0, 1)
        Plane plane=new Plane(new Point(0, 0, -3), new Vector(0, 0, 1));
        checkFunc(plane, Point.ZERO , 9);

        //02: Plane with point (0, 0, -3) and normal vector (0, 0.2, -1)
        plane=new Plane(new Point(0, 0, -3), new Vector(0, 0.2, -1));
        checkFunc(plane, Point.ZERO , 9);

        //03: Plane with point (0, 0, -3) and normal vector (0, 1, -1)
        plane=new Plane(new Point(0, 0, -3), new Vector(0, 1, -1));
        checkFunc(plane, Point.ZERO , 6);

    }

    /**
     * Helper method to check the number of intersection points for a given geometry.
     *
     * @param obj                 the intersectable geometry
     * @param cameraLocation      the location of the camera
     * @param numOfIntersections  the expected number of intersections
     */

    private void checkFunc(Intersectable obj, Point cameraLocation, int numOfIntersections){

        Camera camera=Camera.getBuilder().setLocation(cameraLocation).setDirection(new Vector(0,1,0),new Vector(0,0,-1)).setVpSize(3d,3d).setVpDistance(1d).build();
        Ray ray;

        int counter = 0;
        for(int i=0; i<3; i++)
            for (int j=0; j<3; j++) {
                ray = camera.constructRay(3, 3, j, i);
                List<Point> temp=obj.findIntersections(ray);
                if(temp!=null)
                    counter+=temp.size();
            }
        assertEquals( numOfIntersections,counter,"constructRay does not work correctly");
    }
}
