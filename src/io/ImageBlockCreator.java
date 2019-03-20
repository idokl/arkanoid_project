package io;

import java.awt.Color;

import arkanoid.Block;
import arkanoid.ImageFilling;
import geometry.Point;
import geometry.Rectangle;

/**
 * ImageBlockCreator make the block that get's image as parameter.
 */
public class ImageBlockCreator implements BlockCreator {

    private int x;
    private int y;
    private int width;
    private int height;
    private Color stroke;
    private int hitPoints;
    private ImageFilling imageFilling;

    /**
     * @param width - the width of the block
     * @param height - the height of the block
     * @param imageFilling - the ImageFilling of the image block
     * @param stroke - the stroke of the block
     * @param hitPoints - the hit points of the block
     */
    public ImageBlockCreator(int width, int height, ImageFilling imageFilling, Color stroke, int hitPoints) {
        this.width = width;
        this.height = height;
        this.stroke = stroke;
        this.imageFilling = imageFilling;
        this.hitPoints = hitPoints;
    }

    @Override
    public Block create(int xpos, int ypos) {
        Rectangle rec = new Rectangle(new Point(xpos, ypos), this.width, this.height);
        return new Block(rec, this.imageFilling, this.stroke, this.hitPoints);
    }
}
