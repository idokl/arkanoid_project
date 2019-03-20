package arkanoid;

import biuoop.DrawSurface;
import core.Sprite;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

/**
 * image that is drawn on the background of the screen.
 */
public class ImageBackground implements Sprite {

    private InputStream str;
    private Image image;

    /**
     * constructor.
     *
     * @param str - inputStream of the image file
     */
    public ImageBackground(InputStream str) {
        this.str = str;
        try {
            this.image = ImageIO.read(this.str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.drawImage(0, 0, this.image);
    }

    @Override
    public void timePassed(double dt) {

    }
}
