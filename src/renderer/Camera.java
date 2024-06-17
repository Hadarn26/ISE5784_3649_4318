package renderer;

import primitives.*;

import java.util.MissingResourceException;

/**
 * Camera class represents a camera in 3D space with position, direction, and view plane properties.
 * It provides functionality to construct rays through a view plane.
 *
 * @author Hadar Nagar & Elinoy Damari
 */
public class Camera implements Cloneable {

    private Point position;

    private Vector vUp;
    private Vector vRight;
    private Vector vTo;

    //view plane
    private Double height = 0.0;
    private Double width = 0.0;
    private Double distance = 0.0;

    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;

    /**
     * Private constructor to enforce the use of the builder for creating Camera instances.
     */
    private Camera() {
    }

    /**
     * Gets the position of the camera.
     *
     * @return the position of the camera.
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Gets the up vector of the camera.
     *
     * @return the up vector of the camera.
     */
    public Vector getvUp() {
        return vUp;
    }

    /**
     * Gets the right vector of the camera.
     *
     * @return the right vector of the camera.
     */
    public Vector getvRight() {
        return vRight;
    }

    /**
     * Gets the to vector of the camera.
     *
     * @return the to vector of the camera.
     */
    public Vector getvTo() {
        return vTo;
    }

    /**
     * Gets the height of the view plane.
     *
     * @return the height of the view plane.
     */
    public Double getHeight() {
        return height;
    }

    /**
     * Gets the width of the view plane.
     *
     * @return the width of the view plane.
     */
    public Double getWidth() {
        return width;
    }

    /**
     * Gets the distance from the camera to the view plane.
     *
     * @return the distance from the camera to the view plane.
     */
    public Double getDistance() {
        return distance;
    }


    /**
     * Creates a new builder for Camera.
     *
     * @return a new Camera.Builder instance.
     */
    public static Builder getBuilder() {
        return new Builder();
    }





    /**
     * Constructs a ray through a specific pixel in the view plane.
     *
     * @param nX number of pixels in the X direction.
     * @param nY number of pixels in the Y direction.
     * @param j  pixel index in the X direction.
     * @param i  pixel index in the Y direction.
     * @return a Ray from the camera through the specified pixel.
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        Double rY = height / nY;
        Double rX = width / nX;
        Point pixelIJ = position.add(vTo.scale(distance));

        Double yI = -(i - (nY - 1) / 2.0) * rY;
        Double xJ = (j - (nX - 1) / 2.0) * rX;
        // Check if the pixel is at the center of the view plane
        if (Util.isZero(xJ) && Util.isZero(yI)) {
            return new Ray(position, pixelIJ.subtract(position));
        }

        // Check if the pixel is on the horizontal axis of the view plane
        if (Util.isZero(xJ)) {
            pixelIJ = pixelIJ.add(vUp.scale(yI));
            return new Ray(position, pixelIJ.subtract(position));
        }

        // Check if the pixel is on the vertical axis of the view plane
        if (Util.isZero(yI)) {
            pixelIJ = pixelIJ.add(vRight.scale(xJ));
            return new Ray(position, pixelIJ.subtract(position));
        }

        // Calculate the final point on the view plane for the specified pixel
        pixelIJ = pixelIJ.add(vRight.scale(xJ).add(vUp.scale(yI)));

        // Return the constructed ray from the camera's location to the calculated point on the view plane
        return new Ray(position, pixelIJ.subtract(position));

    }

    @Override
    public Camera clone() {
        try {
            return (Camera) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Cloning not supported", e);
        }
    }

    /**
     * Builder class for constructing Camera instances with a fluent API.
     */
    public static class Builder {

        final private Camera camera = new Camera();

        /**
         * Sets the location of the camera.
         *
         * @param p0 the position of the camera.
         * @return the builder instance.
         */
        public Builder setLocation(Point p0) {
            camera.position = p0;
            return this;
        }

        /**
         * Sets the ImageWriter for the camera.
         *
         * @param imageWriter the ImageWriter instance to be used by the camera.
         * @return the builder instance.
         */
        public Builder setImageWriter(ImageWriter imageWriter) {
            camera.imageWriter = imageWriter;
            return this;
        }

