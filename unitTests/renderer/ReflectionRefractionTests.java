/**
 *
 */
package renderer;

import static java.awt.Color.*;

import geometries.Polygon;
import lighting.DirectionalLight;
import lighting.PointLight;
import org.junit.jupiter.api.Test;

import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial
 * shadows
 * (with transparency)
 *
 * @author dzilb
 */
public class ReflectionRefractionTests {
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

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheres() {
        scene.geometries.add(
                new Sphere(50d, new Point(0, 0, -50)).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setNShininess(100).setKT(0.3)),
                new Sphere(25d, new Point(0, 0, -50)).setEmission(new Color(RED))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(100)));
        scene.lights.add(
                new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2))
                        .setkL(0.0004).setkQ(0.0000006));

        cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000d)
                .setVpSize(150d, 150d)
                .setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500))
                .build()
                .renderImage()
                .writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheresOnMirrors() {
        scene.geometries.add(
                new Sphere(400d, new Point(-950, -900, -1000)).setEmission(new Color(0, 50, 100))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setNShininess(20)
                                .setKT(new Double3(0.5, 0, 0))),
                new Sphere(200d, new Point(-950, -900, -1000)).setEmission(new Color(100, 50, 20))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setNShininess(20)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                        new Point(670, 670, 3000))
                        .setEmission(new Color(20, 20, 20))
                        .setMaterial(new Material().setKR(1)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                        new Point(-1500, -1500, -2000))
                        .setEmission(new Color(20, 20, 20))
                        .setMaterial(new Material().setKR(new Double3(0.5, 0, 0.4))));
        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));
        scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4))
                .setkL(0.00001).setkQ(0.000005));

        cameraBuilder.setLocation(new Point(0, 0, 10000)).setVpDistance(10000d)
                .setVpSize(2500d, 2500d)
                .setImageWriter(new ImageWriter("reflectionTwoSpheresMirrored", 500, 500))
                .build()
                .renderImage()
                .writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light with a
     * partially
     * transparent Sphere producing partial shadow
     */
    @Test
    public void trianglesTransparentSphere() {
        scene.geometries.add(
                new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                        new Point(75, 75, -150))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(60)),
                new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(60)),
                new Sphere(30d, new Point(60, 50, -50)).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setNShininess(30).setKT(0.6)));
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
        scene.lights.add(
                new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1))
                        .setkL(4E-5).setkQ(2E-7));

        cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000d)
                .setVpSize(200d, 200d)
                .setImageWriter(new ImageWriter("refractionShadow", 600, 600))
                .build()
                .renderImage()
                .writeToImage();
    }


    //      /*

    //      * Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
