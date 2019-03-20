package highscore;

import animation.Animation;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * animation to display the scores in the high-scores table.
 */
public class HighScoresAnimation implements Animation {
    private HighScoresTable highScoresTable;
    private String endKey;
    private boolean stop;

    /**
     * @param scores - table of high scores.
     * @param endKey - supposed to be a key that stop the display of this animation
     */
    public HighScoresAnimation(HighScoresTable scores, String endKey) {
        this.highScoresTable = scores;
        this.endKey = endKey;
        this.stop = false;
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(new Color(51, 170, 255));
        d.fillRectangle(0, 0, 800, 600);
        d.setColor(new Color(200, 0, 0));
        d.drawText(100, 70, "HighScore Table", 50);
        d.setColor(Color.black);
        d.drawLine(100, 110, 100, 550);
        d.drawLine(400, 110, 400, 550);
        d.drawLine(700, 110, 700, 550);

        for (int i = 0; i < 12; i++) {
            d.drawLine(100, 110 + i * 40, 700, 110 + i * 40);
        }
        d.setColor(new Color(200, 0, 0));
        d.drawText(130, 140, "Name", 25);
        int index = 0;
        d.setColor(Color.black);
        for (ScoreInfo scoreInfo : this.highScoresTable.getHighScores()) {
            d.drawText(130, 180 + index * 40, scoreInfo.getName(), 17);
            index++;
        }
        d.setColor(new Color(200, 0, 0));
        d.drawText(430, 140, "Score", 25);
        index = 0;
        d.setColor(Color.black);
        for (ScoreInfo scoreInfo : this.highScoresTable.getHighScores()) {
            d.drawText(430, 180 + index * 40, "" + scoreInfo.getScore(), 17);
            index++;
        }
        d.drawText(10, 570, "Paused. press " + this.endKey + " to continue", 20);
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}