        /**
         * Sets the direction vectors of the camera.
         *
         * @param vUp the up vector.
         * @param vTo the to vector.
         * @return the builder instance.
         * @throws IllegalArgumentException if the up vector and to vector are not orthogonal.
         */
        public Builder setDirection(Vector vUp, Vector vTo) {
            if (!(Util.isZero(vUp.dotProduct(vTo))))
                throw new IllegalArgumentException("The vectors aren't ortogonal");
            camera.vUp = vUp.normalize();
            camera.vTo = vTo.normalize();
            return this;
        }

        /**
         * Sets the size of the view plane.
         *
         * @param height the height of the view plane.
         * @param width  the width of the view plane.
         * @return the builder instance.
         * @throws IllegalArgumentException if the height or width is not positive.
         */
        public Builder setVpSize(Double height, Double width) {

            if (height <= 0 || width <= 0)
                throw new IllegalArgumentException("width and height should be positive");
            camera.height = height;
            camera.width = width;
            return this;
        }

        /**
         * Sets the RayTracer for the camera.
         *
         * @param rayTracer the RayTracerBase instance to be used by the camera.
         * @return the builder instance.
         */
        public Builder setRayTracer(RayTracerBase rayTracer) {
            camera.rayTracer = rayTracer;
            return this;
        }

        /**
         * Sets the distance from the camera to the view plane.
         *
         * @param distance the distance to the view plane.
         * @return the builder instance.
         * @throws IllegalArgumentException if the distance is not positive.
         */
        public Builder setVpDistance(Double distance) {

            if (distance <= 0)
                throw new IllegalArgumentException("distance should be not negative");
            camera.distance = distance;
            return this;
        }

        /**
         * Builds and returns the Camera instance.
         *
         * @return the constructed Camera instance.
         * @throws MissingResourceException if any of the required parameters are not set.
         */
        public Camera build() {
            String missingRenderingData="missing rendering data";
            String cameraClass="Camera";
            if (camera.position == null)
                throw new MissingResourceException(missingRenderingData, cameraClass, "camera's position");
            if (camera.vTo == null)
                throw new MissingResourceException(missingRenderingData, cameraClass, "camera's vTo");
            if (camera.vUp == null)
                throw new MissingResourceException(missingRenderingData,cameraClass, "camera's vUp");
            if (camera.imageWriter == null)
                throw new MissingResourceException(missingRenderingData,cameraClass, "camera's image Writer");
            if (camera.rayTracer == null)
                throw new MissingResourceException(missingRenderingData,cameraClass, "camera's ray tracer");
            if (Util.alignZero(camera.height) <= 0)
                throw new MissingResourceException(missingRenderingData, cameraClass, "camera's height");
            if (Util.alignZero(camera.width) <= 0)
                throw new MissingResourceException(missingRenderingData, cameraClass, "camera's width");
            if (Util.alignZero(camera.distance) <= 0)
                throw new MissingResourceException(missingRenderingData, cameraClass, "camera's distance");

            camera.vRight = (camera.vTo.crossProduct(camera.vUp)).normalize();

            return camera.clone();
        }

        /**
         * Prints a grid on the image with the specified interval and color.
         *
         * @param interval the spacing between grid lines.
         * @param color the color of the grid lines.
         */
        public void printGrid(int interval, Color color){
            int nY = camera.imageWriter.getNy();
            int nX = camera.imageWriter.getNx();
            for (int i = 0; i < nY; i += interval)
                for (int j = 0; j < nX; j += 1)
                    camera.imageWriter.writePixel(j, i, color);
            for (int i = 0; i < nY; i += 1)
                for (int j = 0; j < nX; j += interval)
                    camera.imageWriter.writePixel(j, i, color);
        }

        /**
         * Writes the image to the output.
         */
        public void writeToImage(){
            camera.imageWriter.writeToImage();
        }

        /**
         * Renders the image by casting rays through each pixel.
         */
        public void renderImage(){

            for (int i=0;i<camera.imageWriter.getNy();i++)
                for (int j=0;j<camera.imageWriter.getNx();j++)
                {
                    camera.castRay(camera.imageWriter.getNx(),camera.imageWriter.getNy(),j,i);
                }

        }

    }

    private void castRay(int Nx, int Ny, int column, int row){
        imageWriter.writePixel(column, row,rayTracer.traceRay(constructRay(Nx, Ny, row, column)));
    }

}
