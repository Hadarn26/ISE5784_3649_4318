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

public class angleTests {
    private final Scene scene = new Scene("Test scene");
    /**
     * Camera builder for the tests with triangles
     */
    private double y=1000;
    private final Camera.Builder cameraBuilder = Camera.getBuilder()
           // .setDirection(new Vector( -3000,y,-2500),new Vector(y/3000,1,0))
           // .setDirection( new Vector(-20,-30,-30),new Vector(-30, 60, -40))
            .setDirection( new Vector(-0.8,-0.1,-1),new Vector((-0.1/0.8)/2, 1, (-0.1/1)/2))
         //   .setDirection( new Vector(0,1,-1),new Vector(0, 1, 1))
            .setRayTracer(new SimpleRayTracer(scene));
    @Test
    public void cameraChangeLocationTest() {
        scene.geometries.add(
                new Plane(new Point(-3000,0,0),new Vector(1,0,0)).setEmission(new Color(RED)),
                new Plane(new Point(0,-1500,0),new Vector(0,1,0)).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setNShininess(100)),
                new Plane(new Point(0,0,-2500),new Vector(0,0,1)).setEmission(new Color(YELLOW))
                        .setMaterial(new Material().setNShininess(00).setKR(1)),
                new Sphere(50d,new Point(-2500,-1000,-2000))
                        .setMaterial(new Material().setKT(0.7).setNShininess(100))
        );
         // cameraBuilder.setLocation(new Point(1500, 1800, 2000)).setVpDistance(1000d)
        scene.setAmbientLight(new AmbientLight(new Color(WHITE),new Double3(0.1)));
        scene.lights.add(new PointLight(new Color(GREEN),new Point(-2500,-1000,-2000)));
        scene.lights.add(new SpotLight(new Color(GREEN),new Point(-2500,-1000,-2000),new Vector(1,-1,0)));
       cameraBuilder.setLocation(new Point(-10, -1000, 1000)).setVpDistance(2000d)
                .setVpSize((double) 400, (double) 600)
                // .setAntiAliasingFactor(25)
                .setImageWriter(new ImageWriter("angleCameraTest", 600, 400))
                .build()
                .renderImage()
                .writeToImage();
    }
    private Scene scene2 = new Scene("Test scene");
    Material material =new Material();
    Color silver=new Color(26,22,22);
    Color starGold=new Color(10,161,216);
    Color starGold2=new Color(36,181,233);

    public Camera.Builder camera = new Camera.Builder().setLocation(new Point(1000, 0, 520)).setDirection( new Vector(-1, 0, -0.5),
            new Vector(-1, 0, 2)) //
            .setVpSize(170d, 170d).setVpDistance(1000d);
    public Geometry lightSphere1=new Sphere(7.5,new Point(3, -4, 40))
            .setMaterial(new Material().setKd(0.8).setKs(0.2).setNShininess(40)).setEmission(silver);
    public Geometry lightSphere2=new Sphere(4,new Point(19, 12, 82))
            .setMaterial(new Material().setKd(0.8).setKs(0.2).setNShininess(40)).setEmission(silver);
    public Geometry lightSphere3=new Sphere(4.5,new Point(34,32,78))
            .setMaterial(new Material().setKd(0.8).setKs(0.2).setNShininess(40)).setEmission(silver);
    public Geometry lightSphere4=new Sphere(5,new Point(42,43,61))
            .setMaterial(new Material().setKd(0.8).setKs(0.2).setNShininess(40)).setEmission(silver);
    public Geometry lightSphere5=new Sphere(5.5,new Point(40,43,38))
            .setMaterial(new Material().setKd(0.8).setKs(0.2).setNShininess(40)).setEmission(silver);
    public Geometry lightSphere6=new Sphere(6,new Point(33,35,24))
            .setMaterial(new Material().setKd(0.8).setKs(0.2).setNShininess(40)).setEmission(silver);
    public Geometry lightSphere7=new Sphere(6.5,new Point(20,19,19))
            .setMaterial(new Material().setKd(0.8).setKs(0.2).setNShininess(40)).setEmission(silver);
    public Geometry lightSphere8=new Sphere(7,new Point(10,6,24))
            .setMaterial(new Material().setKd(0.8).setKs(0.2).setNShininess(40)).setEmission(silver);
    public Geometry lightSphere9=new Sphere(3.5,new Point(5, -4, 65))
            .setMaterial(new Material().setKd(0.8).setKs(0.2).setNShininess(40)).setEmission(silver);
    public Geometry bigSphere=new Sphere(20,new Point(17.1315, 18.19544, 50))
            .setEmission(new Color(104,3,3)) //
            .setMaterial(new Material().setKd(0.25).setKs(0.7).setKR(1).setNShininess(200));
    public Geometry floor=new Polygon(new Point(0,-90,0),new Point(0,90,0),
            new Point(150,90,0),new Point(150,-90,0))
            .setMaterial(new Material().setKd(0.25).setKs(0.9).setNShininess(200));

    public Geometry wall=new Polygon(new Point(-100, -80, 80),new Point(-100, 80, 80),
            new Point(-80, 90, -10),new Point(-80, -90, -10))
            .setMaterial(new Material().setKd(0.25).setKs(1).setNShininess(200).setKR(1).setKs(10));
    public Geometry tri1=new Triangle(new Point(70,-45,0),new Point(25,-25,0),
            new Point(50,-45,55)).
            setEmission(new Color(71,20,105)).
            setMaterial(new Material().setKs(0.8).setKT(0.8).setKd(0.2).setNShininess(20));
    public Geometry tri2=new Triangle(new Point(70,-45,0),new Point(50,-45,55),
            new Point(25, -65, 0)).
            setEmission(new Color(71,20,105)).
            setMaterial(new Material().setKs(0.8).setKT(0.8).setKd(0.2).setKd(130).setNShininess(20));
    public Geometry tri3=new Triangle(new Point(25, -25, 0),new Point(50,-45,55),
            new Point(25, -65, 0)).
            setEmission(new Color(71,20,105)).
            setMaterial(new Material().setKs(0.8).setKT(0.8).setKd(0.2).setNShininess(20));
    public Geometry cyl=new Cylinder(5,new Ray(new Point(42, -45, 0),new Vector(0,0,1)),30).
            setEmission(new Color(164,230,88)).
            setMaterial(new Material().setKs(0.3).setKd(0.7).setNShininess(20));
    public Geometry triStar1=new Triangle(new Point(-35, -90, 55),new Point(-35, -50, 55),
            new Point(-42, -70, 80)).
            setEmission(starGold).
            setMaterial(new Material().setKs(0.2).setKd(0.8).setNShininess(100));
    public Geometry triStar11=new Triangle(new Point(-35, -50, 55),new Point(-62, -68, 50),
            new Point(-42, -70, 80)).
            setEmission(starGold).
            setMaterial(new Material().setKs(0.2).setKd(0.8).setNShininess(100));
    public Geometry triStar12=new Triangle(new Point(-35, -90, 55),new Point(-62, -68, 50),
            new Point(-42, -70, 80)).
            setEmission(starGold).
            setMaterial(new Material().setKs(0.2).setKd(0.8).setNShininess(100));
    public Geometry triStar13=new Triangle(new Point(-35, -90, 55),new Point(-62, -68, 50),
            new Point(-35, -50, 55)).
            setEmission(starGold).
            setMaterial(new Material().setKs(0.2).setKd(0.8).setNShininess(100));
    public Geometry tristar2=new Triangle(new Point(-20, -70, 70),new Point(-51, -52, 70),
            new Point(-42, -70, 40)).
            setEmission(starGold2).
            setMaterial(new Material().setKs(0.2).setKd(0.8).setNShininess(100));
    public Geometry tristar21=new Triangle(new Point(-51, -86, 70),new Point(-51, -52, 70),
            new Point(-42, -70, 40)).
            setEmission(starGold2).
            setMaterial(new Material().setKs(0.2).setKd(0.8).setNShininess(100));
    public Geometry tristar22=new Triangle(new Point(-51, -86, 70),new Point(-20, -70, 70),
            new Point(-42, -70, 40)).
            setEmission(starGold2).
            setMaterial(new Material().setKs(0.2).setKd(0.8).setNShininess(100));
    public Geometry tristar23=new Triangle(new Point(-51, -86, 70),new Point(-20, -70, 70),
            new Point(-51, -52, 70)).
            setEmission(starGold2).
            setMaterial(new Material().setKs(0.2).setKd(0.8).setNShininess(100));
    public Geometry triStarR1=new Triangle(new Point(-35, 40, 55),new Point(-35, 80, 55),
            new Point(-42, 60, 80)).
            setEmission(starGold).
            setMaterial(new Material().setKs(0.2).setKd(0.8).setNShininess(100));
    public Geometry triStarR11=new Triangle(new Point(-35, 80, 55),new Point(-62, 62, 50),
            new Point(-42, 60, 80)).
            setEmission(starGold).
            setMaterial(new Material().setKs(0.2).setKd(0.8).setNShininess(100));
    public Geometry triStarR12=new Triangle(new Point(-35, 40, 55),new Point(-62, 62, 50),
            new Point(-42, 60, 80)).
            setEmission(starGold).
            setMaterial(new Material().setKs(0.2).setKd(0.8).setNShininess(100));
    public Geometry triStarR13=new Triangle(new Point(-35, 40, 55),new Point(-62, 62, 50),
            new Point(-35, 80, 55)).
            setEmission(starGold).
            setMaterial(new Material().setKs(0.2).setKd(0.8).setNShininess(100));
    public Geometry tristarR2=new Triangle(new Point(-20, 60, 70),new Point(-51, 78, 70),
            new Point(-42, 60, 40)).
            setEmission(starGold2).
            setMaterial(new Material().setKs(0.2).setKd(0.8).setNShininess(100));
    public Geometry tristarR21=new Triangle(new Point(-51, 44, 70),new Point(-51, 78, 70),
            new Point(-42, 60, 40)).
            setEmission(starGold2).
            setMaterial(new Material().setKs(0.2).setKd(0.8).setNShininess(100));
    public Geometry tristarR22=new Triangle(new Point(-51, 44, 70),new Point(-20, 60, 70),
            new Point(-42, 60, 40)).
            setEmission(starGold2).
            setMaterial(new Material().setKs(0.2).setKd(0.8).setNShininess(100));
    public Geometry tristarR23=new Triangle(new Point(-51, 44, 70),new Point(-20, 60, 70),
            new Point(-51, 78, 70)).
            setEmission(starGold2).
            setMaterial(new Material().setKs(0.2).setKd(0.8).setNShininess(100));
    /*  public Geometry tube=new Tube(5,new Ray(new Point(33,47,54),new Vector(-117,-153,104))).
              setEmission(new Color(239,33,74)).
              setMaterial(new Material().setKd(0.7).setKs(0.3).setShininess(20));*/
    @Test
    public void finalImage(){
        scene.geometries.add(floor,wall,bigSphere,tri1,tri2,tri3,cyl,lightSphere1,lightSphere2,lightSphere3,
                lightSphere4,lightSphere5,lightSphere6,lightSphere7,lightSphere8, lightSphere9,triStar1,
                triStar11,triStar12,triStar13,tristar2,tristar21,tristar22,tristar23,triStarR1,
                triStarR11,triStarR12,triStarR13,tristarR2,tristarR21,tristarR22,tristarR23);
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
        scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(40, 40, 300), new Vector(-1, -1, -4)) //
                .setkL(0.003).setkQ(2E-5));
        scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(40, 25, 50), new Vector(-3, -1, 0))
                .setNarrowBeam(50) //
                .setkL(0.03).setkQ(0.007));
        scene.lights.add(
                new PointLight(new Color(56, 230, 50),new Point(-10,70,-11))
                        .setkL(0.0003).setkQ(2E-5));
        scene.lights.add(
                new PointLight(new Color(143, 234, 247),new Point(100,20,11))
                        .setkL(0.0003).setkQ(2E-5));


//        scene.lights.add(new SpotLight(new Color(33, 239, 47),new Point(450,300,40),
//                new Vector(-114,-53,18)).setKl(0.004).setKq(0.00006));

        camera.setImageWriter(new ImageWriter("temp", 1200, 1200)) //
                .setRayTracer(new SimpleRayTracer(scene))
                .build()
                .renderImage() //
                .writeToImage();

    }
}
