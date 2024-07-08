
package renderer;

import static primitives.Util.isZero;

import java.util.LinkedList;
import java.util.List;

import primitives.*;
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/**
 * @author
 */

public class TargetArea {
	private final static double DISTANCE = 100;
	private final static int DENSITY = 3;
//	private final Point p0;
//	private final Vector vRight, vUp, vTo;
//	private double width, height, distance;

	private Point position;

	private Vector vUp;
	private Vector vRight;
	private Vector vTo;

	//view plane
	private Double height = 0.0;
	private Double width = 0.0;
	private Double distance = 0.0;
	/**
	 * Constructor for TargetArea class
	 * 
	 * @param ray  the main ray to the target area
	 * @param size the size of the target area
	 */
	public TargetArea(Ray ray, double size) {
		position = ray.getHead();
		vTo = ray.getDirection();//z
		double a = vTo.getX(), b = vTo.getY(), c = vTo.getZ();
		vRight = (a == b && b == c) ? new Vector(0, -a, a).normalize() : new Vector(b - c, c - a, a - b).normalize();//x
		vUp = vRight.crossProduct(vTo);//y
		this.height = this.width = size;
		this.distance = DISTANCE;
	}

	/**
	 * Constructor for TargetArea class
	 * 
	 * @param p0  the origin point of the target area
	 * @param vTo the direction vector of the target area
	 * @param vUp the up vector of the target area
	 */
	public TargetArea(Point p0, Vector vTo, Vector vUp) {
		this.vTo = vTo.normalize();
		this.vUp = vUp.normalize();
		this.vRight = vTo.crossProduct(vUp);
		this.position = p0;
	}

	/**
	 * Sets width and height in builder pattern
	 * 
	 * @param width
	 * @param height
	 */
	public void setSize(double width, double height) {
		this.width = width;
		this.height = height;
	}

	/**
	 * Sets distance in builder pattern
	 * 
	 * @param distance
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}

	/**
	 * Constructs ray from origin's location to a the center of a given pixel in a
	 * view plane.
	 * 
	 * @param nX number of columns
	 * @param nY number of rows
	 * @param j  current pixel's x index
	 * @param i  current pixel's y index
	 * @return resulting Ray
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
	 * Constructs a grid of rays in the target area
	 * 
	 * @return list of rays
	 */
	public List<Ray> constructRayBeamGrid() {
		List<Ray> rays = new LinkedList<>();
		for (int i = 0; i < DENSITY; ++i)
			for (int j = 0; j < DENSITY; j++)
				rays.add(constructRay(DENSITY, DENSITY, j, i));
		return rays;
	}
}