//		*		.setVPSize(200, 200).setVPDistance(1000);
//
//		*scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
//
//		*scene.geometries.add( //
//		*		new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150)) //
//		*				.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
//		*		new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
//		*				.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
//		*		new Sphere(30d, new Point(60, 50, -50)).setEmission(new Color(BLUE)) //
//		*				.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));
//
//		*scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
//		*		.setKl(4E-5).setKq(2E-7));
//
//		*ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
//		*camera.setImageWriter(imageWriter) //
//		*		.setRayTracer(new RayTracerBasic(scene)) //
//		*		.renderImage() //
//		*		.writeToImage();
//      *
//      * */
    @Test
    public void multyObjectsTest() {
//         Camera.Builder cameraBuilder2 = Camera.getBuilder().setLocation(new Point(0, 0, 1000))
//           .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
//           .setVpSize(200d, 200d)
//           .setVpDistance(1000d)
//           .setRayTracer(new SimpleRayTracer(scene));
//        scene.setAmbientLight(new AmbientLight(new Color(YELLOW), 0.45));
//   Point a = new Point(0, 0, -100), b = new Point(100, 100, 0), c = new Point(-100, 100, 0),
//           d = new Point(0, -100, 0);
//   Material mirror = new Material().setKR(0.1).setKd(0.5).setKs(0.8).setNShininess(3);
//        scene.geometries.add(
//                new Sphere(70d, Point.ZERO).setMaterial(new Material().setKd(0.4).setKs(0.001).setKT(0.2))
//           .setEmission(new Color(200, 200, 200)),
//           new Triangle(a, b, c).setMaterial(mirror).setEmission(new Color(BLUE)),
//           new Triangle(a, b, d).setMaterial(mirror).setEmission(new Color(RED)),
//           new Triangle(a, c, d).setMaterial(mirror).setEmission(new Color(GREEN)));
//        scene.lights.addAll(0, java.util.List.of(new PointLight(new Color(127, 80, 127), Point.ZERO)
//           .setKL(0.0007).setKQ(0.0000007),
//                new SpotLight(new Color(200, 100, 50), new Point(100, 0, 0), new Vector(0, -0.15, -1))
//           .setKL(0.006).setKQ(0.00006)));
//        cameraBuilder2.setImageWriter(new ImageWriter("multiObjectShadowTest", 600, 600)).build() //
//                .renderImage() //
//                .writeToImage();

        Point a = new Point(-80, -80, 200);
        Point b = new Point(70, -80, 100);
        Point c = new Point(-20, -80, -300);


        scene.geometries.add(
                new Sphere(50d, new Point(10, 10, -100)).setEmission(new Color(39, 183, 285))
                        .setMaterial(new Material().setKd(0.25).setKs(0.7).setNShininess(20).setKT(new Double3(0.2, 0, 0)))
                // new Triangle(a,b,c).setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(60))
        );

//              new Sphere(200d,new Point(-950, -900, -1000)).setEmission(new Color(100, 50, 20))
//                      .setMaterial(new Material().setKd(0.25).setKs(0.25).setNShininess(20)),
//
//              new Sphere(50d,new Point(10,10,-100)).setEmission(new Color(39,183,285))
//                      .setMaterial(new Material().setKd(0.25).setKs(0.25).setNShininess(20)
//                              .setKT(new Double3(0.5, 0, 0))),
//              new Sphere(40d,new Point(10,10,-100)).setEmission(new Color(WHITE))
//                      .setMaterial(new Material().setKd(0.25).setKs(0.25).setNShininess(20)
//                              .setKT(new Double3(0.5, 0, 0)))

//              new Sphere(50d,new Point(10,10,-100)).setEmission(new Color(39,183,285))
//                      .setMaterial(new Material().setKd(0.1).setKs(0.0001).setNShininess(50).setKR(0.2).setKT(0.7)),
//              new Sphere(2d,new Point(-17,-24,0)).setEmission(new Color(140,140,140))
//              .setMaterial(new Material().setKs(0.9).setNShininess(10)),
//              new Sphere(3d,new Point(-11,-28,0)).setEmission(new Color(140,140,140))
//                      .setMaterial(new Material().setKs(0.9).setNShininess(10)),
//              new Sphere(2d,new Point(-5,-31,0)).setEmission(new Color(140,140,140))
//                      .setMaterial(new Material().setKs(0.9).setNShininess(10)),
//              new Sphere(1d,new Point(-0.5,-33.5,0)).setEmission(new Color(140,140,140))
//                      .setMaterial(new Material().setKs(0.9).setNShininess(10))


        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
        scene.lights.add(
                //  new SpotLight(new Color(RED), new Point(150, 140, 50) ,new Vector(-1, -1, -4)).setKL(0.00001).setKQ(0.000005)
                new DirectionalLight(new Color(RED), new Vector(-1, -1, -4))
        );
        //new SpotLight(new Color(RED), new Point(40, 40, 50) ,new Vector(-1, -1, -4))
        //  .setKL(0.00001).setKQ(0.000005)
        // new DirectionalLight(new Color(RED), new Vector(-1, -1, -4))
        // new PointLight(new Color(RED), new Point(-20, -20, 50)).setKL(0.00001).setKQ(0.000005)


        //    scene.lights.add(new DirectionalLight(new Color(WHITE), new Vector(-1, -1, -100 )));//

        // ImageWriter imageWriter = new ImageWriter("multyObjects", 600, 600);
        cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000d)
                .setVpSize(200d, 200d)
                .setAntiAliasingFactor(9)
                .setImageWriter(new ImageWriter("SphereDirectionalMP1LightIMPROVED", 600, 600))
                .build()
                .renderImage()
                .writeToImage();


    }

    @Test
    public void mp1Test() {

        scene.geometries.add(new Sphere(50d, Point.ZERO).setEmission(Color.BLACK));
        scene.setBackGround(new Color(white));
        cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000d)
                .setVpSize(200d, 200d)
                .setImageWriter(new ImageWriter("MP1ImprovedImage2", 600, 600))
                .setAntiAliasingFactor(9)
                .build()
                .renderImage()
                .writeToImage();
    }

    @Test
    public void multyObjectTest() {
        scene.setBackGround(new Color(89, 188, 217));
        scene.geometries.add(
                new Triangle(new Point(-150, -150, -115), new Point(250, -150, -135),
                        new Point(-150, -30, -50))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(60)).setEmission(new Color(12, 143, 12)),
                new Sphere(10d, new Point(30, 20, -50)).setEmission(new Color(0, 218, 230))
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setNShininess(30).setKT(0.8).setKR(0.6)),
                new Sphere(5d, new Point(50, 40, -50)).setEmission(new Color(0, 218, 230))
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setNShininess(30).setKT(0.8).setKR(0.6)),
                new Sphere(8d, new Point(60, 15, -50)).setEmission(new Color(0, 218, 230))
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setNShininess(30).setKT(0.8).setKR(0.6)),
                new Triangle(new Point(-55, 40, -50), new Point(-40, -70, -50), new Point(-68, -70, -50))
                        .setEmission(new Color(61, 38, 12)),
                new Triangle(new Point(-55, 40, -50), new Point(-40, -70, -50), new Point(-54, -70, 0))
                        .setEmission(new Color(61, 38, 12)),
                new Triangle(new Point(-55, 40, -50), new Point(-54, -70, 0), new Point(-68, -70, -50))
                        .setEmission(new Color(61, 38, 12)),
                new Sphere(15,new Point(-55, 45, -47)).setMaterial(new Material().setKd(0.1))
                        .setEmission(new Color(64, 173, 27)),///////////////
                new Sphere(12,new Point(-40, 30, -45)).setMaterial(new Material().setKd(0.1))
                        .setEmission(new Color(64, 173, 27)),
                new Sphere(13,new Point(-35, 50, -55)).setMaterial(new Material().setKd(0.1))
                        .setEmission(new Color(64, 173, 27)),
                new Sphere(17,new Point(-50, 60, -52)).setMaterial(new Material().setKd(0.1))
                        .setEmission(new Color(64, 173, 27)),
                new Sphere(13.5,new Point(-70, 55, -49)).setMaterial(new Material().setKd(0.1))
                        .setEmission(new Color(64, 173, 27)),
                new Sphere(10,new Point(-75, 35, -55)).setMaterial(new Material().setKd(0.1))
                        .setEmission(new Color(64, 173, 27)),
                new Sphere(16,new Point(-60, 30, -65)).setMaterial(new Material().setKd(0.1))
                        .setEmission(new Color(64, 173, 27)),
                new Sphere(13.8,new Point(-85, 44, -60)).setMaterial(new Material().setKd(0.1))
                        .setEmission(new Color(64, 173, 27)),
                new Sphere(15,new Point(-25, 40, -60)).setMaterial(new Material().setKd(0.1))
                        .setEmission(new Color(64, 173, 27)),
                new Sphere(5,new Point(-30, 59, -48)).setMaterial(new Material().setKd(0.1))
                        .setEmission(new Color(64, 173, 27))


        );
        scene.setAmbientLight(new AmbientLight(new Color(153, 217, 234), 0.15));
//        scene.lights.add(
//                new DirectionalLight(new Color(700, 400, 400), new Vector(10, -10, -1))
//        );
//        scene.lights.add(
//                new SpotLight(new Color(YELLOW), new Point(-100,100,0), new Vector(10,-5,-10)).setkL(4E-5).setkQ(2E-7)
//        );
        scene.lights.add(
                new DirectionalLight(new Color(700, 400, 400), new Vector(20, -10, -10))
        );

        //  .setkL(4E-5).setkQ(2E-7));
        cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000d)
                .setVpSize(200d, 200d)
                .setImageWriter(new ImageWriter("multyObject07", 600, 600))
                .build()
                .renderImage()
                .writeToImage();
    }
}
