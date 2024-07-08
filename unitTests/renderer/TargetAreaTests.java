/**
 * 
 */
package renderer;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import primitives.*;

/**
 * @author ashih
 *
 */
class TargetAreaTests {

	TargetArea targetArea = new TargetArea(new Ray(new Point(0, 0, 0), new Vector(1, 0, 0)), 100);

	@Test
	void testConstructRayBeamGrid() {
		List<Ray> result = targetArea.constructRayBeamGrid();
		assertEquals(9, result.size(), "ERROR: findIntersections() did not return the right number of rays");

		result = targetArea.constructRayBeamGrid().stream().filter(r -> r.getDirection().dotProduct(new Vector(0, 1, 0)) <= 0)
				.collect(Collectors.toList());
		assertEquals(6, result.size(), "ERROR: findIntersections() did not return the right number of reflected rays");

		result = targetArea.constructRayBeamGrid().stream().filter(r -> r.getDirection().dotProduct(new Vector(0, 1, 0)) > 0)
				.collect(Collectors.toList());
		assertEquals(3, result.size(), "ERROR: findIntersections() did not return the right number of refracted rays");
	}

}
