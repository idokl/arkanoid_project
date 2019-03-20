package animation;

import biuoop.AlphaChannelNotSupportedException;
import biuoop.DrawSurface;

import java.awt.*;

/**
 * Created by ohad on 10/07/2017.
 */
public class mySurfce implements DrawSurface {
    private DrawSurface d;



    public mySurfce(DrawSurface d){
        this.d = d;
    }
    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public void setColor(Color color) throws AlphaChannelNotSupportedException {
        d.setColor(color);
    }

    @Override
    public void drawLine(int i, int i1, int i2, int i3) {

    }

    @Override
    public void drawOval(int i, int i1, int i2, int i3) {

    }

    @Override
    public void fillOval(int i, int i1, int i2, int i3) {

    }

    @Override
    public void drawRectangle(int i, int i1, int i2, int i3) {

    }

    @Override
    public void fillRectangle(int i, int i1, int i2, int i3) {

    }

    @Override
    public void drawImage(int i, int i1, Image image) {

    }

    @Override
    public void drawCircle(int i, int i1, int i2) {

    }

    @Override
    public void fillCircle(int i, int i1, int i2) {

    }

    @Override
    public void drawText(int i, int i1, String s, int i2) {

    }

    @Override
    public void drawPolygon(Polygon polygon) {

    }

    @Override
    public void fillPolygon(Polygon polygon) {

    }
}
