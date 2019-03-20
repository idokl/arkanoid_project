package arkanoid;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import biuoop.DrawSurface;

/**
 * Image that is filling a block.
 */
public class ImageFilling implements BlockFilling {

    private static final int KEY_OF_DEFAULT_IMAGE = -1;
    private Map<Integer, Image> imageMap = new HashMap<Integer, Image>();

    /**
     * constructor.
     *
     * @param image - permanent image of the block
     */
    public ImageFilling(Image image) {
        this.imageMap.put(KEY_OF_DEFAULT_IMAGE, image);
    }

    /**
     * constructor.
     *
     * @param imageMap - map from hit-points number to the image of the block when it has this number of hit-points
     */
    public ImageFilling(Map<Integer, Image> imageMap) {
        this.imageMap = imageMap;
    }

    @Override
    public void drawFilling(int x, int y, DrawSurface surface, int numberOfHits) {
        int keyOfImage;
        if (imageMap.containsKey(numberOfHits)) {
            keyOfImage = numberOfHits;
        } else {
            keyOfImage = KEY_OF_DEFAULT_IMAGE;
        }
        surface.drawImage(x, y, imageMap.get(keyOfImage));

    }

}
