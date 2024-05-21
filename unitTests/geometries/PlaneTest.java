package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class PlaneTest {

    private final Point p001 = new Point(0, 0, 1);
    private final Point p100 = new Point(1, 0, 0);
    private final Point p010 = new Point(0, 1, 0);
    //private final Vector v001 = new Vector(0, 0, 1);
    @Test
    void testGetNormalPoint() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the normal is the right one
        Plane p = new Plane(new Point(0, 0, 1), new Point(0, 1, 0), new Point(1, 0, 0));
        Vector result = p.getNormal(new Point(0, 0, 1));
        // test the length is 1
        assertEquals(1, result.length(), "ERROR: the length of the normal is not 1");
        // check the normal in orthogonal to the plane
        assertTrue(Util.isZero(result.dotProduct(new Vector(0, -1, 1)))&&
                Util.isZero(result.dotProduct(new Vector(-1, 1, 0))),"ERROR: the getNormal(Point p) does not work correctly");

    }

   // @Test
    // void testTestGetNormal() {
   // }

    @Test
    void testPlane() {
        // ============ Boundary Values Tests ==================
        // TC01: Test two points are the same
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(new Point(1, 2, 3), new Point(1, 2, 3), new Point(1, 2, 4)),
                "ERROR: does not throw exception for two points are the same");

        // TC02: Test three points are on the same line
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(new Point(1, 2, 3), new Point(2, 4, 6), new Point(3, 6, 9)),
                "ERROR: does not throw exception for three points are on the same line");
    }

    @Test
    void testFindIntsersections() {
        Plane plane=new Plane(p001,p010,p100);
                /*
        * מחלקות שקילות:
.1קרן שמתחילה מחוץ למישור, לא מקבילה למישור, מייצרת זווית לא ישרה עם
המישור, וחותכת את המישור
.2קרן שמתחילה מחוץ למישור, לא מקבילה למישור, מייצרת זווית לא ישרה עם
המישור, ולא חותכת את המישור
מקרי קצה וגבול:
.12מקרים של קרן שמקבילה למישור (מקרה של קרן מחוץ למישור ומקרה של קרן
בתוך המישור)
.23מקרים של קרן המאוðכת למישור (מתחילה "לפðי", בתוך, ו"אחרי" המישור)
.3מקרה אחד של קרן שלא מקבילה ולא מאוðכת למישור אך מתחיל בתוך המישור
.4 ומקרה אחד שדומה למקרה הקודם, אך ראשית הקרן בדיוק ב"ðקודת הייחוס" של
המישור (הðקודה ששמורה באובייקט של המישור בðוסף לווקטור הðורמל, או
במילים אחרות - ðקודה ðתוðה בתוך המישור)
        * */
        Point p200=new Point(2,0,0);
        Vector v502=new Vector(-5,0,2);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray starts outside the plane, not orthogonal, not paralal, cross the Plane
        List<Point> result1=plane.findIntsersections(new Ray(p200,v502));
        List<Point> exp=List.of(new Point(0.33,0,0.67));
        assertEquals(1, result1.size(), "Wrong number of points");
        assertEquals(exp, result1, "Ray starts outside the plane, not orthogonal, not paralal, cross the Plane");

        // TC02: Ray starts outside the plane, not orthogonal, not paralal, doesn't cross the Plane
        assertNull(plane.findIntsersections(new Ray(p200, v502.scale(-1))), "Ray starts outside the plane, not orthogonal, not paralal, doesn't cross the Plane");

        // =============== Boundary Values Tests ==================

        //**** Group: Ray parallel to the Plane
        // TC03: Ray inside the plane
        // TC04: Ray outside inside the plane

        //**** Group: Ray orthogonal to the Plane
        // TC05: Ray start inside the plane
        // TC06: Ray starts before the plane
        // TC07: Ray starts after the plane

        //**** Group: Ray not parallel and not orthogonal to the Plane
        // TC08: Ray starts inside the plane
        // TC09: Ray tstarts in the reference point of the plane

    }
}