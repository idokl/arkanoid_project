package highscore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

/**
 * A table to store the historic high scores. This class manage a table of some high-scores.
 */
public class HighScoresTable implements Serializable {

    private List<ScoreInfo> highScoreTableList;
    //number of high scores in the table
    private int size;

    /**
     * @param size - the max size of the table.
     */
    // Create an empty high-scores table with the specified size.
    // The size means that the table holds up to size top scores.
    public HighScoresTable(int size) {
        this.highScoreTableList = new ArrayList<ScoreInfo>();
        this.size = size;
    }

    /**
     * @param score - add score to the highScore table.
     */
    // Add a high-score.
    public void add(ScoreInfo score) {
        highScoreTableList.add(getRank(score.getScore()), score);
        if (this.highScoreTableList.size() > this.size) {
            highScoreTableList.remove(size);
        }
    }

    /**
     * @return the size of the table.
     */
    // Return table size.
    public int size() {
        return this.size;
    }

    /**
     * @return list of scoreInfo objects, contains the name and their score.
     */
    // Return the current high scores.
    // The list is sorted such that the highest
    // scores come first.
    public List<ScoreInfo> getHighScores() {
        return highScoreTableList;
    }

    /**
     * @param score - the value of the score to compere with the current highScore table
     * @return -  the correct rank
     */
    // return the rank of the current score: where will it
    // be on the list if added?
    // Rank 1 means the score will be highest on the list.
    // Rank `size` means the score will be lowest.
    // Rank > `size` means the score is too low and will not
    //      be added to the list.
    public int getRank(int score) {
        int index = 0;
        for (ScoreInfo scoreInfo : highScoreTableList) {
            if (score >= scoreInfo.getScore()) {
                return index;
            }
            index++;
        }
        return index;
    }

    /**
     * Clears the table.
     */
    public void clear() {
        highScoreTableList.clear();
    }

    /**
     * Load table data from file. Current table data is cleared.
     *
     * @param filename - gets the path for the high score table file
     * @throws IOException - throws exception if file not found
     */
    public void load(File filename) throws IOException {
        FileInputStream file = new FileInputStream(filename);
        ObjectInputStream objectInputStream = new ObjectInputStream(file);
        try {
            HighScoresTable highScoresTable = (HighScoresTable) objectInputStream.readObject();
            this.highScoreTableList = highScoresTable.highScoreTableList;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(" Something went wrong while reading !");
        } finally {
            try {
                objectInputStream.close(); // closes FileInputStream too
            } catch (IOException e) {
                System.out.println(" Failed closing the file !");
            }
        }
    }

    /**
     * Save table data to the specified file.
     *
     * @param filename - file to maintain the data
     * @throws IOException - throws exception if file not found
     */
    public void save(File filename) throws IOException {
        FileOutputStream file = new FileOutputStream(filename);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(file);
        try {
            objectOutputStream.writeObject(this);
        } catch (IOException e) {
            System.out.println(" Something went wrong while writing !");
        } finally {
            try {
                objectOutputStream.close(); // closes FileInputStream too
            } catch (IOException e) {
                System.out.println(" Failed closing the file !");
            }
        }
    }

    /**
     * Read a table from file and return it.
     * If the file does not exist, or there is a problem with reading it, an empty table is returned.
     *
     * @param filename - name of the file that contains information about the high scores
     * @return high score table according to the file (if it exists)
     */
    public static HighScoresTable loadFromFile(File filename) {
        HighScoresTable highScoresTable;
        if (!filename.exists()) {
            return new HighScoresTable(10);
        } else {
            try {
                highScoresTable = new HighScoresTable(10);
                highScoresTable.load(filename);
                return highScoresTable;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new HighScoresTable(10);
    }
}