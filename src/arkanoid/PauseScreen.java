package arkanoid;

import animation.Animation;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.Color;

/**
 * simple animation, that displays a screen with the message "paused -- press space to continue"
 * until the space key is pressed.
 */
public class PauseScreen implements Animation {
    private boolean stop;
    private int radius;

    /**
     * @param k keyboard sensor (of biuoop.GUI)
     */
    public PauseScreen(KeyboardSensor k) {
        this.stop = false;
        this.radius = 50;
    }

    /**
     * do one frame of the Animation.
     *
     * @param d  DrawSurface to draw the sprites of the Animation on
     * @param dt amount of seconds passed since the last frame
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.black);
        d.fillRectangle(0, 0, 800, 600);
        d.setColor(Color.red);
        d.fillCircle(400, 200, 10);
        d.drawCircle(400, 200, this.radius + 60);
        d.drawLine(400, 195, 420, 100);
        d.drawLine(405, 200, 500, 200);

        d.setColor(Color.white);
        d.drawText(300, d.getHeight() / 2 + 75, "Paused", 60);
        d.drawText(150, d.getHeight() / 2 + 150, "Press space to continue", 45);
    }

    /**
     * stopping condition of the Animation.
     *
     * @return true, if the space key has been pressed and the PauseScreen hasn't to be displayed. false, otherwise.
     */
    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}