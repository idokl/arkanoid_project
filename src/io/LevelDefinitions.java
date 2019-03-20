package io;

import arkanoid.Block;
import biuoop.DrawSurface;
import core.Sprite;
import core.Velocity;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.awt.Color;

/**
 * LevelDefinitions make every level by the info he gets.
 */
public class LevelDefinitions implements LevelInformation {
    private Map<String, String> map;
    private BlocksFromSymbolsFactory blocksFromSymbolsFactory;
    private List<String> blocksFromLevelDef;

    /**
     *
     * @param map - get the map to read the values from.
     * @param blocksFromLevelDef - list with the string values of the orders of the blocks
     */
    public LevelDefinitions(Map<String, String> map, List<String> blocksFromLevelDef) {
        this.map = map;
        this.blocksFromLevelDef = blocksFromLevelDef;
    }

    private final String velocities = "ball_velocities";
    private final String background = "background";
    private final String xPoint = "blocks_start_x";
    private final String yPoint = "blocks_start_y";
    private final String rHeight = "row_height";
    private final String startOfBlock = "START_BLOCKS";

    @Override
    public int numberOfBalls() {
        return this.initialBallVelocities().size();
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> velList = new ArrayList<Velocity>();
        Map<String, String> velMap = new HashMap<String, String>();
        String str = map.get(velocities);
        String velStr = str.replaceAll("\\s", ",");
        List<String> splitVel = Arrays.asList(velStr.split(","));
        for (int i = 0, j = 1; j < splitVel.size(); i += 2, j += 2) {
            velMap.put(splitVel.get(i), splitVel.get(j));
        }
        for (Map.Entry<String, String> entry : velMap.entrySet()) {
            velList.add(Velocity.fromAngleAndSpeed(Double.valueOf(entry.getKey()), Double.valueOf(entry.getValue())));
        }
        return velList;
    }

    @Override
    public int paddleSpeed() {
        return Integer.parseInt(map.get("paddle_speed"));
    }

    @Override
    public int paddleWidth() {
        return Integer.parseInt(map.get("paddle_width"));
    }

    @Override
    public String levelName() {
        return map.get("level_name");
    }

    @Override
    public Sprite getBackground() {
        Image image = null;
        if (map.get(background).startsWith("image")) {
            String[] str = map.get(background).split("\\(");
            String strImage = str[1].substring(0, str[1].length() - 1);
            InputStream inputStreamImage = ClassLoader.getSystemClassLoader().getResourceAsStream(strImage);
            try {
                image = ImageIO.read(inputStreamImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            final Image finalImage = image;
            return new Sprite() {
                @Override
                public void drawOn(DrawSurface d) {
                    d.drawImage(0, 0, finalImage);
                }

                @Override
                public void timePassed(double dt) {

                }
            };
        } else {
            String[] str = map.get(background).split("color\\(");
            String strColor = str[1].substring(0, str[1].length() - 1);
            final Color color = ColorsParser.colorFromString(strColor);
            return new Sprite() {
                @Override
                public void drawOn(DrawSurface d) {
                    d.setColor(color);
                    d.fillRectangle(0, 0, 800, 600);
                }

                @Override
                public void timePassed(double dt) {

                }
            };

        }
    }

    @Override
    public List<Block> blocks() {
        List<Block> blocksList = new ArrayList<Block>();
        try {
            InputStream blocksDefinitions = ClassLoader.getSystemClassLoader()
                    .getResourceAsStream(map.get("block_definitions"));
            InputStreamReader in = new InputStreamReader(blocksDefinitions);
            this.blocksFromSymbolsFactory = BlocksDefinitionReader.fromReader(in);
            int index = 0;
            int startX = Integer.parseInt(map.get(xPoint));
            int startY = Integer.parseInt(map.get(yPoint));
            for (String str : this.blocksFromLevelDef) {
                index++;
                ArrayList<Character> charList = new ArrayList<Character>();
                if (!str.equals(startOfBlock)) {
                    for (int i = 0; i < str.length(); i++) {
                        charList.add(str.charAt(i));
                    }
                }
                ArrayList<String> strList = new ArrayList<String>();
                for (char a : charList) {
                    strList.add(Character.toString(a));
                }
                Block block = null;

                for (String strSymbol : strList) {

                    if (this.blocksFromSymbolsFactory.isSpaceSymbol(strSymbol)) {
                        startX += this.blocksFromSymbolsFactory.getSpaceWidth(strSymbol);
                    }
                    if (this.blocksFromSymbolsFactory.isBlockSymbol(strSymbol)) {
                        block = this.blocksFromSymbolsFactory.getBlock(strSymbol,
                                startX, startY);
                        blocksList.add(block);
                    }
                    if (block != null) {
                        startX += block.getCollisionRectangle().getWidth();
                    }
                }
                startY = Integer.parseInt(map.get(yPoint));
                startY += index * Integer.parseInt(map.get(rHeight));
                startX = 25;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return blocksList;
    }

    @Override
    public int numberOfBlocksToRemove() {

        return Integer.parseInt(map.get("num_blocks"));
    }

}
