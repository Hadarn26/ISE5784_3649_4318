package renderer;

import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;
import scene.Scene;

import static java.awt.Color.*;

public class SnookerTests {
    /**
     * Scene for the tests
     */
    private final Scene scene = new Scene("Test scene");
    /**
     * Camera builder for the tests with triangles
     */
    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setRayTracer(new SimpleRayTracer(scene));

    @Test
    public void mp1Test() {
        int nX = 600;
        int nY = 400;
        double zWall = -100;
        double yTable = 30;
        double zTable = -120;
        double n = 10;
        double h = 500;
        double xWall = nX / 2 - 2 * nX / 3 - 50;
        double b = 5;
        double bb = 1.5 * b;


        scene.setBackGround(new Color(WHITE));
//        Point p1=new Point(5,3,-10);
//        Point p2=new Point(10,3,0);
//        Point p3=new Point(-5,3,-10);
//    Polygon rectangle=new Polygon(p1,new Point(-5,3,-10),new Point(-5,-3,-10),new Point(5,-3,-10));
        scene.geometries.add(
                new Plane(new Point(nX / 2, nY / 2, zWall), new Point(xWall, nY / 2, zWall)
                        , new Point(xWall, -5 * nY / 44, zWall))
                        .setEmission(new Color(89, 52, 0)),
                new Plane(new Point(nX / 2, -5 * nY / 44, zWall), new Point(xWall, -5 * nY / 44, zWall), new Point(-nX / 2, -nY / 2, 1000))
                        .setEmission(new Color(245, 234, 180)),
                new Plane(new Point(xWall, nY / 2, zWall), new Point(xWall, -5 * nY / 44, zWall), new Point(-nX / 2, -nY / 2, 1000))
                        .setEmission(new Color(89, 52, 0)),
                new Polygon(new Point(-100, 30, -120), new Point(300, 30, -120)
                        , new Point(115, -55, 200), new Point(-190, -55, 200))
                        .setMaterial(new Material().setKs(0.5)),
                new Polygon(new Point(115, -55, 200), new Point(-190, -55, 200),
                        new Point(-190 + bb, -55 - bb, 200), new Point(115 , -55 - bb, 200))
                        .setMaterial(new Material().setKs(0.5)),
                new Polygon(new Point(300, 30, -120), new Point(115, -55, 200),
                        new Point(115, -55 -  bb, 200), new Point(300, 30 - bb, -120))
                        .setMaterial(new Material().setKs(0.5)),
                new Polygon(new Point(-100 + b, 30 + 0.0001, -120), new Point(300 - b, 30 + 0.0001, -120)
                        , new Point(115 - b, -55 + 0.0001, 200), new Point(-190 + b, -55 + 0.0001, 200))
                        .setEmission(new Color(29, 148, 0))

//                new Polygon(new Point(-100+bb,30-bb,-120),new Point(300+bb,30-bb,-120)
//                        ,new Point(115+bb,-55-bb,200),new Point(-190+bb,-55-bb,200))
//                        .setMaterial(new Material().setKs(0.5)).setEmission(new Color(GRAY))


        );

        cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000d)
                .setVpSize((double) nY, (double) nX)
                //.setAntiAliasingFactor(3)
                .setImageWriter(new ImageWriter("mp1SNOOKER", nX, nY))
                .build()
                .renderImage()
                .writeToImage();


    }
}
