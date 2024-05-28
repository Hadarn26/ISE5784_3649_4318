package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.MissingResourceException;

public class Camera implements Cloneable{

    private Point position;
    private Vector vUp;
    private Vector vRight;
    private Vector vTo;
    private Double height=0.0;
    private Double width=0.0;
    private Double distance=0.0;

    private Camera() {
    }

    public Point getPosition() {
        return position;
    }

    public Vector getvUp() {
        return vUp;
    }

    public Vector getvRight() {
        return vRight;
    }

    public Vector getvTo() {
        return vTo;
    }

    public Double getHeight() {
        return height;
    }

    public Double getWidth() {
        return width;
    }

    public Double getDistance() {
        return distance;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

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

    public static class Builder{

        final private Camera camera = new Camera();

//        public Builder() {
//            camera=new Camera();
//        }
//
//        public Builder(Camera camera) {
//            this.camera=camera;
//        }

      public Builder setLocation(Point p0){
          camera.position=p0;
          return this;
      }

      public Builder setDirection(Vector vUp, Vector vTo){
          if(!(Util.isZero(vUp.dotProduct(vTo))))
              throw new IllegalArgumentException("The vectors aren't ortogonal");
          camera.vUp=vUp.normalize();
          camera.vTo=vTo.normalize();
          return this;
      }

      public Builder setVpSize(Double height, Double width){

          if(height<=0||width<=0)
              throw new IllegalArgumentException("width and height should be positive");
          camera.height=height;
          camera.width=width;
          return this;
      }

      public Builder setVpDistance(Double distance){

          if(distance<=0)
              throw new IllegalArgumentException("distance should be not negative");
          camera.distance=distance;
          return this;
      }

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
