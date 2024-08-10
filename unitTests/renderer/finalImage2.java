package renderer;

import geometries.Geometries;
import geometries.Polygon;
import geometries.Sphere;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import static java.awt.Color.WHITE;

public class finalImage2 {
    private final Scene scene = new Scene("Test scene");

    private Camera.Builder camera = Camera.getBuilder().setLocation(new Point(0, 0, 1000))
            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0)) //
            .setVpSize(200d, 300d).setVpDistance(1000d) //
            .setRayTracer(new SimpleRayTracer(scene));

    @Test
    public void renderFinalImage() {
        createLights();
        createRoom();
        createTable();
        createFruitBowl();
        createTV();
        createAquarium();
        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), new Double3(0.1)));
        camera.setImageWriter(new ImageWriter("mp2ImageOption2", 750, 500))//
                .setRayTracer(new SimpleRayTracer(scene))
                .build()//
                .renderImage() //
                .writeToImage();
    }

    private void createAquarium() {
        //aquarium with water
        //fish1
        //fish2
    }

    private void createTV() {
        //            sp(x,y,z)   ------------- sp(x+width,y,z)
        //                        |  front    |
        //                        |   view    |
        //      sp(x,y-height,z)  -------------sp(x+width,y-height,z+size)
        Point startPoint = new Point(-50, 30, -380);
        double width = 100;
        double height = 0.5 * width,borderLine=4;

        scene.geometries.add(
                new Polygon(startPoint, startPoint.add(new Vector(width, 0, 0)), startPoint.add(new Vector(width, -height, 0))
                        , startPoint.add(new Vector(0, -height, 0))).setEmission(Color.BLACK)
                        .setMaterial(new Material().setNShininess(300).setKs(0.5).setKd(0.1)),
                new Polygon(startPoint.add(new Vector(borderLine,-borderLine,1)),
                        startPoint.add(new Vector(width-borderLine, -borderLine, 1)),
                        startPoint.add(new Vector(width-borderLine, -height+borderLine, 1)),
                        startPoint.add(new Vector(borderLine, -height+borderLine, 1)))
                        .setEmission(new Color(20, 20, 20))
                        .setMaterial(new Material().setNShininess(100).setKR(0.05).setKd(0.001))
        );
    }

    private void createFruitBowl() {
        //create the bowl
        // apples
        // pears
        // grapes
        //    oranges
    }

    private void createLeg(double sizeForWidth, Point startPoint/*=sp(x,y,z)*/, double length) {
        //            sp(x,y,z)   ------------- sp(x+size,y,z)
        //                        |   up      |
        //                        |   view    |
        //        sp(x,y,z+size)  -------------sp(x+size,y,z+size)

        //     sp(x,y-length,z)   ------------- sp(x+size,y-length,z)
        //                        |   down    |
        //                        |   view    |
        // sp(x,y-length,z+size)  -------------sp(x+size,y-length,z+size)
        Color legColor = new Color(58, 6, 3);
        double ks = 0.1;
        double kd = 0.3;
        Point p1 = startPoint;
        Point p2 = new Point(startPoint.getX(), startPoint.getY(), startPoint.getZ() + sizeForWidth);
        Point p3 = new Point(startPoint.getX(), startPoint.getY() - length, startPoint.getZ() + sizeForWidth);
        Point p4 = new Point(startPoint.getX(), startPoint.getY() - length, startPoint.getZ());
        Polygon left = new Polygon(p1, p2, p3, p4);
        p2 = new Point(startPoint.getX(), startPoint.getY() - length, startPoint.getZ());
        p3 = new Point(startPoint.getX() + sizeForWidth, startPoint.getY() - length, startPoint.getZ());
        p4 = new Point(startPoint.getX() + sizeForWidth, startPoint.getY(), startPoint.getZ());
        Polygon back = new Polygon(p1, p2, p3, p4);
        p1 = p4;
        p2 = new Point(startPoint.getX() + sizeForWidth, startPoint.getY(), startPoint.getZ() + sizeForWidth);
        p3 = new Point(startPoint.getX() + sizeForWidth, startPoint.getY() - length, startPoint.getZ() + sizeForWidth);
        p4 = new Point(startPoint.getX() + sizeForWidth, startPoint.getY() - length, startPoint.getZ());
        Polygon right = new Polygon(p1, p2, p3, p4);
        p1 = new Point(startPoint.getX(), startPoint.getY(), startPoint.getZ() + sizeForWidth);
        p2 = new Point(startPoint.getX() + sizeForWidth, startPoint.getY(), startPoint.getZ() + sizeForWidth);
        p3 = new Point(startPoint.getX() + sizeForWidth, startPoint.getY() - length, startPoint.getZ() + sizeForWidth);
        p4 = new Point(startPoint.getX(), startPoint.getY() - length, startPoint.getZ() + sizeForWidth);
        Polygon front = new Polygon(p1, p2, p3, p4);

        scene.geometries.add(
                left.setEmission(legColor).setMaterial(new Material().setKd(kd).setKs(ks).setNShininess(50)),
                right.setEmission(legColor).setMaterial(new Material().setKd(kd).setKs(ks).setNShininess(50)),
                front.setEmission(legColor).setMaterial(new Material().setKd(kd).setKs(ks).setNShininess(50))
                , back.setEmission(legColor).setMaterial(new Material().setKd(kd).setKs(ks).setNShininess(50)));

    }

    private void createRoom() {
        scene.geometries.add( //
                //left wall
                new Polygon(new Point(-110, 70, 300), new Point(-110, -70, 300), new Point(-110, -70, -400), new Point(-110, 70, -400)).setEmission(new Color(195, 195, 195).reduce(2)) //
                        .setMaterial(new Material().setKd(0.7).setKs(0.1)),
                //ceiling
                new Polygon(new Point(110, 70, 300), new Point(-110, 70, 300), new Point(-110, 70, -400), new Point(110, 70, -400)).setEmission(new Color(115, 115, 115).reduce(3)) //
                        .setMaterial(new Material().setKd(0.7).setKs(0.1).setNShininess(100)),
                //right wall
                new Polygon(new Point(110, -70, 300), new Point(110, 70, 300), new Point(110, 70, -400), new Point(110, -70, -400)).setEmission(new Color(195, 195, 195).reduce(2)) //
                        .setMaterial(new Material().setKd(0.7).setKs(0.1).setNShininess(50)),
                // floor
                new Polygon(new Point(-110, -70, 300), new Point(110, -70, 300), new Point(110, -70, -400), new Point(-110, -70, -400)).setEmission(new Color(255, 236, 229).reduce(3)) //
                        .setMaterial(new Material().setKd(0.7).setKs(0.3).setNShininess(100)),
                // back wall
                new Polygon(new Point(110, 70, -400), new Point(-110, 70, -400), new Point(-110, -70, -400), new Point(110, -70, -400)).setEmission(new Color(195, 195, 195).reduce(2.5)) //
                        .setMaterial(new Material().setKs(0.3).setNShininess(100))

        );
    }

    private void createLights() {

        scene.setBackGround(new Color(WHITE));

        Point p1 = new Point(-20, 30, 60);
        Point p2 = new Point(20, 30, 300);

        scene.lights.add(new SpotLight(new Color(255, 100, 100).reduce(3), p1, new Vector(0, -1, 0.25)));
        scene.lights.add(new PointLight(new Color(255, 100, 100).reduce(3), p1));

        scene.lights.add(new SpotLight(new Color(255, 100, 100).reduce(3), p2, new Vector(0, -1, -0.25)));
        scene.lights.add(new PointLight(new Color(255, 100, 100).reduce(3), p2));

        scene.lights.add(new DirectionalLight(new Color(255, 100, 100).reduce(6), new Vector(0, -0.3, -1)));


        scene.geometries.add(new Sphere(7, p1).setEmission(new Color(255, 100, 100))
                .setMaterial(new Material().setKT(0.8).setNShininess(100)));

        scene.geometries.add(new Sphere(7, p2).setEmission(new Color(255, 100, 100))
                .setMaterial(new Material().setKT(0.8).setNShininess(100)));

        //   scene.geometries.add(new Cylinder(new Ray(p1.add(new Vector(0, 7, 0)), new Vector(0, 1, 0)), 1, 50).setEmission(new Color(BLACK))
        //           .setMaterial(new Material().setKs(1).setKd(0.4).setShininess(100)));

        //   scene.geometries.add(new Cylinder(new Ray(p2.add(new Vector(0, 7, 0)), new Vector(0, 1, 0)), 1, 50).setEmission(new Color(BLACK))
        //          .setMaterial(new Material().setKs(1).setKd(0.4).setShininess(100)));


    }

    private void createTable() {
        Point up1 = new Point(-50, -20, 600);
        Point up2 = new Point(-50, -20, 60);
        Point up3 = new Point(50, -20, 60);
        Point up4 = new Point(50, -20, 600);

        Point down1 = new Point(-50, -23, 600);
        Point down2 = new Point(-50, -23, 60);
        Point down3 = new Point(50, -23, 60);
        Point down4 = new Point(50, -23, 600);

        Color tableColor = new Color(115, 64, 20);

        scene.geometries.add(
                new Polygon(up1, up2, up3, up4).setEmission(tableColor)//
                        .setMaterial(new Material().setKd(0.7).setKs(0.3)),
                new Polygon(down1, down2, down3, down4).setEmission(tableColor)
                        .setMaterial(new Material().setKd(0.7).setKs(0.3)),
                new Polygon(up1, up2, down2, down1).setEmission(tableColor)
                        .setMaterial(new Material().setKd(0.7).setKs(0.3)),
                new Polygon(up3, up4, down4, down3).setEmission(tableColor)
                        .setMaterial(new Material().setKd(0.7).setKs(0.3)),
                new Polygon(up1, down1, down4, up4).setEmission(tableColor)
                        .setMaterial(new Material().setKd(0.7).setKs(0.3)),
                new Polygon(up2, down2, down3, up3).setEmission(tableColor)
                        .setMaterial(new Material().setKd(0.7).setKs(0.3))

        );
        double widthLeg = 3, lengthLeg = 40, spaceBorder = 0.5;
        createLeg(widthLeg, new Point(up2.getX() + spaceBorder, up2.getY(), up2.getZ() + spaceBorder), lengthLeg);
        createLeg(widthLeg, new Point(up4.getX() - spaceBorder - widthLeg, up4.getY(), up4.getZ() - widthLeg - spaceBorder), lengthLeg);
        createLeg(widthLeg, new Point(up3.getX() - widthLeg - spaceBorder, up3.getY(), up3.getZ() + spaceBorder), lengthLeg);
        createLeg(widthLeg, new Point(up1.getX() + spaceBorder, up1.getY(), up1.getZ() - widthLeg - spaceBorder), lengthLeg);


    }


}
