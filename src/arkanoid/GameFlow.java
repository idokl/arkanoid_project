package arkanoid;

import animation.AnimationRunner;
import animation.KeyPressStoppableAnimation;
import biuoop.DialogManager;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import core.Counter;
import highscore.HighScoresAnimation;
import highscore.HighScoresTable;
import highscore.ScoreInfo;
import io.LevelInformation;
import menu.LevelSetOption;
import menu.MenuAnimation;
import menu.Menu;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * This class is in charge of creating the different levels of the game, and moving from one level to the next.
 */
public class GameFlow {

    private final String fileName = "highscores.txt";

    private AnimationRunner animationRunner;
    private GUI gui;
    private KeyboardSensor keyboardSensor;
    private HighScoresTable highScoresTable;
    private int guiWidth;
    private int guiHeight;

    /**
     * Constructor.
     *
     * @param ar        - animation runner to run the Animation of each level
     * @param gui       - a biuoop.GUI
     * @param ks        - keyboard sensor
     * @param guiWidth  - width of the GUI
     * @param guiHeight - height of the GUI
     */
    public GameFlow(AnimationRunner ar, GUI gui, KeyboardSensor ks, int guiWidth, int guiHeight) {
        this.animationRunner = ar;
        this.gui = gui;
        this.keyboardSensor = ks;
        this.guiWidth = guiWidth;
        this.guiHeight = guiHeight;
        this.highScoresTable = HighScoresTable.loadFromFile(new File(fileName));
    }


    /**
     * Task is action that has to be executed and may return a value that indicates its running result.
     *
     * @param <T> returnVal of the task running.
     */
    private interface Task<T> {
        /**
         * @return generic value according to the task type (and according to the result of the running)
         */
        T run();
    }


    /**
     * This task displays the high score table.
     */
    private Task<Void> showHiScoresTask = new Task<Void>() {
        @Override
        public Void run() {
            animationRunner.run(new KeyPressStoppableAnimation(keyboardSensor, KeyboardSensor.SPACE_KEY,
                    new HighScoresAnimation(highScoresTable, KeyboardSensor.SPACE_KEY)));
            return null;
        }
    };


    /**
     * Task that run Arkanoid game with some levels.
     */
    private final class PlayGameTask implements Task<Void> {
        private Counter score = new Counter(0);
        private Counter numberOfLives = new Counter(5);
        private List<LevelInformation> levels;

        /**
         * constructor.
         *
         * @param levels - list of level information that enable us to build the levels of the game
         */
        private PlayGameTask(List<LevelInformation> levels) {
            this.levels = levels;
        }

        @Override
        public Void run() {
            for (LevelInformation levelInfo : levels) {
                GameLevel level = new GameLevel(levelInfo, animationRunner, keyboardSensor,
                        score, numberOfLives, guiWidth, guiHeight);
                level.initialize();

                while ((level.getNumberOfLives().getValue() > 0)
                        && (level.getBlockCounter().getValue() > 0)) {
                    level.playOneTurn();
                }

                if (level.getNumberOfLives().getValue() == 0) {
                    break;
                }
            }
            animationRunner.run(new KeyPressStoppableAnimation(
                    keyboardSensor, KeyboardSensor.SPACE_KEY, new EndScreen(this.score, this.numberOfLives)));
            if (highScoresTable.getRank(this.score.getValue()) < 10) {
                this.addScoreToScoresTable();
            }
            animationRunner.run(new KeyPressStoppableAnimation(keyboardSensor, KeyboardSensor.SPACE_KEY,
                    new HighScoresAnimation(highScoresTable, KeyboardSensor.SPACE_KEY)));
            //this.stoppableShowHiScoresTask.run();

            return null;
        }

        /**
         * add score of the player to the high score table.
         */
        private void addScoreToScoresTable() {
            DialogManager dialog = gui.getDialogManager();
            String name = dialog.showQuestionDialog("Enter Your Name", "What is your name?", "Anonymous");
            highScoresTable.add(new ScoreInfo(name, this.score.getValue()));
            try {
                highScoresTable.save(new File(fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private Task<Void> exitTask = new Task<Void>() {
        @Override
        public Void run() {
            gui.close();
            return null;
        }
    };

    /**
     * create a menu with the level sets according to the given definitions, show this menu and run the selected tasks.
     *
     * @param levelsList - list that include information about the levels sets: description, the key to select the game
     *                   and the task that display the game
     */
    public void runGame(List<LevelSetOption> levelsList) {

        while (true) {

            Menu<Task<Void>> mainMenu = new MenuAnimation<Task<Void>>("Arkanoid", this.keyboardSensor);

            mainMenu.addSelection("h", "High scores", showHiScoresTask);
            mainMenu.addSelection("e", "Exit", exitTask);


            Menu<Task<Void>> levelSetSubmenu = new MenuAnimation<Task<Void>>("Select levels set", this.keyboardSensor);
            //create submenu to enable to select levels set:
            for (LevelSetOption levelSetOption : levelsList) {
                List<LevelInformation> levelsInformationList = levelSetOption.getLevelsInformationList();
                String key = levelSetOption.getKey();
                String description = levelSetOption.getDescription();

                Task<Void> playLevels = new PlayGameTask(levelsInformationList);
                levelSetSubmenu.addSelection(key, description, playLevels);
            }

            mainMenu.addSubMenu("s", "Select level and start game", levelSetSubmenu);

            animationRunner.run(mainMenu);
            // wait for user selection
            Task<Void> task = mainMenu.getStatus();
            task.run();
        }
    }
}