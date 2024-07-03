///////////////////////////////////////////////
//
//
//
//package renderer;
//
//import geometries.Geometry;
//import lighting.LightSource;
//import primitives.*;
//import scene.Scene;
//import java.util.List;
//import geometries.Intersectable.GeoPoint;
//
///**
// * The SimpleRayTracer class extends RayTracerBase and provides a basic implementation
// * for tracing rays in a scene. It calculates the color of the closest intersection point
// * or returns the background color if no intersections are found.
// */
//public class SimpleRayTracer extends  RayTracerBase {
//
//    /**
//     * Constructs a SimpleRayTracer with the specified scene.
//     *
//     * @param scene the scene to be rendered.
//     */
//    public SimpleRayTracer(Scene scene) {
//        super(scene);
//    }
//   // private static final double DELTA = 0.1;
//    private static final int MAX_CALC_COLOR_LEVEL = 10;
//    private static final double MIN_CALC_COLOR_K = 0.001;
//
//    @Override
//    public Color traceRay(Ray ray) {
//        GeoPoint intersectionPoint = scene.geometries.findClosestIntersection(ray);
//        return intersectionPoint == null
//                ? scene.backGround
//                : calcColor(intersectionPoint, ray);
//        //findGeoIntersections
//    }
//
//    /**
//     * Calculates the color at a given point.
//     * In this simple implementation, it returns the ambient light intensity of the scene.
//     *
//     * @param intersection the point at which to calculate the color.
//     * @return the color at the given point.
//     */
//    private Color calcColor(GeoPoint intersection,Ray ray)
//    {
//        return scene.ambientLight.getIntensity()
//                .add(calcColor(intersection, ray, MAX_CALC_COLOR_LEVEL, Double3.ONE));
//    }
//
//
//    private Color calcColor(GeoPoint gp, Ray ray,int level,Double3 k){
//        Color color=calcLocalEffects(gp, ray,k);
//        return 1==level?color:color.add(calcGlobalEffects(gp,ray,level,k));
//
//    }
//
//    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
//        Material material=gp.geometry.getMaterial();
//        Vector normal=gp.geometry.getNormal(gp.point);
//        Point gpPoint=gp.point;
//        Vector direction=ray.getDirection();
//        return calcGlobalEffect(constructRefractedRay(normal,gpPoint,direction),material.kT,level,k)
//                .add(calcGlobalEffect(constructReflectedRay(normal,gpPoint,direction),material.kR,level,k));
//    }
//
//    private Ray constructReflectedRay(Vector normal,Point point, Vector rayDirection) {
//        return new Ray( point, rayDirection.subtract(normal.scale(2*rayDirection.dotProduct(normal))), normal);
//    }
//
//
//
//    private Ray constructRefractedRay(Vector normal,Point point, Vector rayDirection) {
//        return new Ray( point,rayDirection, normal);
//    }
//
//    private Color calcGlobalEffect( Ray ray,Double3 kX, int level, Double3 k) {
//        Double3 kKx=kX.product(k);
//        if(kKx.lowerThan(MIN_CALC_COLOR_K))
//            return Color.BLACK;
//        GeoPoint gp=scene.geometries.findClosestIntersection(ray);
//        if (gp == null)
//            return scene.backGround.scale(kX);
//        return Util.isZero(gp.geometry.getNormal(gp.point).dotProduct(ray.getDirection())) ? Color.BLACK
//                : calcColor(gp, ray, level - 1, kKx).scale(kX);
//
////        return (gp==null?scene.backGround:calcColor(gp,ray,level-1,kKx)).scale(kX);
//    }
//
//
//    private Color calcLocalEffects(GeoPoint gp, Ray ray,Double3 k){
//        Color color = gp.geometry.getEmission();
//        Vector v = ray.getDirection();
//        Vector n = gp.geometry.getNormal(gp.point);
//        double nv = Util.alignZero(n.dotProduct(v));
//        if (nv == 0)
//            return color;
//        Material material = gp.geometry.getMaterial();
//        for (LightSource lightSource : scene.lights) {
//            Vector l = lightSource.getL(gp.point);
//            double nl = Util.alignZero(n.dotProduct(l));
//            if ((nl * nv > 0)) {
//                Double3 ktr = transparency(gp, lightSource, l, n);
//                if (!(ktr.product(k).lowerThan(MIN_CALC_COLOR_K))) {
//                    Color iL = lightSource.getIntensity(gp.point).scale(ktr);
//                    color = color.add(iL.scale(calcDiffusive(material, nl)),
//                            iL.scale(calcSpecular(material, n, l, nl, v)));
//                }
//            }
//        }
//        return color;
////        Color color = gp.geometry.getEmission();
////        Vector n=gp.geometry.getNormal(gp.point);
////        Vector v=ray.getDirection();
////        double nv= Util.alignZero(n.dotProduct(v));
////        if (nv==0)
////            return color;
////        Material material=gp.geometry.getMaterial();
////        for(LightSource lightSource:scene.lights){
////            Vector l=lightSource.getL(gp.point);
////            double nl=Util.alignZero(n.dotProduct(l));
////            if(Util.alignZero(nl*nv)>0 ){
////                Double3 ktr=transparency(gp,lightSource,l,n);
////                if(!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
////                    Color il = lightSource.getIntensity(gp.point);
////                    color = color.add(il.scale(calcDiffusive(material, nl)), il.scale(calcSpecular(material, n, l, nl, v)));
////                }
////            }
////        }
////        return color;
//    }
//    private Double3 calcDiffusive(Material mat, double nl) {
//        return mat.kD.scale(nl>0?nl:-nl);
//    }
//
//    private Double3 calcSpecular(Material material, Vector normal, Vector lightDir, double cosAngle, Vector rayDir) {
//        Vector r = lightDir.subtract(normal.scale(2 * cosAngle));
//        double coefficient = -rayDir.dotProduct(r);
//        coefficient = Util.alignZero(coefficient) > 0 ? coefficient : 0;
//        return material.kS.scale(Math.pow(coefficient, material.nShininess));
//
//
//    }
//
//    private boolean unshaded(GeoPoint gp,LightSource light, Vector l, Vector n,double nl){
//        Vector lightDirection=l.scale(-1);
//        Point point=gp.point;
//        Ray lightRay=new Ray(gp.point,lightDirection,n);
////        List<GeoPoint> inters=scene.geometries.findGeoIntersections(lightRay);
////        if(inters==null||inters.isEmpty())
////            return true;
////        for (GeoPoint geoPoint: inters){
////            if(geoPoint.point.distance(point)<light.getDistance(point))
////                return false;
////        }
//        GeoPoint intersectionPoint=scene.geometries.findClosestIntersection(lightRay);
//        if(intersectionPoint==null)
//            return true;
//        if(intersectionPoint.point.distance(point)<light.getDistance(point))
//                return false;
//        return true;
//
//    }
//
//    private Double3 transparency(GeoPoint gp, LightSource lightSource, Vector l, Vector n) {
//        Ray lightRay = new Ray( gp.point, l.scale(-1),n);
//        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay,
//                lightSource.getDistance(gp.point));
//        Double3 ktr = Double3.ONE;
//        if (intersections == null)
//            return ktr;
//        for (GeoPoint intersection : intersections)
//            ktr = ktr.product(intersection.geometry.getMaterial().kT);
//        return ktr;
//    }
////        if(intersectionPoint==null)
////            return ktr;
////        for()
//
////            return true;
////        if(intersectionPoint.point.distance(point)<light.getDistance(point))
////            return false;
////        return true;
//
//
//
//
////    private double powr(double b, int e) {
////        double res = 1;
////        for (int i = 0; i < e; ++i)
////            res *= b;
////        for (int i = 0; i > e; --i)
////            res /= b;
////        return res;
////    }
//}
package renderer;

