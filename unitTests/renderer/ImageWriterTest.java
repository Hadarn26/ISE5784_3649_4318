package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testing Image Writer
 * @author Hadar Nagar & Elinoy Damari
 */
public class ImageWriterTest {

    /**
     * Tests the writeToImage method of the ImageWriter class.
     */
    @Test
    void testWriteToImage() {

        // Create an ImageWriter with a test image size
        ImageWriter imageWriter = new ImageWriter("TestPic", 800, 500);
        // Define colors for background and grid
        Color background = new Color(161, 251, 142);
        Color grid = new Color(251, 106, 106);

        // Separately colors each pixel based on the ray tracer's findings
        for (int i = 0; i < imageWriter.getNy(); ++i) {
            for (int j = 0; j < imageWriter.getNx(); ++j) {
                if (i % 50 == 0 || j % 50 == 0)
                    imageWriter.writePixel(j, i, grid);
                else
                    imageWriter.writePixel(j, i, background);
            }
        }
        // Write the image to file
        imageWriter.writeToImage();

    }
}
