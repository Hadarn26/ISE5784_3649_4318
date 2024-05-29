package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.MissingResourceException;

/**
 * Camera class represents a camera in 3D space with position, direction, and view plane properties.
 * It provides functionality to construct rays through a view plane.
 * @author Hadar Nagar & Elinoy Damari
 */
public class Camera implements Cloneable{

    private Point position;
    private Vector vUp;
    private Vector vRight;
    private Vector vTo;
    private Double height=0.0;
    private Double width=0.0;
    private Double distance=0.0;

    /**
     * Private constructor to enforce the use of the builder for creating Camera instances.
     */
    private Camera() {
    }

    /**
     * Gets the position of the camera.
     * @return the position of the camera.
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Gets the up vector of the camera.
     * @return the up vector of the camera.
     */
    public Vector getvUp() {
        return vUp;
    }

    /**
     * Gets the right vector of the camera.
     * @return the right vector of the camera.
     */
    public Vector getvRight() {
        return vRight;
    }

    /**
     * Gets the to vector of the camera.
     * @return the to vector of the camera.
     */
    public Vector getvTo() {
        return vTo;
    }

    /**
     * Gets the height of the view plane.
     * @return the height of the view plane.
     */
    public Double getHeight() {
        return height;
    }

    /**
     * Gets the width of the view plane.
     * @return the width of the view plane.
     */
    public Double getWidth() {
        return width;
    }

    /**
     * Gets the distance from the camera to the view plane.
     * @return the distance from the camera to the view plane.
     */
    public Double getDistance() {
        return distance;
    }

    /**
     * Creates a new builder for Camera.
     * @return a new Camera.Builder instance.
     */
    public static Builder getBuilder() {
        return new Builder();
    }

    /**
     * Constructs a ray through a specific pixel in the view plane.
     * @param nX number of pixels in the X direction.
     * @param nY number of pixels in the Y direction.
     * @param j pixel index in the X direction.
     * @param i pixel index in the Y direction.
     * @return a Ray from the camera through the specified pixel.
     */
    public Ray constructRay(int nX, int nY, int j, int i){

        Point pixelIJ=position.add(vTo.scale(distance));
        Double rY=height/nY;
        Double rX=width/nX;
        Double yI=-(i-(nY-1)/2.0)*rY;
        Double xJ=-(j-(nX-1)/2.0)*rX;
        if(!Util.isZero(xJ))
            pixelIJ=pixelIJ.add(vRight.scale(-xJ));
        if(!Util.isZero(yI))
            pixelIJ=pixelIJ.add(vUp.scale(yI));
        return new Ray(position,pixelIJ.subtract(position));
    }

    @Override
    public Camera clone() {
        try {
            Camera clone = (Camera) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    /**
     * Builder class for constructing Camera instances with a fluent API.
     */
    public static class Builder{

        final private Camera camera = new Camera();

//        public Builder() {
//            camera=new Camera();
//        }
//
//        public Builder(Camera camera) {
//            this.camera=camera;
//        }


        /**
         * Sets the location of the camera.
         * @param p0 the position of the camera.
         * @return the builder instance.
         */
      public Builder setLocation(Point p0){
          camera.position=p0;
          return this;
      }

        /**
         * Sets the direction vectors of the camera.
         * @param vUp the up vector.
         * @param vTo the to vector.
         * @return the builder instance.
         * @throws IllegalArgumentException if the up vector and to vector are not orthogonal.
         */
      public Builder setDirection(Vector vUp, Vector vTo){
          if(!(Util.isZero(vUp.dotProduct(vTo))))
              throw new IllegalArgumentException("The vectors aren't ortogonal");
          camera.vUp=vUp.normalize();
          camera.vTo=vTo.normalize();
          return this;
      }

        /**
         * Sets the size of the view plane.
         * @param height the height of the view plane.
         * @param width the width of the view plane.
         * @return the builder instance.
         * @throws IllegalArgumentException if the height or width is not positive.
         */
      public Builder setVpSize(Double height, Double width){

          if(height<=0||width<=0)
              throw new IllegalArgumentException("width and height should be positive");
          camera.height=height;
          camera.width=width;
          return this;
      }

        /**
         * Sets the distance from the camera to the view plane.
         * @param distance the distance to the view plane.
         * @return the builder instance.
         * @throws IllegalArgumentException if the distance is not positive.
         */
      public Builder setVpDistance(Double distance){

          if(distance<=0)
              throw new IllegalArgumentException("distance should be not negative");
          camera.distance=distance;
          return this;
      }

        /**
         * Builds and returns the Camera instance.
         * @return the constructed Camera instance.
         * @throws MissingResourceException if any of the required parameters are not set.
         */
      public Camera build(){
          if(camera.position==null)
              throw new MissingResourceException("missing rendering data","Camera","camera's position");
          if(camera.vTo==null)
              throw new MissingResourceException("missing rendering data","Camera","camera's vTo");
          if(camera.vUp==null)
              throw new MissingResourceException("missing rendering data","Camera","camera's vUp");
          if(Util.alignZero(camera.height)<=0)
              throw new MissingResourceException("missing rendering data","Camera","camera's height");
          if(Util.alignZero(camera.width)<=0)
              throw new MissingResourceException("missing rendering data","Camera","camera's width");
          if(Util.alignZero(camera.distance)<=0)
              throw new MissingResourceException("missing rendering data","Camera","camera's distance");

          camera.vRight=(camera.vTo.crossProduct(camera.vUp)).normalize();

          return (Camera)camera.clone();
      }

    }

}
