package io;

import arkanoid.Block;
import geometry.Point;
import geometry.Rectangle;

import java.awt.Color;
import java.util.Map;

/**
 * Color block creator.
 */
public class ColorBlockCreator implements BlockCreator {

   private int width;
    private int height;
    private Color stroke;
    private Map<Integer, Color> colorMap;
    private int hitPoints;

    /**
     *
     * @param width - the width of the block
     * @param height - the height of the block
     * @param stroke - the stroke of the block
     * @param colorMap - map with hit points and color
     * @param hitPoints - the hit points of the block
     */
    public ColorBlockCreator(int width, int height, Color stroke, Map<Integer, Color> colorMap, int hitPoints) {
        this.width = width;
        this.height = height;
        this.stroke = stroke;
        this.colorMap = colorMap;
        this.hitPoints = hitPoints;
    }

    @Override
    public Block create(int xpos, int ypos) {
        Rectangle rec = new Rectangle(new Point(xpos, ypos), this.width, this.height);
        return new Block(rec, this.stroke, this.colorMap, this.hitPoints);
    }
}
