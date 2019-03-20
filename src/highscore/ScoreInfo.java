package highscore;

import java.io.Serializable;

/**
 * entry in the high score table. maintain information about the player and his score.
 */
public class ScoreInfo implements Serializable {
    private String name;
    private int score;

    /**
     * constructor.
     *
     * @param name  - name of the player
     * @param score - score the player reached in the game
     */
    public ScoreInfo(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return score
     */
    public int getScore() {
        return this.score;
    }
}