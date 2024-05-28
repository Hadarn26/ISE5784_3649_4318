package renderer;

import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class IntegrationCameraTest {

    @Test
    void testSphere() {

        //01
        Sphere sphere=new Sphere(1, new Point(0,0,-3));
        checkFunc(sphere, Point.ZERO, 2);

        //02
        sphere=new Sphere(2.5, new Point(0,0,-2.5));
        checkFunc(sphere, new Point(0,0,0.5), 18);

        //03
        sphere=new Sphere(2, new Point(0,0,-2));
        checkFunc(sphere, new Point(0,0,0.5), 10);

        //04
        sphere=new Sphere(4, new Point(0,0,-1));
        checkFunc(sphere, Point.ZERO, 9);

        //05
        sphere=new Sphere(0.5, new Point(0,0,1));
        checkFunc(sphere, Point.ZERO , 0);


    }

    @Test
    void testTriangle() {

        //01
        Triangle triangle=new Triangle(new Point(0,1,-2),new Point(1,-1,-2),new Point(-1,-1,-2));
        checkFunc(triangle, Point.ZERO , 1);

        //02
        triangle=new Triangle(new Point(0,20,-2),new Point(1,-1,-2),new Point(-1,-1,-2));
        checkFunc(triangle, Point.ZERO , 2);

    }

    @Test
    void testPlane() {

        //01
        Plane plane=new Plane(new Point(0, 0, -3), new Vector(0, 0, 1));
        checkFunc(plane, Point.ZERO , 9);

        //02
        plane=new Plane(new Point(0, 0, -3), new Vector(0, 0.2, -1));
        checkFunc(plane, Point.ZERO , 9);

        //03
        plane=new Plane(new Point(0, 0, -3), new Vector(0, 1, -1));
        checkFunc(plane, Point.ZERO , 6);

    }

    void checkFunc(Intersectable obj, Point cameraLocation, int numOfIntersections){

        Camera camera=Camera.getBuilder().setLocation(cameraLocation).setDirection(new Vector(0,0,-1),new Vector(0,-1,0)).setVpSize(3d,3d).setVpDistance(1d).build();
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
