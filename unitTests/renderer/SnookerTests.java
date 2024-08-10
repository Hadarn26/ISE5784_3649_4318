package renderer;

import geometries.*;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
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
            .setDirection( new Vector(0,0,-1),new Vector(0, 1, 0))
            .setRayTracer(new SimpleRayTracer(scene));
 //   @Test
//    public void cameraChangeLocationTest() {
//        scene.geometries.add(
//                new Plane(new Point(-1500,0,0),new Vector(1,0,0)).setEmission(new Color(RED)),
//                new Plane(new Point(0,-1500,0),new Vector(0,1,0)).setEmission(new Color(BLUE)),
//                new Plane(new Point(0,0,-1500),new Vector(0,0,1)).setEmission(new Color(YELLOW))
//        );
//        //  cameraBuilder.setLocation(new Point(1500, 1800, 2000)).setVpDistance(1000d)
//        cameraBuilder.setLocation(new Point(0, 0, 0)).setVpDistance(2000d)
//                .setVpSize((double) 400, (double) 600)
//                // .setAntiAliasingFactor(25)
//                .setImageWriter(new ImageWriter("angleCameraTest", 600, 400))
//                .build()
//                .renderImage()
//                .writeToImage();
//    }
    @Test
    public void mp1Test() {
        int nX = 600;
        int nY = 400;
        double zWall = -1000;
        double yTable = 30;
        double zTable = -120;
        double n = 10;
        double h = 500;
        double xWall = nX / 2 - 2 * nX / 3 - 50;
        double b = 5;
        double bb = 1.5 * b;
        double radiusBall = 7;
        double x = -100;
        double z = (-110454545.45 - 490909.09 * x) / 36818.18;
        scene.setBackGround(new Color(WHITE));

        scene.geometries.add(
                new Plane(new Point(nX / 2, nY / 2, zWall), new Point(xWall, nY / 2, zWall)
                        , new Point(xWall, -5 * nY / 44, zWall))
                        .setEmission(new Color(89, 52, 0)),
                        //.setMaterial(new Material().setKR(1)),
                new Plane(new Point(nX / 2, -5 * nY / 44, zWall), new Point(xWall, -5 * nY / 44, zWall), new Point(-nX / 2, -nY / 2, 1000))
                        .setEmission(new Color(245, 234, 180)),
                new Plane(new Point(xWall, nY / 2, zWall), new Point(xWall, -5 * nY / 44, zWall), new Point(-nX / 2, -nY / 2, 1000))
                        .setEmission(new Color(89, 52, 0)),
                new Polygon(new Point(-100, 30, -120), new Point(300, 30, -120)
                        , new Point(115, -55, 200), new Point(-190, -55, 200))
                        .setMaterial(new Material().setKs(0.5)),
                new Polygon(new Point(115, -55, 200), new Point(-190, -55, 200),
                        new Point(-190 + bb, -55 - bb, 200), new Point(115, -55 - bb, 200))/////////
                        .setMaterial(new Material().setKs(0.5)),
                new Polygon(new Point(300, 30, -120), new Point(115, -55, 200),
                        new Point(115, -55 - bb, 200), new Point(300, 30 - bb, -120))////////
                        .setMaterial(new Material().setKs(0.5)),
                new Polygon(new Point(-100 + b, 30 + 0.0001, -120), new Point(300 - b, 30 + 0.0001, -120)
                        , new Point(115 - b, -55 + 0.0001, 200), new Point(-190 + b, -55 + 0.0001, 200))
                        .setEmission(new Color(29, 148, 0)),//*********
                new Triangle(new Point(115, -55, 200), new Point(115 - b, -55, 200), new Point(115, -285, 200)),
                new Triangle(new Point(115, -55, 200), new Point(125, -55, 190), new Point(115, -285, 200)),
                new Triangle(new Point(-190, -55, 200), new Point(-190 + bb, -55, 200), new Point(-190, -285, 200)),
                new Triangle(new Point(-190, -55, 200), new Point(-190, -55, 190), new Point(-190, -285, 200)),
                new Triangle(new Point(300, 30, -120), new Point(300 - b, 30, -120), new Point(300, -285, -120)),
                new Triangle(new Point(300, 30, -120), new Point(300 - 2 * b, 30, -120), new Point(300, -285, -120)),
                new Triangle(new Point(-100, 30, -120), new Point(-100 + b, 30, -120), new Point(-100, -285, -120)),
                new Triangle(new Point(-100, 20, -120), new Point(-100 - b, 20, -110), new Point(-100, -285, -120)),
                new Triangle(new Point(-190 + bb, -55 - bb, 200), new Point(-190 + 5 * radiusBall + bb, -55 - bb, 200)
                        , new Point(-190 + 6 * radiusBall + bb, -55 - 2.5 * radiusBall - bb, 200 - radiusBall))
                        .setEmission(new Color(WHITE))
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setNShininess(30).setKT(0.8).setKR(0.3)),
                new Triangle(new Point(-190 + bb, -55 - bb, 200), new Point(-190 + 5 * radiusBall + bb, -55 - bb, 200 - 3 * radiusBall)
                        , new Point(-190 + 6 * radiusBall + bb, -55 - 2.5 * radiusBall - bb, 200 - radiusBall))
                        .setEmission(new Color(WHITE))
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setNShininess(30).setKT(0.8).setKR(0.3)),
                new Sphere(radiusBall, new Point(-190 + 4 * radiusBall + bb, -55 - 2 * radiusBall, 200 - radiusBall - bb))
                        .setEmission(new Color(RED))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setNShininess(20)),
                new Triangle(new Point(115, -55 - bb, 200), new Point(100 - 5 * radiusBall + bb, -55 - bb, 200)
                        , new Point(100 - 6 * radiusBall + bb, -55 - 2.5 * radiusBall - bb, 200 - radiusBall))
                        .setEmission(new Color(WHITE))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setNShininess(20)
                                .setKT(new Double3(0.5, 0, 0))),
                new Triangle(new Point(115, -55 - bb, 200), new Point(100 - 5 * radiusBall + bb, -55 - bb, 200 - 3 * radiusBall)
                        , new Point(100 - 6 * radiusBall + bb, -55 - 2.5 * radiusBall - bb, 200 - radiusBall))
                        .setEmission(new Color(WHITE))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setNShininess(20)
                                .setKT(new Double3(0.5, 0, 0))),
                new Triangle(new Point(-200, -105, 100), new Point(-200, 90, 120), new Point(-200, 100, 80))
                        .setEmission(new Color(214,165,143)),
                new Triangle(new Point(-200, -105, 55), new Point(-200, 100, 35), new Point(-200, 90, 75))
                        .setEmission(new Color(214,165,143)),
                new Triangle(new Point(-200, -105, 10), new Point(-200, 100, -10), new Point(-200, 90, 30))
                        .setEmission(new Color(214,165,143)),
                new Triangle(new Point(-200, -105, -35), new Point(-200, 100, -55), new Point(-200, 90, -15))
                        .setEmission(new Color(214,165,143)),
                new Polygon(new Point(-1, 200, 0), new Point(1, 200, 0), new Point(1, 140, 0)
                        , new Point(-1, 140, 0))
                        .setEmission(new Color(PINK)),
                new Sphere(30, new Point(0, 110, 0)).setEmission(new Color(WHITE))
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setNShininess(30).setKT(0.6)),
                      //  .setMaterial(new Material().setKs(0.00000000000001).setKT(0.8).setNShininess(50)),
                new Sphere(radiusBall, new Point(-60,10+2*radiusBall,-60)).setEmission(new Color (RED))
                        .setMaterial(new Material().setKd(0.1).setKs(0.7)),
                new Sphere(radiusBall, new Point(0,10+radiusBall,-10)).setEmission(new Color (ORANGE))
                        .setMaterial(new Material().setKd(0.1).setKs(0.7)),
                new Sphere(radiusBall, new Point(90,-15,75)).setEmission(new Color (WHITE))//
                        .setMaterial(new Material().setKd(0.1).setKs(0.7)),
                new Sphere(radiusBall, new Point(205,2,40)).setEmission(new Color (BLACK))
                        .setMaterial(new Material().setKd(0.1).setKs(0.7)),
                new Sphere(radiusBall, new Point(-120,-20,95)).setEmission(new Color (BLUE))
                        .setMaterial(new Material().setKd(0.1).setKs(0.7)),
                new Sphere(radiusBall, new Point(-75,-40,180)).setEmission(new Color (ORANGE))
                        .setMaterial(new Material().setKd(0.1).setKs(0.7)),
                new Sphere(radiusBall, new Point(85,-38,170)).setEmission(new Color (YELLOW))
                        .setMaterial(new Material().setKd(0.1).setKs(0.7)),
                new Sphere(radiusBall, new Point(65,-41,185)).setEmission(new Color (89,40,89))
                        .setMaterial(new Material().setKd(0.1).setKs(0.7)),
                new Sphere(radiusBall, new Point(-15,-15,75)).setEmission(new Color (GREEN))
                        .setMaterial(new Material().setKd(0.1).setKs(0.7))
        );
       // scene.setAmbientLight(new AmbientLight(new Color(WHITE),0.2));
        //scene.lights.add(new SpotLight(new Color(RED),new Point(0, 110, 0),new Vector(0,-1,0)).setkL(4E-5).setkQ(2E-7));
        scene.lights.add(new PointLight(new Color(251,248,176),new Point(0, 110, 0)).setkL(4E-5).setkQ(2E-7));
          scene.lights.add(new DirectionalLight(new Color(239,104,132),new Vector(-1, -1, 4)));
      //  scene.lights.add(new DirectionalLight(new Color(RED), new Vector(0, 0, -950)));
        scene.lights.add(new SpotLight(new Color(RED), new Point(90+2*radiusBall,-15,75+radiusBall), new Vector(-1, 0, -1))
        ) ;
        cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000d)
                .setVpSize((double) nY, (double) nX)
                .setAntiAliasingFactor(25)
                .setMultiThreading(3);
//                .setAdaptive(true)
//
//              //  .setImageWriter(new ImageWriter("mp1SNOOKER300rays", nX, nY))
//                .setImageWriter(new ImageWriter("mp2withAdaptiveSuperSampelingandThreads", nX, nY))
//                .build()
//                .renderImage()
//                .writeToImage();
        cameraBuilder
                .setAdaptive(false)

                //  .setImageWriter(new ImageWriter("mp1SNOOKER300rays", nX, nY))
                .setImageWriter(new ImageWriter("mp2WithThreads", nX, nY))
                .build()
                .renderImage()
                .writeToImage();
//        cameraBuilder
//                .setMultiThreading(0)
//                .setAdaptive(true)
//
//                //  .setImageWriter(new ImageWriter("mp1SNOOKER300rays", nX, nY))
//                .setImageWriter(new ImageWriter("mp2WithoutAnyImproves", nX, nY))
//                .build()
//                .renderImage()
//                .writeToImage();

    }


}
