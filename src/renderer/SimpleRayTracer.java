/////////////////////////////////////////////



package renderer;

import geometries.Geometry;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import geometries.Intersectable.GeoPoint;

/**
 * The SimpleRayTracer class extends RayTracerBase and provides a basic implementation
 * for tracing rays in a scene. It calculates the color of the closest intersection point
 * or returns the background color if no intersections are found.
 */
public class SimpleRayTracer extends  RayTracerBase {

    /**
     * Constructs a SimpleRayTracer with the specified scene.
     *
     * @param scene the scene to be rendered.
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }
    private static final double DELTA = 0.1;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final int NUM_SAMPLES = 3;


    @Override
    public Color traceRay(Ray ray) {
       // return traceRay(ray, NUM_SAMPLES);
        GeoPoint intersectionPoint = scene.geometries.findClosestIntersection(ray);
        return intersectionPoint == null
                ? scene.backGround
                : calcColor(intersectionPoint, ray);
        //findGeoIntersections
    }

    @Override
    public Color traceRays(List<Ray> rays) {

        Color color = Color.BLACK;
        for (Ray ray : rays) {
            color = color.add(traceRay(ray));
        }
        return color.reduce(rays.size());

    }

    /**
     * Performs adaptive super-sampling for a given pixel.
     *
     * @param centerP     The center point of the pixel.
     * @param Width       The width of the pixel.
     * @param Height      The height of the pixel.
     * @param minWidth    The minimum width of a sub-pixel for further sampling.
     * @param minHeight   The minimum height of a sub-pixel for further sampling.
     * @param cameraLoc   The location of the camera.
     * @param Vright      The vector representing the right direction.
     * @param Vup         The vector representing the up direction.
     * @param prePoints   A list of pre-sampled points to avoid redundancy.
     * @return The color computed for the pixel through adaptive super-sampling.
     */
    @Override
    public Color AdaptiveSuperSamplingRec(Point centerP, double Width, double Height, double minWidth, double minHeight, Point cameraLoc, Vector Vright, Vector Vup, List<Point> prePoints) {
        if (Width < minWidth * 2 || Height < minHeight * 2) {
            // If the pixel is smaller than the minimum size, trace a ray through the pixel and return the color.
            return this.traceRay(new Ray(cameraLoc, centerP.subtract(cameraLoc)));
        }

        List<Point> nextCenterPList = new LinkedList<>();
        List<Point> cornersList = new LinkedList<>();
        List<primitives.Color> colorList = new LinkedList<>();
        Point tempCorner;
        Ray tempRay;
        // Iterate over the corners of the pixel and perform sub-sampling
        for (int i = -1; i <= 1; i += 2) {
            for (int j = -1; j <= 1; j += 2) {
                tempCorner = centerP.add(Vright.scale(i * Width / 2)).add(Vup.scale(j * Height / 2));
                cornersList.add(tempCorner);
                // Check if the sub-pixel's corner is already sampled
                if (prePoints == null || !isInList(prePoints, tempCorner)) {
                    tempRay = new Ray(cameraLoc, tempCorner.subtract(cameraLoc));
                    nextCenterPList.add(centerP.add(Vright.scale(i * Width / 4)).add(Vup.scale(j * Height / 4)));
                    colorList.add(traceRay(tempRay));
                }
            }
        }


        if (nextCenterPList == null || nextCenterPList.size() == 0) {
            // If no valid sub-pixels were found, return black color.
            return primitives.Color.BLACK;
        }

        boolean isAllEquals = true;
        primitives.Color tempColor = colorList.get(0);
        // Check if all colors in the colorList are almost equal
        for (primitives.Color color : colorList) {
            if (!tempColor.isAlmostEquals(color))
                isAllEquals = false;
        }
        if (isAllEquals && colorList.size() > 1)
            // If all colors are equal and there is more than one color, return the first color.
            return tempColor;


        tempColor = primitives.Color.BLACK;
        // Recursively perform adaptive super-sampling on sub-pixels
        for (Point center : nextCenterPList) {
            tempColor = tempColor.add(AdaptiveSuperSamplingRec(center, Width / 2, Height / 2, minWidth, minHeight, cameraLoc, Vright, Vup, cornersList));
        }
        // Reduce the color by dividing by the number of sub-pixels
        return tempColor.reduce(nextCenterPList.size());
    }

