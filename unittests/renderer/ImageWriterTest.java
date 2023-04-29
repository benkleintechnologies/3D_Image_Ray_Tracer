package renderer;

import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.*;

/**
 * Class ImageWriterTest that will build images to test the program
 * @author Eli Hawk and Binyamin Klein
 * 563385586 & 576708589
 */
public class ImageWriterTest {

    /**
     * Test method for {@link ImageWriter#writeToImage()}.
     */
    @Test
    void writeImageTest() {
        //Create an image made of one color background and a different color grid.
        // Make a 10x16 grid with a resolution of 500x800
        ImageWriter imageWriter = new ImageWriter("test_grid", 800, 500);
        //Write to the pixels
        Color background = new Color(50, 200, 50);
        Color grid = new Color(0, 0, 255);
        //50x50 squares
        for (int i = 0; i < 500; i++) {
            for (int j = 0; j < 800; j++) {
                if (i % 50 == 0 || j % 50 == 0) {
                    imageWriter.writePixel(j, i, grid);
                }else{
                    imageWriter.writePixel(j, i, background);
                }
            }
        }
        imageWriter.writeToImage();
    }
}
