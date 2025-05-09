package renderer;

import primitives.*;

import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;

import renderer.PixelManager;

import static primitives.Util.isZero;

/*
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
   // private TargetArea targetArea;

    private int antiAliasingFactor = 1;

    private boolean threads = false;
    private PixelManager pixelManager;
    private double printInterval = 1;
    private int threadsCount = 0;//num of threads
    //private Point centerPoint;

    private boolean useAdaptive=false;
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
//    public Ray constructRay(int nX, int nY, int j, int i) {
//
////        Double rY = height / nY;
////        Double rX = width / nX;
////        Point pixelIJ = position.add(vTo.scale(distance));
////
////        Double yI = -(i - (nY - 1) / 2.0) * rY;
////        Double xJ = (j - (nX - 1) / 2.0) * rX;
////        // Check if the pixel is at the center of the view plane
////        if (Util.isZero(xJ) && Util.isZero(yI)) {
////            return new Ray(position, pixelIJ.subtract(position));
////        }
////
////        // Check if the pixel is on the horizontal axis of the view plane
////        if (Util.isZero(xJ)) {
////            pixelIJ = pixelIJ.add(vUp.scale(yI));
////            return new Ray(position, pixelIJ.subtract(position));
////        }
////
////        // Check if the pixel is on the vertical axis of the view plane
////        if (Util.isZero(yI)) {
////            pixelIJ = pixelIJ.add(vRight.scale(xJ));
////            return new Ray(position, pixelIJ.subtract(position));
////        }
////
////        // Calculate the final point on the view plane for the specified pixel
////        pixelIJ = pixelIJ.add(vRight.scale(xJ).add(vUp.scale(yI)));
////
////        // Return the constructed ray from the camera's location to the calculated point on the view plane
////        return new Ray(position, pixelIJ.subtract(position));
//        return targetArea.constructRay(nX, nY, j, i);
//        //return targetArea.constructRayBeamGrid()
//
//
//    }
    private Point findPixelLocation(int nX, int nY, int j, int i) {

        double rY = height / nY;
        double rX = width / nX;

        double yI = -(i - (nY - 1d) / 2) * rY;
        double jX = (j - (nX - 1d) / 2) * rX;
        Point pIJ = position.add(vTo.scale(distance));

        if (yI != 0) pIJ = pIJ.add(vUp.scale(yI));
        if (jX != 0) pIJ = pIJ.add(vRight.scale(jX));
        return pIJ;
    }
    public Ray constructRay(int nX, int nY, int j, int i) {
        return new Ray(position, findPixelLocation(nX, nY, j, i).subtract(position));
    }

    @Override
    public Camera clone() {
        try {
            return (Camera) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Cloning not supported", e);
        }
    }

    public List<Ray> constructRays(int nX, int nY, int j, int i) {
        List<Ray> rays = new LinkedList<>();
        Point centralPixel = findPixelLocation(nX, nY, j, i);
        double rY = height / nY / antiAliasingFactor;
        double rX = width / nX / antiAliasingFactor;
        double x, y;

        if (useAdaptive)
        {

        }
        for (int rowNumber = 0; rowNumber < antiAliasingFactor; rowNumber++) {
            for (int colNumber = 0; colNumber < antiAliasingFactor; colNumber++) {
                y = -(rowNumber - (antiAliasingFactor - 1d) / 2) * rY;
                x = (colNumber - (antiAliasingFactor - 1d) / 2) * rX;
                Point pIJ = centralPixel;
                if (y != 0) pIJ = pIJ.add(vUp.scale(y));
                if (x != 0) pIJ = pIJ.add(vRight.scale(x));
                rays.add(new Ray(position, pIJ.subtract(position)));
            }
        }
        return rays;
    }

    /*
     * Builder class for constructing Camera instances with a fluent API.
     */
    public static class Builder {

        final private Camera camera = new Camera();

        /**
         * Sets number of threads in builder pattern.
         *
         * @param threadsCount
         * @return Camera that results
         */
        public Builder setMultiThreading(int threadsCount) {
            camera.threadsCount = threadsCount;
            return this;
        }

        public Builder setAdaptive(boolean adaptive) {
            camera.useAdaptive = adaptive;
            return this;
        }

        /**
         * Sets interval size for progress printing in builder pattern.
         *
         * @param d
         * @return Camera that results
         */
        public Builder setDebugPrint(double d) {
            camera.printInterval = d;
            return this;
        }

//        public Builder setThreads() {
//            camera.threads = true;
//            return this;
//        }

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
            if (imageWriter == null) {
                throw new IllegalArgumentException("ImageWriter cannot be null");
            }
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
        public Builder setDirection(Vector vTo, Vector vUp) {
            if (!(Util.isZero(vUp.dotProduct(vTo))))
                throw new IllegalArgumentException("The vectors aren't ortogonal");
            camera.vUp = vUp.normalize();
            camera.vTo = vTo.normalize();
            return this;
        }

        public Builder setAntiAliasingFactor(int antiAliasingFactor) {
            camera.antiAliasingFactor = antiAliasingFactor;
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
            if (rayTracer == null) {
                throw new IllegalArgumentException("RayTracer cannot be null");
            }
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
            String missingRenderingData = "missing rendering data";
            String cameraClass = "Camera";
            if (camera.position == null)
                throw new MissingResourceException(missingRenderingData, cameraClass, "camera's position");
            if (camera.vTo == null)
                throw new MissingResourceException(missingRenderingData, cameraClass, "camera's vTo");
            if (camera.vUp == null)
                throw new MissingResourceException(missingRenderingData, cameraClass, "camera's vUp");
            if (camera.imageWriter == null)
                throw new MissingResourceException(missingRenderingData, cameraClass, "camera's image Writer");
            if (camera.rayTracer == null)
                throw new MissingResourceException(missingRenderingData, cameraClass, "camera's ray tracer");
            if (Util.alignZero(camera.height) <= 0)
                throw new MissingResourceException(missingRenderingData, cameraClass, "camera's height");
            if (Util.alignZero(camera.width) <= 0)
                throw new MissingResourceException(missingRenderingData, cameraClass, "camera's width");
            if (Util.alignZero(camera.distance) <= 0)
                throw new MissingResourceException(missingRenderingData, cameraClass, "camera's distance");

//            camera.targetArea = new TargetArea(camera.position, camera.vTo, camera.vUp);
//            camera.targetArea.setSize(camera.width, camera.height);
//            camera.targetArea.setDistance(camera.distance);

            camera.vRight = (camera.vTo.crossProduct(camera.vUp)).normalize();

            return camera.clone();
        }

//

//

//
    }