    /**
     * Performs regular super-sampling for a given pixel.
     *
     * @param centerP     The center point of the pixel.
     * @param Width       The width of the pixel.
     * @param Height      The height of the pixel.
     * @param minWidth    The minimum width of a sub-pixel for further sampling.
     * @param minHeight   The minimum height of a sub-pixel for further sampling.
     * @param cameraLoc   The location of the camera.
     * @param Right       The vector representing the right direction.
     * @param Vup         The vector representing the up direction.
     * @param prePoints   A list of pre-sampled points to avoid redundancy.
     * @return The color computed for the pixel through regular super-sampling.
     */
    @Override
    public Color RegularSuperSampling(Point centerP, double Width, double Height, double minWidth, double minHeight, Point cameraLoc, Vector Right, Vector Vup, List<Point> prePoints) {
        List<Color> colorList = new ArrayList<>();

        int numSubPixelsX = (int) Math.ceil(Width / minWidth);
        int numSubPixelsY = (int) Math.ceil(Height / minHeight);

        Random random = new Random();
        // Iterate over sub-pixels and perform regular super-sampling
        for (int i = 0; i < numSubPixelsY; i++) {
            for (int j = 0; j < numSubPixelsX; j++) {
                double offsetX = minWidth * j;
                double offsetY = minHeight * i;

                double randomX = offsetX + random.nextDouble() * minWidth;
                double randomY = offsetY + random.nextDouble() * minHeight;

                Point subPixelPoint = centerP.add(Right.scale(randomX - Width / 2)).add(Vup.scale(randomY - Height / 2));

                // Check if the sub-pixel's point is already sampled
                if (prePoints == null || !isInList(prePoints, subPixelPoint)) {
                    Ray ray = new Ray(cameraLoc, subPixelPoint.subtract(cameraLoc));
                    colorList.add(traceRay(ray));
                }
            }
        }

        if (colorList.isEmpty()) {
            // If no valid sub-pixels were found, return black color.
            return primitives.Color.BLACK;
        }

        Color averageColor = Color.BLACK;
        // Calculate the average color by adding all colors in the colorList
        for (Color color : colorList) {
            averageColor = averageColor.add(color);
        }
        // Reduce the color by dividing by the number of sub-pixels
        return averageColor.reduce(colorList.size());
    }
    public Color traceRay(Ray ray, int numSamples) {
        Color color = Color.BLACK;

        TargetArea targetArea=new TargetArea(ray,200);
        // Generate multiple rays for super sampling
        List<Ray> rays = targetArea.constructRayBeamGrid();

        for (Ray sampleRay : rays) {
            GeoPoint intersectionPoint = scene.geometries.findClosestIntersection(sampleRay);
               if (intersectionPoint != null) {
                color = color.add(calcColor(intersectionPoint, sampleRay));
            }
             else {
                color = color.add(scene.backGround);
            }

        }

        return color.reduce(rays.size());
    }

    /**
     * Find a point in the list
     *
     * @param pointsList the list
     * @param point      the point that we look for
     */
    private boolean isInList(List<Point> pointsList, Point point) {
        for (Point tempPoint : pointsList) {
            if (point.equals(tempPoint))
                return true;
        }
        return false;
    }


    /**
     * Calculates the color at a given point.
     * In this simple implementation, it returns the ambient light intensity of the scene.
     *
     * @param intersection the point at which to calculate the color.
     * @return the color at the given point.
     */
    private Color calcColor(GeoPoint intersection,Ray ray)
    {
//        Color color = Color.BLACK;
//
//        TargetArea targetArea=new TargetArea(ray,NUM_SAMPLES);
//        List<Ray> rays = targetArea.constructRayBeamGrid();
//        for (Ray sampleRay : rays) {
//            GeoPoint intersectionPoint = scene.geometries.findClosestIntersection(sampleRay);
//            if (intersectionPoint != null) {
//                color = scene.ambientLight.getIntensity()
//                        .add(color.add(calcColor(intersection, ray, MAX_CALC_COLOR_LEVEL, Double3.ONE)));
//            }
//            else {
//                color = color.add(scene.backGround);
//            }
//
//        }
//
//        return color.reduce(rays.size());
    // return antiAlising(intersection,ray);
       return scene.ambientLight.getIntensity()
               .add(calcColor(intersection, ray, MAX_CALC_COLOR_LEVEL, Double3.ONE));
    }

    private Color antiAlising(GeoPoint intersection,Ray ray){
        Color color = Color.BLACK;

        TargetArea targetArea=new TargetArea(ray,0.35);
        List<Ray> rays = targetArea.constructRayBeamGrid();
        for (Ray sampleRay : rays) {
            GeoPoint intersectionPoint = scene.geometries.findClosestIntersection(sampleRay);
            if (intersectionPoint != null) {
                color = color.add(calcColor(intersection, sampleRay, MAX_CALC_COLOR_LEVEL, Double3.ONE));
//                color = scene.ambientLight.getIntensity()
//                        .add(color.add(calcColor(intersection, ray, MAX_CALC_COLOR_LEVEL, Double3.ONE)));
            }
            else {
                color = color.add(scene.backGround);
//                color = scene.ambientLight.getIntensity()
//                        .add(color.add(scene.backGround));
            }

        }

        return scene.ambientLight.getIntensity().add(color.reduce(rays.size()));
    }

    private Color antiAlising(GeoPoint gp, Ray ray,int level,Double3 k){
        Color color = Color.BLACK;

        TargetArea targetArea=new TargetArea(ray,NUM_SAMPLES);
        List<Ray> rays = targetArea.constructRayBeamGrid();
        for (Ray sampleRay : rays) {
            GeoPoint intersectionPoint = scene.geometries.findClosestIntersection(sampleRay);
            if (intersectionPoint != null) {
                color = color.add(calcColor(intersectionPoint, ray, MAX_CALC_COLOR_LEVEL, Double3.ONE));
//                color = scene.ambientLight.getIntensity()
//                        .add(color.add(calcColor(intersection, ray, MAX_CALC_COLOR_LEVEL, Double3.ONE)));
            }
            else {
                color = color.add(scene.backGround);
//                color = scene.ambientLight.getIntensity()
//                        .add(color.add(scene.backGround));
            }

        }

        return scene.ambientLight.getIntensity().add(color.reduce(rays.size()));
    }


