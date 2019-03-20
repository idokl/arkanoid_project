import animation.AnimationRunner;
import arkanoid.GameFlow;
import io.LevelInformation;
import io.LevelSpecificationReader;
import biuoop.GUI;
import menu.LevelSetOption;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;


/**
 * Ass6Game - Create new game out of sprites and environment, initialize and run it.
 */
public class Ass6Game {
    /**
     * main function.
     * run Arkanoid game with some levels.
     *
     * @param args - indicates numbers of the levels of the game.
     *             [The levels the game contains are depend on these arguments:
     *             the arguments 1, 2, 3, 4 indicate the levels DirectHit, WideEasy, Green3, FinalFour (respectively).
     *             if none of these arguments is supplied, the default levels of the game are 1, 2, 3, 4.
     */
    public static void main(String[] args) {
        int guiWidth = 800;
        int guiHeight = 600;
        //Reader reader;
        List<LevelInformation> levelsFromFile = new ArrayList<LevelInformation>();
        List<LevelSetOption> levelSetOptions = new ArrayList<LevelSetOption>();

        String levelSetsFileName;
        if (args.length > 0) {
            levelSetsFileName = args[0];
        } else {
            levelSetsFileName = "level_sets.txt";
        }

        LineNumberReader is = null;
        LevelSpecificationReader levelSpecificationReader = new LevelSpecificationReader();
        try {
            InputStream levelSetsStream = ClassLoader.getSystemClassLoader().getResourceAsStream(levelSetsFileName);
            InputStreamReader isr = new InputStreamReader(levelSetsStream);
            is = new LineNumberReader(new BufferedReader(isr));

            String line;
            while ((line = is.readLine()) != null) {
                int lineIndex = is.getLineNumber();
                if (lineIndex % 2 == 1) {
                    String[] keyAndDescriptionOfLevelSet = line.split(":");
                    if (keyAndDescriptionOfLevelSet.length == 2) {
                        String levelSetKey = keyAndDescriptionOfLevelSet[0];
                        String levelSetDescription = keyAndDescriptionOfLevelSet[1];
                        String levelDefinitionsFileName = is.readLine();

                        InputStream levelsDefinitionStream = ClassLoader.getSystemClassLoader().getResourceAsStream(
                                levelDefinitionsFileName);
                        InputStreamReader levelsReader = new InputStreamReader(levelsDefinitionStream);

                        levelSpecificationReader = new LevelSpecificationReader();
                        levelsFromFile = levelSpecificationReader.fromReader(levelsReader);
                        LevelSetOption levelSetOption = new LevelSetOption(levelsFromFile, levelSetKey,
                                levelSetDescription);

                        levelSetOptions.add(levelSetOption);
                        levelsReader.close();
                    } else {
                        throw new RuntimeException("this file isn't a valid level-sets file because"
                                + " there is odd line that doesn't have exactly one colon");
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("we didn't succeed to read the levelSetsFile or a definition file!");
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    System.out.println("Failed closing the levelSetsFile!");
                }
            }
        }

        GUI gui = new GUI("Arkanoid", guiWidth, guiHeight);
        AnimationRunner animationRunner = new AnimationRunner(gui);
        GameFlow gameFlow = new GameFlow(animationRunner, gui, gui.getKeyboardSensor(), guiWidth, guiHeight);
        gameFlow.runGame(levelSetOptions);
        System.exit(0);
    }
}