//

//

    /**
     * Renders the image by casting rays through each pixel.
     */
    public Camera renderImage() {
       // pixelManager = new PixelManager(imageWriter.getNy(), imageWriter.getNx(), printInterval);
        PixelManager.initialize(imageWriter.getNy(), imageWriter.getNx(), printInterval);
        if (threadsCount == 0) {
            for (int i = 0; i < imageWriter.getNy(); i++)
                for (int j = 0; j < imageWriter.getNx(); j++) {

                    this.imageWriter.writePixel(j, i, castRay(j, i));
                }
        }
//        else { // see further... option 2
//            var threads = new LinkedList<Thread>(); // list of threads
//            while (threadsCount-- > 0) // add appropriate number of threads
//                threads.add(new Thread(() -> { // add a thread with its code
//                    PixelManager pixel; // current pixel(row,col)
//                    // allocate pixel(row,col) in loop until there are no more pixels
//                    while ( pixelManager.nextPixel())
//                        // cast ray through pixel (and color it – inside castRay)
//                        this.imageWriter.writePixel(pixelManager.row, pixelManager.col, castRay(pixelManager.row, pixelManager.col));
//                    // castRay(imageWriter.getNx(), imageWriter.getNy(), pixel.col(), pixel.row());
//                }));
//            // start all the threads
//            for (var thread : threads)
//                thread.start();
//            // wait until all the threads have finished
//            try {
//                for (var thread : threads)
//                    thread.join();
//            } catch (InterruptedException ignore) {
//            }
    //    }

                else {
            // Render the image using adaptive super-sampling
            // Create multiple threads to process the pixels in parallel

            while (threadsCount-- > 0) {
                new Thread(() -> {
                    // Iterate over each pixel in the image
                    for (PixelManager pixel=new PixelManager(); pixel.nextPixel(); PixelManager.pixelDone()) {
                        // Apply adaptive super-sampling to determine the pixel color
                        if (useAdaptive){
                            Color pixelColorAdaptive = SuperSampling(imageWriter.getNx(), imageWriter.getNy(), pixel.col, pixel.row, antiAliasingFactor, false);
                            imageWriter.writePixel(pixel.col, pixel.row, pixelColorAdaptive);
                    }
                        else{
                        // Construct rays for the current pixel and trace them using the ray tracer
                        List<Ray> rays = constructRays(imageWriter.getNx(), imageWriter.getNy(), pixel.col, pixel.row);
                        Color pixelColor = rayTracer.traceRays(rays);
                            imageWriter.writePixel(pixel.col, pixel.row, pixelColor);
                        }

                    }
                }).start();
            }
            // Wait for all the threads to finish processing the pixels
            PixelManager.waitToFinish();
        }

        return this;
    }
    private Color SuperSampling(int nX, int nY, int j, int i,  int numOfRays, boolean adaptiveAlising)  {
        // Get the right and up vectors of the camera
        Vector Vright = vRight;
        Vector Vup = vUp;
        // Get the location of the camera
        Point cameraLoc =position;
        // Calculate the number of rays in each row and column
        int numOfRaysInRowCol = (int)Math.floor(Math.sqrt(numOfRays));
        // If the number of rays is 1, perform regular ray tracing
        if(numOfRaysInRowCol == 1)
            return rayTracer.traceRay(constructRayThroughPixel(nX, nY, j, i));
        // Calculate the center point of the current pixel
        Point pIJ = findPixelLocation(nX, nY, j, i);///////////////////////////////////////
        // Calculate the height and width ratios of the pixel
        double rY = Util.alignZero(height / nY);
        double rX = Util.alignZero(width / nX);

        // Calculate the pixel row and column ratios
        double PRy = rY/numOfRaysInRowCol;
        double PRx = rX/numOfRaysInRowCol;

        if (adaptiveAlising)
            return rayTracer.AdaptiveSuperSamplingRec(pIJ, rX, rY, PRx, PRy,cameraLoc,Vright, Vup,null);
        else
            return rayTracer.RegularSuperSampling(pIJ, rX, rY, PRx, PRy,cameraLoc,Vright, Vup,null);
    }
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {
        Point pIJ = findPixelLocation(nX, nY, j, i); // center point of the pixel

        //Vi,j = Pi,j - P0, the direction of the ray to the pixel(j, i)
        Vector vIJ = pIJ.subtract(position);
        return new Ray(position, vIJ);
    }

    /**
     * Prints a grid on the image with the specified interval and color.
     *
     * @param interval the spacing between grid lines.
     * @param color    the color of the grid lines.
     */
    public Camera printGrid(int interval, Color color) {
        int nY = imageWriter.getNy();
        int nX = imageWriter.getNx();
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                if (i % interval == 0 || j % interval == 0) {
                    imageWriter.writePixel(j, i, color);
                }
            }
        }
        return this;
    }

    /**
     * Writes the image to the output.
     */
    public void writeToImage() {
        imageWriter.writeToImage();
    }