    private Color calcColor(GeoPoint gp, Ray ray,int level,Double3 k){
        Color color=calcLocalEffects(gp, ray,k);

        return 1==level?color:color.add(calcGlobalEffects(gp,ray,level,k));

    }

    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Material material=gp.geometry.getMaterial();
        return calcGlobalEffect(constructRefractedRay(gp,ray),material.kT,level,k)
                .add(calcGlobalEffect(constructReflectedRay(gp,ray),material.kR,level,k));
    }

    private Ray constructReflectedRay(GeoPoint gp, Ray ray) {
        Vector normal=gp.geometry.getNormal(gp.point);
        return new Ray( gp.point, ray.getDirection().subtract(normal.scale(2*ray.getDirection().dotProduct(normal))), normal);
    }

    private Ray constructRefractedRay(GeoPoint gp, Ray ray) {
        Vector normal=gp.geometry.getNormal(gp.point);
        return new Ray( gp.point, ray.getDirection(), normal);
    }

    private Color calcGlobalEffect( Ray ray,Double3 kX, int level, Double3 k) {
        Double3 kKx=kX.product(k);
//        double nv= Util.alignZero(n.dotProduct(v));
//        if (nv==0)
//            return color;
        if(kKx.lowerThan(MIN_CALC_COLOR_K))
            return Color.BLACK;
        GeoPoint gp=scene.geometries.findClosestIntersection(ray);
//        if(gp==null)
//            return Color.BLACK;//scene.background.scale(kx)

        return (gp==null?Color.BLACK:calcColor(gp,ray,level-1,kKx)).scale(kX);
    }


    private Color calcLocalEffects(GeoPoint gp, Ray ray,Double3 k){
        Color color = gp.geometry.getEmission();
        Vector n=gp.geometry.getNormal(gp.point);
        Vector v=ray.getDirection();
        double nv= Util.alignZero(n.dotProduct(v));
        if (nv==0)
            return color;
        Material material=gp.geometry.getMaterial();
        for(LightSource lightSource:scene.lights){

            Vector l=lightSource.getL(gp.point);
            double nl=Util.alignZero(n.dotProduct(l));
            if(Util.alignZero(nl*nv)>0){
                Double3 ktr = transperency(gp, lightSource, l, n,nl);
                if (!(ktr.product(k).lowerThan(MIN_CALC_COLOR_K))) {
                    Color il = lightSource.getIntensity(gp.point).scale(ktr);
                    color = color.add(il.scale(calcDiffusive(material, nl)), il.scale(calcSpecular(material, n, l, nl, v)));
                }
            }
        }
        return color;
    }
    private Double3 calcDiffusive(Material mat, double nl) {
        return mat.kD.scale(nl>0?nl:-nl);
    }

    private Double3 calcSpecular(Material material, Vector normal, Vector lightDir, double cosAngle, Vector rayDir) {
        Vector r = lightDir.subtract(normal.scale(2 * cosAngle));
        double coefficient = -rayDir.dotProduct(r);
        coefficient = Util.alignZero(coefficient) > 0 ? coefficient : 0;
        return material.kS.scale(Math.pow(coefficient, material.nShininess));

    }

    private boolean unshaded(GeoPoint gp,LightSource light, Vector l, Vector n,double nl){
        Vector lightDirection=l.scale(-1);
        Vector deltaVector=n.scale(Util.alignZero(nl)<0?DELTA:-DELTA);
        Point point=gp.point.add(deltaVector);
        Ray lightRay=new Ray(point,lightDirection);
        GeoPoint intersectionPoint=scene.geometries.findClosestIntersection(lightRay);
        if(intersectionPoint==null)
            return true;
        if(intersectionPoint.point.distance(point)<light.getDistance(point))
                return false;
        return true;

    }
        private Double3 transperency(GeoPoint gp,LightSource light, Vector l, Vector n,double nl){
            Ray lightRay = new Ray( gp.point, l.scale(-1),n);
            List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, light.getDistance(gp.point));
          //  GeoPoint intersectionPoint=scene.geometries.findClosestIntersection(lightRay);

            Double3 ktr = Double3.ONE;
            if (intersections == null)
                return ktr;
            for (GeoPoint intersection : intersections)
                if(intersection.point.distance(gp.point)<light.getDistance(gp.point))
                  ktr = ktr.product(intersection.geometry.getMaterial().kT);
            return ktr;


    }


//    private double powr(double b, int e) {
//        double res = 1;
//        for (int i = 0; i < e; ++i)
//            res *= b;
//        for (int i = 0; i > e; --i)
//            res /= b;
//        return res;
//    }
}
