package renderer;

import java.util.LinkedList;
import java.util.List;

import primitives.*;

/**
 * TargetArea class to represent an area on the target plane for beam rays.
 */
public class TargetArea {
	private static final double DISTANCE = 100;  // Default distance from the camera
	private static final int DENSITY = 3;       // Density of rays in the grid

	private Point position;
	private Vector vUp;
	private Vector vRight;
	private Vector vTo;

	private double height = 0.0;
	private double width = 0.0;
	private double distance = 0.0;

	/**
	 * Constructor for TargetArea class using a Ray and size.
	 *
	 * @param ray  the main ray to the target area.
	 * @param size the size of the target area.
	 */
	public TargetArea(Ray ray, double size) {
		position = ray.getHead();
		vTo = ray.getDirection().normalize(); // Direction (z-axis)
		vRight = (vTo.getX() == vTo.getY() && vTo.getY() == vTo.getZ()) ?
				new Vector(0, -vTo.getX(), vTo.getX()).normalize() :
				new Vector(vTo.getY() - vTo.getZ(), vTo.getZ() - vTo.getX(), vTo.getX() - vTo.getY()).normalize(); // Right (x-axis)
		vUp = vRight.crossProduct(vTo); // Up (y-axis)
		this.height = this.width = size;
		this.distance = DISTANCE;
	}

	/**
	 * Constructor for TargetArea class using origin point, direction vector and up vector.
	 *
	 * @param p0  the origin point of the target area.
	 * @param vTo the direction vector of the target area.
	 * @param vUp the up vector of the target area.
	 */
	public TargetArea(Point p0, Vector vTo, Vector vUp) {
		this.vTo = vTo.normalize();
		this.vUp = vUp.normalize();
		this.vRight = vTo.crossProduct(vUp);
		this.position = p0;
	}

	/**
	 * Sets width and height in builder pattern.
	 *
	 * @param width  the width of the target area.
	 * @param height the height of the target area.
	 */
	public void setSize(double width, double height) {
		this.width = width;
		this.height = height;
	}

	/**
	 * Sets distance in builder pattern.
	 *
	 * @param distance the distance from the target area.
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}

	/**
	 * Constructs a ray from origin's location to the center of a given pixel in the target area.
	 *
	 * @param nX number of columns.
	 * @param nY number of rows.
	 * @param j  current pixel's x index.
	 * @param i  current pixel's y index.
	 * @return resulting Ray.
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

	/**
	 * Constructs a grid of rays in the target area.
	 *
	 * @return list of rays.
	 */
	public List<Ray> constructRayBeamGrid() {
		List<Ray> rays = new LinkedList<>();
		// Adding the central ray to ensure the center is covered
		//rays.add(new Ray(position, vTo));
		for (int i = 0; i < DENSITY; ++i) {
			for (int j = 0; j < DENSITY; j++) {
				rays.add(constructRay(DENSITY, DENSITY, j, i));
			}
		}
		return rays;
	}
}