//    private Color castRay(int j, int i) {
//       // List<Ray> lst = targetArea.constructRayBeamGrid();
//        Ray ray = constructRay(this.imageWriter.getNx(), this.imageWriter.getNy(), j, i);
//       // Color sumColor=Color.BLACK;
//       // for (Ray ray1 : lst)
//        return this.rayTracer.traceRay(ray);
//        //return sumColor.reduce(lst.size());
//    }

    private Color castRay( int i, int j) {
        if (antiAliasingFactor == 1) {
            List<Ray> ray = constructRays(this.imageWriter.getNx(), this.imageWriter.getNy(), j, i);
            return this.rayTracer.traceRay(ray.get(0));
        } else {
            List<Ray> rays = constructRays(this.imageWriter.getNx(), this.imageWriter.getNy(), j, i);
            return (rayTracer.traceRays(constructRays(this.imageWriter.getNx(), this.imageWriter.getNy(), i, j)));
//        Color color;
//        if (antiAliasingFactor == 1)
//            color = rayTracer.traceRay(constructRay(this.imageWriter.getNx(), this.imageWriter.getNy(), i, j));
//        else
//            color = rayTracer.traceRays(constructRays(this.imageWriter.getNx(), this.imageWriter.getNy(), i, j));
//
////        if (i == 0 && j % 10 == 0) {
////            System.out.print("Traced: ");
////            System.out.print((float) i / (float) imageWriter.getNy() * 100);
////            System.out.println("%");
////        }
//        pixelManager.pixelDone();
//        return color;
        }
    }

    private Color traceRays(List<Ray> rays) {

        Color color = Color.BLACK;
        for (Ray ray : rays) {
            color = color.add(rayTracer.traceRay(ray));
        }
        return color.reduce(rays.size());

    }
}


