package renderer;

import static java.awt.Color.*;

import org.junit.jupiter.api.Test;

import geometries.*;
import lighting.*;
import primitives.*;
import scene.Scene;

/**
 * Test rendering a basic image
 *
 * @author Dan
 */
public class LightsTests {
    /**
     * First scene for some of tests
     */
    private final Scene scene1 = new Scene("Test scene");
    /**
     * Second scene for some of tests
     */
    private final Scene scene2 = new Scene("Test scene")
            .setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));

    /**
     * First camera builder for some of tests
     */
    private final Camera.Builder camera1 = Camera.getBuilder()
            .setRayTracer(new SimpleRayTracer(scene1))
            .setLocation(new Point(0, 0, 1000))
            .setDirection( new Vector(0,0,-1),new Vector(0,1,0))//Point.ZERO
            .setVpSize(150d, 150d).setVpDistance(1000d);
    /**
     * Second camera builder for some of tests
     */
    private final Camera.Builder camera2 = Camera.getBuilder()
            .setRayTracer(new SimpleRayTracer(scene2))
            .setLocation(new Point(0, 0, 1000))
            .setDirection(new Vector(0,0,-1),new Vector(0,1,0))
            .setVpSize(200d, 200d).setVpDistance(1000d);

    /**
     * Shininess value for most of the geometries in the tests
     */
    private static final int SHININESS = 301;
    /**
     * Diffusion attenuation factor for some of the geometries in the tests
     */
    private static final double KD = 0.5;
    /**
     * Diffusion attenuation factor for some of the geometries in the tests
     */
    private static final Double3 KD3 = new Double3(0.2, 0.6, 0.4);

    /**
     * Specular attenuation factor for some of the geometries in the tests
     */
    private static final double KS = 0.5;
    /**
     * Specular attenuation factor for some of the geometries in the tests
     */
    private static final Double3 KS3 = new Double3(0.2, 0.4, 0.3);

    /**
     * Material for some of the geometries in the tests
     */
    private final Material material = new Material().setKd(KD3).setKs(KS3).setNShininess(SHININESS);
    /**
     * Light color for tests with triangles
     */
    private final Color trianglesLightColor = new Color(800, 500, 250);
    /**
     * Light color for tests with sphere
     */
    private final Color sphereLightColor = new Color(800, 500, 0);
    /**
     * Color of the sphere
     */
    private final Color sphereColor = new Color(BLUE).reduce(2);

    /**
     * Center of the sphere
     */
    private final Point sphereCenter = new Point(0, 0, -50);
    /**
     * Radius of the sphere
     */
    private static final double SPHERE_RADIUS = 50d;

    /**
     * The triangles' vertices for the tests with triangles
     */
    private final Point[] vertices =
            {
                    // the shared left-bottom:
                    new Point(-110, -110, -150),
                    // the shared right-top:
                    new Point(95, 100, -150),
                    // the right-bottom
                    new Point(110, -110, -150),
                    // the left-top
                    new Point(-75, 78, 100)
            };
    /**
     * Position of the light in tests with sphere
     */
    private final Point sphereLightPosition = new Point(-50, -50, 25);
    /**
     * Light direction (directional and spot) in tests with sphere
     */
    private final Vector sphereLightDirection = new Vector(1, 1, -0.5);
    /**
     * Position of the light in tests with triangles
     */
    private final Point trianglesLightPosition = new Point(30, 10, -100);
    /**
     * Light direction (directional and spot) in tests with triangles
     */
    private final Vector trianglesLightDirection = new Vector(-2, -2, -2);

    /**
     * The sphere in appropriate tests
     */
    private final Geometry sphere = new Sphere( SPHERE_RADIUS,sphereCenter)
            .setEmission(sphereColor).setMaterial(new Material().setKd(KD).setKs(KS).setNShininess(SHININESS));
    /**
     * The first triangle in appropriate tests
     */
    private final Geometry triangle1 = new Triangle(vertices[0], vertices[1], vertices[2])
            .setMaterial(material);
    /**
     * The first triangle in appropriate tests
     */
    private final Geometry triangle2 = new Triangle(vertices[0], vertices[1], vertices[3])
            .setMaterial(material);

    /**
     * Produce a picture of a sphere lighted by a directional light
     */
    @Test
    public void sphereDirectional() {
        scene1.geometries.add(sphere);
        scene1.lights.add(new DirectionalLight(sphereLightColor, sphereLightDirection));

        camera1.setImageWriter(new ImageWriter("lightSphereDirectional", 500, 500))
                .build()
                .renderImage()
                .writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a point light
     */
    @Test
    public void spherePoint() {
        scene1.geometries.add(sphere);
        scene1.lights.add(new PointLight(sphereLightColor, sphereLightPosition)
                .setKL(0.001).setKQ(0.0002));

        camera1.setImageWriter(new ImageWriter("lightSpherePoint", 500, 500))
                .build()
                .renderImage()
                .writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spotlight
     */
    @Test
    public void sphereSpot() {
        scene1.geometries.add(sphere);
        scene1.lights.add(new SpotLight(sphereLightColor, sphereLightPosition, sphereLightDirection)
                .setKL(0.001).setKQ(0.0001));

        camera1.setImageWriter(new ImageWriter("lightSphereSpot", 500, 500))
                .build()
                .renderImage()
                .writeToImage();
    }

    /**
     * Produce a picture of two triangles lighted by a directional light
     */
    @Test
    public void trianglesDirectional() {
        scene2.geometries.add(triangle1, triangle2);
        scene2.lights.add(new DirectionalLight(trianglesLightColor, trianglesLightDirection));

        camera2.setImageWriter(new ImageWriter("lightTrianglesDirectional", 500, 500)) //
                .build()
        .renderImage()
        .writeToImage();
    }

    /**
     * Produce a picture of two triangles lighted by a point light
     */
    @Test
    public void trianglesPoint() {
        scene2.geometries.add(triangle1, triangle2);
        scene2.lights.add(new PointLight(trianglesLightColor, trianglesLightPosition)
                .setKL(0.001).setKQ(0.0002));

        camera2.setImageWriter(new ImageWriter("lightTrianglesPoint", 500, 500)) //
                .build() //
                .renderImage() //
                .writeToImage(); //
    }

    /**
     * Produce a picture of two triangles lighted by a spotlight
     */
    @Test
    public void trianglesSpot() {
        scene2.geometries.add(triangle1, triangle2);
        scene2.lights.add(new SpotLight(trianglesLightColor, trianglesLightPosition, trianglesLightDirection)
                .setKL(0.001).setKQ(0.0001));

        camera2.setImageWriter(new ImageWriter("lightTrianglesSpot", 500, 500))
                .build() //
                .renderImage() //
                .writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a narrow spotlight
     */
    @Test
    public void sphereSpotSharp() {
        scene1.geometries.add(sphere);
        scene1.lights
                .add(new SpotLight(sphereLightColor, sphereLightPosition, new Vector(1, 1, -0.5)).setNarrowBeam(10)
                        .setKL(0.001).setKQ(0.00004));


        camera1.setImageWriter(new ImageWriter("lightSphereSpotSharp", 500, 500))
                .build() //
                .renderImage() //
                .writeToImage();




    }

    /**
     * Produce a picture of two triangles lighted by a narrow spotlight
     */
    @Test
    public void trianglesSpotSharp() {
        scene2.geometries.add(triangle1, triangle2);
        scene2.lights.add(new SpotLight(trianglesLightColor, trianglesLightPosition, trianglesLightDirection).setNarrowBeam(10)
                .setKL(0.001).setKQ(0.00004));


        camera2.setImageWriter(new ImageWriter("lightTrianglesSpotSharp", 500, 500)).setRayTracer(new SimpleRayTracer(scene2))
                .build() //
                .renderImage() //
                .writeToImage();

//
//        ImageWriter imageWriter = new ImageWriter("lightTrianglesSpotSharp", 500, 500);
//        camera2.setImageWriter(imageWriter) //
//
//                .renderImage() //
//                .writeToImage(); //
//    }
    }

    @Test
    public void sphereMultiLightSource() {
        scene1.geometries.add(sphere);
        scene1.lights.add(new SpotLight(new Color(242, 0, 0), new Point(0, 1000, 1000), new Vector(0, -1, -1))
                .setKL(0.000001).setKQ(0.0000004));
        scene1.lights
                .add(new PointLight(new Color(0, 203, 0), new Point(-25, 0, 25)).setKL(0.00000001).setKQ(0.000000002));
        scene1.lights.add(new DirectionalLight(new Color(241, 199, 0), new Vector(0, 0, -1)));
        ImageWriter imageWriter = new ImageWriter("sphereMultiLightSource", 500, 500);
        camera1.setImageWriter(imageWriter) //
                .setRayTracer(new SimpleRayTracer(scene1)) .build()//
                .renderImage()
         //
                .writeToImage(); //

    }

    /**
     * Produce a picture of two triangles with multiple light sources
     */
    @Test
    public void triangleMultiLightSource() {
        scene2.geometries.add(triangle1, triangle2);
        scene2.lights.add(new SpotLight(new Color(0, 0, 255), new Point(1, 1000, 1000), new Vector(0, -1, -1))
                .setKL(0.00000005).setKQ(0.0000004));
        scene2.lights
                .add(new PointLight(new Color(1, 255, 0), new Point(0, 200, 200)).setKL(0.00000001).setKQ(0.000000002));
        scene2.lights.add(new DirectionalLight(new Color(255, 1, 0), new Vector(0, 1, -100000)));
        ImageWriter imageWriter = new ImageWriter("triangleMultiLightSource", 500, 500);
        camera2.setImageWriter(imageWriter) //
                .setRayTracer(new SimpleRayTracer(scene2))
                .build() //
                .renderImage() //
                .writeToImage();//
    }

//    /** Produce a picture of a sphere lighted by a narrow spotlight */
//    @Test
//    public void sphereSpotSharp() {
//
//    }

//    /**
//     * Produce a picture of two triangles lighted by a narrow spotlight
//     */
//    @Test
//    public void trianglesSpotSharp() {
//        scene2.geometries.add(triangle1, triangle2);
//        scene2.lights.add(new SpotLight(trianglesLightColor, trianglesLightPosition, trianglesLightDirection)
//                .setNarrowBeam(10).setkL(0.001).setkQ(0.00004));
//
//        ImageWriter imageWriter = new ImageWriter("lightTrianglesSpotSharp", 500, 500);
//        camera2.setImageWriter(imageWriter) //
//                .setRayTracer(new SimpleRayTracer(scene2)) //
//                .renderImage() //
//                .writeToImage(); //
//    }

}
//
//package renderer;
//
//import static java.awt.Color.*;
//
//import org.junit.jupiter.api.Test;
//
//import geometries.*;
//import lighting.*;
//import primitives.*;
//import renderer.*;
//import scene.Scene;
//
///**
// * Test rendering a basic image
// *
// * @author Dan
// */
//public class LightsTests {
//    private final Scene scene1 = new Scene("Test scene");
//    private final Scene scene2 = new Scene("Test scene")
//            .setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));
//        private final Camera.Builder camera1 = Camera.getBuilder()
//            .setRayTracer(new SimpleRayTracer(scene1))
//            .setLocation(new Point(0, 0, 1000))
//            .setDirection( new Vector(0,1,0),new Vector(0,0,-1))//Point.ZERO
//            .setVpSize(150d, 150d).setVpDistance(1000d);
//    private final Camera.Builder camera2 = Camera.getBuilder()
//            .setRayTracer(new SimpleRayTracer(scene1))
//            .setLocation(new Point(0, 0, 1000))
//            .setDirection( new Vector(0,1,0),new Vector(0,0,-1))//Point.ZERO
//            .setVpSize(200d, 200d).setVpDistance(1000d);
//
//
//    private static final int SHININESS = 301;
//    private static final double KD = 0.5;
//    private static final Double3 KD3 = new Double3(0.2, 0.6, 0.4);
//
//    private static final double KS = 0.5;
//    private static final Double3 KS3 = new Double3(0.2, 0.4, 0.3);
//
//    private final Material material = new Material().setkD(KD3).setkS(KS3).setnShininess(SHININESS);
//    private final Color trianglesLightColor = new Color(800, 500, 250);
//    private final Color sphereLightColor = new Color(800, 500, 0);
//    private final Color sphereColor = new Color(BLUE).reduce(2);
//
//    private final Point sphereCenter = new Point(0, 0, -50);
//    private static final double SPHERE_RADIUS = 50d;
//
//    // The triangles' vertices:
//    private final Point[] vertices = {
//            // the shared left-bottom:
//            new Point(-110, -110, -150),
//            // the shared right-top:
//            new Point(95, 100, -150),
//            // the right-bottom
//            new Point(110, -110, -150),
//            // the left-top
//            new Point(-75, 78, 100) };
//    private final Point sphereLightPosition = new Point(-50, -50, 25);
//    private final Point trianglesLightPosition = new Point(30, 10, -100);
//    private final Vector trianglesLightDirection = new Vector(-2, -2, -2);
//
//    private final Geometry sphere = new Sphere(SPHERE_RADIUS, sphereCenter).setEmission(sphereColor)
//            .setMaterial(new Material().setkD(KD).setkS(KS).setnShininess(SHININESS));
//    private final Geometry triangle1 = new Triangle(vertices[0], vertices[1], vertices[2]).setMaterial(material);
//    private final Geometry triangle2 = new Triangle(vertices[0], vertices[1], vertices[3]).setMaterial(material);
//
//    /** Produce a picture of a sphere lighted by a directional light */
//    @Test
//    public void sphereDirectional() {
//        scene1.geometries.add(sphere);
//        scene1.lights.add(new DirectionalLight(sphereLightColor, new Vector(1, 1, -0.5)));
//
//        ImageWriter imageWriter = new ImageWriter("lightSphereDirectional", 500, 500);
//        camera1.setImageWriter(imageWriter) //
//                .setRayTracer(new SimpleRayTracer(scene1)) //
//                .renderImage(); //
//               camera1 .writeToImage(); //
//    }
//
//    /** Produce a picture of a sphere lighted by a point light */
//    @Test
//    public void spherePoint() {
//        scene1.geometries.add(sphere);
//        scene1.lights.add(new PointLight(sphereLightColor, sphereLightPosition).setkL(0.001).setkC(0.0002));
//
//        ImageWriter imageWriter = new ImageWriter("lightSpherePoint", 500, 500);
//        camera1.setImageWriter(imageWriter) //
//                .setRayTracer(new SimpleRayTracer(scene1)) //
//                .renderImage(); //
//                camera1.writeToImage(); //
//    }
//
//    /** Produce a picture of a sphere lighted by a spotlight */
//    @Test
//    public void sphereSpot() {
//        scene1.geometries.add(sphere);
//        scene1.lights.add(new SpotLight(sphereLightColor, sphereLightPosition, new Vector(1, 1, -0.5)).setkL(0.001)
//                .setkQ(0.0001));
//
//        ImageWriter imageWriter = new ImageWriter("lightSphereSpot", 500, 500);
//        camera1.setImageWriter(imageWriter) //
//                .setRayTracer(new SimpleRayTracer(scene1)) //
//                .renderImage() ;//
//        camera1.writeToImage(); //
//    }
//
//    /** Produce a picture of two triangles lighted by a directional light */
//    @Test
//    public void trianglesDirectional() {
//        scene2.geometries.add(triangle1, triangle2);
//        scene2.lights.add(new DirectionalLight(trianglesLightColor, trianglesLightDirection));
//
//        ImageWriter imageWriter = new ImageWriter("lightTrianglesDirectional", 500, 500);
//        camera2.setImageWriter(imageWriter) //
//                .setRayTracer(new SimpleRayTracer(scene2)) //
//                .renderImage(); //
//        camera2.writeToImage(); //
//    }
//
//    /** Produce a picture of two triangles lighted by a point light */
//    @Test
//    public void trianglesPoint() {
//        scene2.geometries.add(triangle1, triangle2);
//        scene2.lights.add(new PointLight(trianglesLightColor, trianglesLightPosition).setkL(0.001).setkQ(0.0002));
//
//        ImageWriter imageWriter = new ImageWriter("lightTrianglesPoint", 500, 500);
//        camera2.setImageWriter(imageWriter) //
//                .setRayTracer(new SimpleRayTracer(scene2)) //
//                .renderImage(); //
//        camera2.writeToImage(); //
//    }
//
//    /** Produce a picture of two triangles lighted by a spotlight */
//    @Test
//    public void trianglesSpot() {
//        scene2.geometries.add(triangle1, triangle2);
//        scene2.lights.add(new SpotLight(trianglesLightColor, trianglesLightPosition, trianglesLightDirection)
//                .setkL(0.001).setkQ(0.0001));
//
//        ImageWriter imageWriter = new ImageWriter("lightTrianglesSpot", 500, 500);
//        camera2.setImageWriter(imageWriter) //
//                .setRayTracer(new SimpleRayTracer(scene2)) //
//                .renderImage(); //
//        camera2.writeToImage(); //
//    }
//
//    /** Produce a picture of a sphere lighted by a spotlight */
//    @Test
//    public void sphereLights() {
//        scene1.geometries.add(sphere);
//        scene1.lights.add(new SpotLight(sphereLightColor, sphereLightPosition, new Vector(1, 1, -0.5)).setkL(0.001)
//                .setkQ(0.0001));
//        scene1.lights.add(new PointLight(new Color(290, 90, 100), new Point(80, 80, 0)).setkL(0.0003).setkQ(0.00003));
//        scene1.lights.add(new DirectionalLight(new Color(52, 232, 235), new Vector(0, 1, 0)));
//
//        ImageWriter imageWriter = new ImageWriter("SphereLights", 500, 500);
//        camera1.setImageWriter(imageWriter) //
//                .setRayTracer(new SimpleRayTracer(scene1)) //
//                .renderImage(); //
//        camera1.writeToImage(); //
//    }
//
//    /** Produce a picture of two triangles lighted by a spotlight */
//    @Test
//    public void trianglesLights() {
//        scene2.geometries.add(triangle1, triangle2);
//        scene2.lights.add(new SpotLight(trianglesLightColor, trianglesLightPosition, trianglesLightDirection)
//                .setkL(0.001).setkQ(0.0001));
//        scene2.lights.add(new PointLight(new Color(250, 2, 250), new Point(80, 80, 60)).setkL(0.0003).setkQ(0.00003));
//        scene2.lights.add(new DirectionalLight(new Color(150, 10, 20), new Vector(0, -1, -1)));
//
//        ImageWriter imageWriter = new ImageWriter("TriangleLights", 500, 500);
//        camera2.setImageWriter(imageWriter) //
//                .setRayTracer(new SimpleRayTracer(scene2)) //
//                .renderImage(); //
//        camera2.writeToImage(); //
//    }
//
//    /** Produce a picture of a sphere lighted by a narrow spotlight */
//    /*
//     * @Test public void sphereSpotSharp() { scene1.geometries.add(sphere);
//     * scene1.lights.add(new SpotLight(sphereLightColor, sphereLightPosition, new
//     * Vector(1, 1, -0.5)).setNarrowBeam(10) .setKl(0.001).setKq(0.00004));
//     *
//     * ImageWriter imageWriter = new ImageWriter("lightSphereSpotSharp", 500, 500);
//     * camera1.setImageWriter(imageWriter) // .setRayTracer(new
//     * RayTracerBasic(scene1)) // .renderImage() // .writeToImage(); // }
//     */
//    /** Produce a picture of two triangles lighted by a narrow spotlight */
//    /*
//     * @Test public void trianglesSpotSharp() { scene2.geometries.add(triangle1,
//     * triangle2); scene2.lights.add(new SpotLight(trianglesLightColor,
//     * trianglesLightPosition, trianglesLightDirection)
//     * .setNarrowBeam(10).setKl(0.001).setKq(0.00004));
//     *
//     * ImageWriter imageWriter = new ImageWriter("lightTrianglesSpotSharp", 500,
//     * 500); camera2.setImageWriter(imageWriter) // .setRayTracer(new
//     * RayTracerBasic(scene2)) // .renderImage() // .writeToImage(); // }
//     */
//}

//package renderer;
//
//import static java.awt.Color.*;
//
//import org.junit.jupiter.api.Test;
//
//import geometries.*;
//import lighting.*;
//import primitives.*;
//import scene.Scene;
//
///**
// * Test rendering a basic image
// *
// * @author Dan
// */
//public class LightsTests {
//    /**
//     * First scene for some of the tests
//     */
//    private final Scene scene1 = new Scene("Test scene");
//    /**
//     * Second scene for some of the tests
//     */
//    private final Scene scene2 = new Scene("Test scene")
//            .setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));
//
//    /**
//     * First camera builder for some of the tests
//     */
//    private final Camera.Builder camera1 = Camera.getBuilder()
//            .setRayTracer(new SimpleRayTracer(scene1))
//            .setLocation(new Point(0, 0, 1000))
//            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
//            .setVpSize(150d, 150d).setVpDistance(1000d);
//    /**
//     * Second camera builder for some of the tests
//     */
//    private final Camera.Builder camera2 = Camera.getBuilder()
//            .setRayTracer(new SimpleRayTracer(scene2))
//            .setLocation(new Point(0, 0, 1000))
//            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
//            .setVpSize(200d, 200d).setVpDistance(1000d);
//
//    /**
//     * Shininess value for most of the geometries in the tests
//     */
//    private static final int SHININESS = 301;
//    /**
//     * Diffusion attenuation factor for some of the geometries in the tests
//     */
//    private static final double KD = 0.5;
//    /**
//     * Diffusion attenuation factor for some of the geometries in the tests
//     */
//    private static final Double3 KD3 = new Double3(0.2, 0.6, 0.4);
//
//    /**
//     * Specular attenuation factor for some of the geometries in the tests
//     */
//    private static final double KS = 0.5;
//    /**
//     * Specular attenuation factor for some of the geometries in the tests
//     */
//    private static final Double3 KS3 = new Double3(0.2, 0.4, 0.3);
//
//    /**
//     * Material for some of the geometries in the tests
//     */
//    private final Material material = new Material().setKd(KD3).setKs(KS3).setNShininess(SHININESS);
//    /**
//     * Light color for tests with triangles
//     */
//    private final Color trianglesLightColor = new Color(800, 500, 250);
//    /**
//     * Light color for tests with sphere
//     */
//    private final Color sphereLightColor = new Color(800, 500, 0);
//    /**
//     * Color of the sphere
//     */
//    private final Color sphereColor = new Color(BLUE).reduce(2);
//
//    /**
//     * Center of the sphere
//     */
//    private final Point sphereCenter = new Point(0, 0, -50);
//    /**
//     * Radius of the sphere
//     */
//    private static final double SPHERE_RADIUS = 50d;
//
//    /**
//     * The triangles' vertices for the tests with triangles
//     */
//    private final Point[] vertices =
//            {
//                    // the shared left-bottom:
//                    new Point(-110, -110, -150),
//                    // the shared right-top:
//                    new Point(95, 100, -150),
//                    // the right-bottom
//                    new Point(110, -110, -150),
//                    // the left-top
//                    new Point(-75, 78, 100)
//            };
//    /**
//     * Position of the light in tests with sphere
//     */
//    private final Point sphereLightPosition = new Point(-50, -50, 25);
//    /**
//     * Light direction (directional and spot) in tests with sphere
//     */
//    private final Vector sphereLightDirection = new Vector(1, 1, -0.5);
//    /**
//     * Position of the light in tests with triangles
//     */
//    private final Point trianglesLightPosition = new Point(30, 10, -100);
//    /**
//     * Light direction (directional and spot) in tests with triangles
//     */
//    private final Vector trianglesLightDirection = new Vector(-2, -2, -2);
//
//    /**
//     * The sphere in appropriate tests
//     */
//    private final Geometry sphere = new Sphere(SPHERE_RADIUS, sphereCenter)
//            .setEmission(sphereColor).setMaterial(new Material().setKd(KD).setKs(KS).setNShininess(SHININESS));
//    /**
//     * The first triangle in appropriate tests
//     */
//    private final Geometry triangle1 = new Triangle(vertices[0], vertices[1], vertices[2])
//            .setMaterial(material);
//    /**
//     * The first triangle in appropriate tests
//     */
//    private final Geometry triangle2 = new Triangle(vertices[0], vertices[1], vertices[3])
//            .setMaterial(material);
//
//    /**
//     * Produce a picture of a sphere lighted by a directional light
//     */
//    @Test
//    public void sphereDirectional() {
//        scene1.geometries.add(sphere);
//        scene1.lights.add(new DirectionalLight(sphereLightColor, sphereLightDirection));
//
//        camera1.setImageWriter(new ImageWriter("lightSphereDirectional", 500, 500))
//                .build()
//                .renderImage()
//                .writeToImage();
//    }
//
//    /**
//     * Produce a picture of a sphere lighted by a point light
//     */
//    @Test
//    public void spherePoint() {
//        scene1.geometries.add(sphere);
//        scene1.lights.add(new PointLight(sphereLightColor, sphereLightPosition)
//                .setkL(0.001).setkQ(0.0002));
//
//        camera1.setImageWriter(new ImageWriter("lightSpherePoint", 500, 500))
//                .build()
//                .renderImage()
//                .writeToImage();
//    }
//
//    /**
//     * Produce a picture of a sphere lighted by a spotlight
//     */
//    @Test
//    public void sphereSpot() {
//        scene1.geometries.add(sphere);
//        scene1.lights.add(new SpotLight(sphereLightColor, sphereLightPosition, sphereLightDirection)
//                .setkL(0.001).setkQ(0.0001));
//
//        camera1.setImageWriter(new ImageWriter("lightSphereSpot", 500, 500))
//                .build()
//                .renderImage()
//                .writeToImage();
//    }
//
//    /**
//     * Produce a picture of two triangles lighted by a directional light
//     */
//    @Test
//    public void trianglesDirectional() {
//        scene2.geometries.add(triangle1, triangle2);
//        scene2.lights.add(new DirectionalLight(trianglesLightColor, trianglesLightDirection));
//
//        camera2.setImageWriter(new ImageWriter("lightTrianglesDirectional", 500, 500)) //
//                .build()
//                .renderImage()
//                .writeToImage();
//    }
//
//    /**
//     * Produce a picture of two triangles lighted by a point light
//     */
//    @Test
//    public void trianglesPoint() {
//        scene2.geometries.add(triangle1, triangle2);
//        scene2.lights.add(new PointLight(trianglesLightColor, trianglesLightPosition)
//                .setkL(0.001).setkQ(0.0002));
//
//        camera2.setImageWriter(new ImageWriter("lightTrianglesPoint", 500, 500)) //
//                .build() //
//                .renderImage() //
//                .writeToImage(); //
//    }
//
//    /**
//     * Produce a picture of two triangles lighted by a spotlight
//     */
//    @Test
//    public void trianglesSpot() {
//        scene2.geometries.add(triangle1, triangle2);
//        scene2.lights.add(new SpotLight(trianglesLightColor, trianglesLightPosition, trianglesLightDirection)
//                .setkL(0.001).setkQ(0.0001));
//
//        camera2.setImageWriter(new ImageWriter("lightTrianglesSpot", 500, 500))
//                .build()
//                .renderImage()
//                .writeToImage();
//    }
//
//    /**
//     * Produce a picture of a sphere lighted by a narrow spotlight
//     */
//    @Test
//    public void sphereSpotSharp() {
//        scene1.geometries.add(sphere);
//        scene1.lights
//                .add(new SpotLight(sphereLightColor, sphereLightPosition, new Vector(1, 1, -0.5))
//                        .setkL(0.001).setkQ(0.00004));
//        //.setNarrowBeam(10));
//
//        camera1.setImageWriter(new ImageWriter("lightSphereSpotSharp", 500, 500))
//                .build()
//                .renderImage()
//                .writeToImage();
//    }
//
//    /**
//     * Produce a picture of two triangles lighted by a narrow spotlight
//     */
//    @Test
//    public void trianglesSpotSharp() {
//        scene2.geometries.add(triangle1, triangle2);
//        scene2.lights.add(new SpotLight(trianglesLightColor, trianglesLightPosition, trianglesLightDirection)
//                .setkL(0.001).setkQ(0.00004));
//        //.setNarrowBeam(10));
//
//        camera2.setImageWriter(new ImageWriter("lightTrianglesSpotSharp", 500, 500))
//                .build()
//                .renderImage()
//                .writeToImage();
//    }
//
//}


