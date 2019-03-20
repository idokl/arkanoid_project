package io;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Map;

/**
 * LevelSpecificationReader gets the file with the levels, read and analyze them.
 */
public class LevelSpecificationReader {

    private List<LevelInformation> levelInformationList;
    private List<String> singleLevel;
    private List<ArrayList<String>> separateLevels;
    private List<String> blocksFromLevelDef;

    private final String startLevel = "START_LEVEL";
    private final String endLevel = "END_LEVEL";
    private final String startBlocks = "START_BLOCKS";
    private final String endBlocks = "END_BLOCKS";

    /**
     * constructor.
     */
    public LevelSpecificationReader() {
        this.levelInformationList = new LinkedList<>();
        this.singleLevel = new ArrayList<>();
        this.separateLevels = new ArrayList<ArrayList<String>>();
        this.blocksFromLevelDef = new ArrayList<String>();
    }

    /**
     *
     * @param reader - get's the file to read.
     * @return - List of Levels.
     */
    public List<LevelInformation> fromReader(java.io.Reader reader) {
        BufferedReader in = new BufferedReader(reader);
        String line = null;
        try {
            while ((line = in.readLine()) != null) {
                if (line.contentEquals(startLevel)) {
                    while (!line.contentEquals(endLevel)) {
                        line = in.readLine();
                        this.singleLevel.add(line);
                    }
                    this.separateLevels.add(new ArrayList<String>(this.singleLevel));
                    this.singleLevel.clear();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                System.out.println(" Failed closing the file !");
            }
        }
        Map<String, String> m = new HashMap<String, String>();
        for (ArrayList<String> levelToLevelInfo : this.separateLevels) {
            for (int i = 0; i < levelToLevelInfo.size(); i++) {
                while (!levelToLevelInfo.get(i).equals(endBlocks)) {
                    while (!levelToLevelInfo.get(i).equals(startBlocks)) {
                        String[] split = levelToLevelInfo.get(i).split(":");
                        m.put(split[0], split[1]);
                        i++;
                    }
                    while (!levelToLevelInfo.get(i).equals(endBlocks)) {
                        if (!levelToLevelInfo.get(i).startsWith(startBlocks)) {
                            this.blocksFromLevelDef.add(levelToLevelInfo.get(i));
                        }
                        i++;
                    }
                }
                break;
            }
            LevelDefinitions levelDefinitions = new LevelDefinitions(m, new ArrayList<String>(this.blocksFromLevelDef));

            this.levelInformationList.add(levelDefinitions);
            m = new HashMap<String, String>();
            this.blocksFromLevelDef.clear();
        }
        return this.levelInformationList;
    }
}