import primitives.Color;

import primitives.*;
import scene.*;
import geometries.Intersectable.GeoPoint;
import lighting.*;

import java.util.List;

/**
 * the most basic rayTracer
 *
 * @author Shulman and Yonatan
 *
 */
public class SimpleRayTracer extends RayTracerBase {

    private static final int MAX_CALC_COLOR_LEVEL = 8;
    private static final double MIN_CALC_COLOR_K = 0.001;

    /**
     * construct rayTracer with specific scene
     *
     * @param scene with the relevant geometries and lighting
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        GeoPoint intersectionPoint = scene.geometries.findClosestIntersection(ray);
        return (intersectionPoint == null) ? scene.backGround : calcColor(intersectionPoint, ray);
    }

    /**
     * returns the color of a given point
     *
     * @param intersection the point to calculate
     * @return the color of the point
     */
    private Color calcColor(GeoPoint intersection, Ray ray) {
        return scene.ambientLight.getIntensity().add(calcColor(intersection, ray, MAX_CALC_COLOR_LEVEL, Double3.ONE));
    }

    /**
     * recursive function to calculate the color
     *
     * @param gp    the geoPoint of the intersection
     * @param ray   the ray of the intersection
     * @param level the level of recursion we're in
     * @param k     the current coefficient at our level of recursion
     * @return the calculated color
     */
    private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(gp, ray, k);
        return 1 == level ? color : color.add(calcGlobalEffects(gp, ray, level, k));
    }

    /**
     * calculates the effects of other object in the scene in a specific
     * point
     *
     * @param gp    the specific point and its geometry
     * @param ray   the intersection ray
     * @param level the current level of recursion
     * @param k     the current coefficient at our level of recursion
     * @return the calculated color
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Vector v = ray.getDirection();
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        return calcColorGlobalEffect(reflectionRay(n, gp.point, v), level, k, material.kR)
                .add(calcColorGlobalEffect(refractionRay(n, gp.point, v), level, k, material.kT));
    }

    /**
     * calculates the effect of specific global part of the system
     * @param ray the ray to trace
     * @param level of the recursion
     * @param k the recursion coefficient
     * @param kx the attenuation of the specific part
     * @return the calculated color
     */
    private Color calcColorGlobalEffect(Ray ray, int level, Double3 k, Double3 kx) {
        Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K))
            return Color.BLACK;
        GeoPoint gp = scene.geometries.findClosestIntersection(ray);
        if (gp == null)
            return scene.backGround.scale(kx);
        return Util.isZero(gp.geometry.getNormal(gp.point).dotProduct(ray.getDirection())) ? Color.BLACK
                : calcColor(gp, ray, level - 1, kkx).scale(kx);
    }

    /**
     * calculate the effects of the geometry itself without the effect of other
     * geometries on this object
     *
     * @param gp  the point to calculate the effects on
     * @param ray the ray we intersected with
     * @return the result color of the local effects
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
        Color color = gp.geometry.getEmission();
        Vector v = ray.getDirection();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = Util.alignZero(n.dotProduct(v));
        if (nv == 0)
            return color;
        Material material = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = Util.alignZero(n.dotProduct(l));
            if ((nl * nv > 0)) {
                Double3 ktr = transparency(gp, lightSource, l, n);
                if (!(ktr.product(k).lowerThan(MIN_CALC_COLOR_K))) {
                    Color iL = lightSource.getIntensity(gp.point).scale(ktr);
                    color = color.add(iL.scale(calcDiffusive(material, nl)),
                            iL.scale(calcSpecular(material, n, l, nl, v)));
                }
            }
        }
        return color;
    }

    /**
     * calculates the diffusive light part of the object
     *
     * @param material the material of the object
     * @param cosAngle the cosine of the angle between the light and the normal to
     *                 the object
     * @return the diffusive light color
     */
    private Double3 calcDiffusive(Material material, double cosAngle) {
        return material.kD.scale(cosAngle > 0 ? cosAngle : -cosAngle);
    }

    /**
     * calculates the specular light part of the object
     *
     * @param material the material of the object
     * @param normal   the normal to the object
     * @param lightDir the direction of the light
     * @param cosAngle the cosine of the angle between the light and the normal to
     *                 the object
     * @param rayDir   the direction the camera is pointed to
     * @return
     */
    private Double3 calcSpecular(Material material, Vector normal, Vector lightDir, double cosAngle, Vector rayDir) {
        Vector r = lightDir.subtract(normal.scale(2 * cosAngle));
        double coefficient = Util.alignZero(-rayDir.dotProduct(r));
        coefficient = coefficient > 0 ? coefficient : 0;
        return material.kS.scale(Util.alignZero(Math.pow(coefficient, material.nShininess)));
    }

    /**
     * calculates the transparency coefficient of a point on a geometry
     *
     * @param gp          the point and its geometry
     * @param lightSource the light source
     * @param l           the direction vector of the light source
     * @param n           the normal to the geometry

     * @return the transparency coefficient
     */
    private Double3 transparency(GeoPoint gp, LightSource lightSource, Vector l, Vector n) {
        Ray lightRay = new Ray( gp.point, l.scale(-1),n);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay,
                lightSource.getDistance(gp.point));
        Double3 ktr = Double3.ONE;
        if (intersections == null)
            return ktr;
        for (GeoPoint intersection : intersections)
            ktr = ktr.product(intersection.geometry.getMaterial().kT);
        return ktr;
    }

    /**
     * calculates the ray for the refraction
     *
     * @param n        the normal vector to the geometry
     * @param point    the point on our geometry
     * @param inVector the vector coming in from the light

     * @return the calculated ray
     */
    private Ray refractionRay(Vector n, Point point, Vector inVector) {
        return new Ray( point, inVector,n);
    }

    /**
     * calculates the ray for the reflection
     *
     * @param n        the normal vector to the geometry
     * @param point    the point on our geometry
     * @param inVector the vector coming in from the light

     * @return the calculated ray
     */
    private Ray reflectionRay(Vector n, Point point, Vector inVector) {
        return new Ray( point, inVector.subtract(n.scale(2 * inVector.dotProduct(n))),n);

    }

}
