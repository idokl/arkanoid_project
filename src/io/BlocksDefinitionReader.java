package io;

import arkanoid.ImageFilling;

import java.awt.Color;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

/**
 * BlocksDefinitionReader is in charge of reading a block-definitions file
 * and returning a BlocksFromSymbolsFactory object.
 */
public class BlocksDefinitionReader {

    /**
     * @param reader - reader of a blocks definitions file
     * @return blocksFromSymbolsFactory according to the file
     */
    public static BlocksFromSymbolsFactory fromReader(java.io.Reader reader) {
        BlocksDefinitionReader blocksDefinitionReader = new BlocksDefinitionReader();
        BlocksFromSymbolsFactory blocksFromSymbolsFactory = null;
        Map<String, String> mapDefaultHeight, blocksDefMap, spacersDefMap;
        Map<String, BlockCreator> blockCreators = new HashMap<String, BlockCreator>();
        Map<String, Integer> spacerWidths = new HashMap<String, Integer>();
        BufferedReader in = new BufferedReader(reader);
        String line;
        int width = 0, height = 0, hitPoints = 0, defaultWidth = 0, defaultHeight = 0, defaultHitPoints = 0;
        Color stroke = null, defaultStroke = null;
        Map<Integer, String> defaultColorsOrImagesMap, colorsOrImagesMap, colorsOrImagesMapWithFlag = null;
        Map<Integer, Color> colorMap = null, defaultColorMap = null;
        Map<Integer, Image> imageMap = null, defaultImageMap = null;
        Map<String, String> defaultFillNoHitPoints = null;
        boolean noDefaultHitPointsFlag = false;
        try {
            while ((line = in.readLine()) != null) {
                if (!"".equals(line) && !line.startsWith("#")) {
                    if (line.startsWith("default")) {
                        mapDefaultHeight = SplitLines.split(line);
                        if (mapDefaultHeight.containsKey("width")) {
                            defaultWidth = blocksDefinitionReader.widthCheck(mapDefaultHeight);
                        }
                        if (mapDefaultHeight.containsKey("height")) {
                            defaultHeight = blocksDefinitionReader.heightCheck(mapDefaultHeight);
                        }
                        if (mapDefaultHeight.containsKey("hit_points")) {
                            defaultHitPoints = blocksDefinitionReader.hitPointsCheck(mapDefaultHeight);
                        }
                        if (mapDefaultHeight.containsKey("stroke")) {
                            defaultStroke = blocksDefinitionReader.strokeCheck(mapDefaultHeight);
                        }
                        if (defaultHitPoints > 0) {
                            defaultColorsOrImagesMap = blocksDefinitionReader.fillCheck(mapDefaultHeight,
                                    defaultHitPoints);
                        } else {
                            defaultColorsOrImagesMap = new HashMap<Integer, String>();
                            defaultFillNoHitPoints = blocksDefinitionReader.defaultFillCheck(mapDefaultHeight);
                            noDefaultHitPointsFlag = true;
                        }
                        if (defaultColorsOrImagesMap.size() > 0) {
                            if (defaultColorsOrImagesMap.get(1).contains("image")) {
                                defaultImageMap = blocksDefinitionReader.imagesMapCheck(defaultColorsOrImagesMap);
                            } else {
                                defaultColorMap = blocksDefinitionReader.colorsMapCheck(defaultColorsOrImagesMap);
                            }
                        }
                    }
                    if (line.startsWith("bdef")) {
                        blocksDefMap = SplitLines.split(line);
                        if (blocksDefMap.containsKey("width")) {
                            width = blocksDefinitionReader.widthCheck(blocksDefMap);
                        }
                        if (blocksDefMap.containsKey("height")) {
                            height = blocksDefinitionReader.heightCheck(blocksDefMap);
                        }
                        if (blocksDefMap.containsKey("hit_points")) {
                            hitPoints = blocksDefinitionReader.hitPointsCheck(blocksDefMap);
                        }
                        if (blocksDefMap.containsKey("stroke")) {
                            stroke = blocksDefinitionReader.strokeCheck(blocksDefMap);
                        }
                        if (noDefaultHitPointsFlag) {
                            colorsOrImagesMapWithFlag = blocksDefinitionReader.fillCheck(defaultFillNoHitPoints,
                                    hitPoints);
                        }
                        if (hitPoints == 0) {
                            colorsOrImagesMap = blocksDefinitionReader.fillCheck(blocksDefMap, defaultHitPoints);
                        } else {
                            colorsOrImagesMap = blocksDefinitionReader.fillCheck(blocksDefMap, hitPoints);
                        }
                        if (colorsOrImagesMap.size() > 0) {
                            if (colorsOrImagesMap.get(1).contains("image")) {
                                imageMap = blocksDefinitionReader.imagesMapCheck(colorsOrImagesMap);
                            } else {
                                colorMap = blocksDefinitionReader.colorsMapCheck(colorsOrImagesMap);
                            }
                        } else {
                            if (colorsOrImagesMapWithFlag.get(1).contains("image")) {
                                imageMap = blocksDefinitionReader.imagesMapCheck(colorsOrImagesMapWithFlag);
                            } else {
                                colorMap = blocksDefinitionReader.colorsMapCheck(colorsOrImagesMapWithFlag);
                            }
                        }
                        colorsOrImagesMap.clear();
                        final int finalWidth, finalHeight, finalHitPoints;
                        final Color finalStroke;
                        finalWidth = blocksDefinitionReader.whosNull(defaultWidth, width);
                        finalHeight = blocksDefinitionReader.whosNull(defaultHeight, height);
                        finalHitPoints = blocksDefinitionReader.whosNull(defaultHitPoints, hitPoints);
                        if ((defaultStroke != null) && (stroke == null)) {
                            finalStroke = defaultStroke;
                        } else {
                            finalStroke = stroke;
                        }
                        if (imageMap != null) {
                            Map<Integer, Image> mapFromHitPointsToImages = new HashMap<Integer, Image>();
                            if (defaultImageMap != null) {
                                mapFromHitPointsToImages = defaultImageMap;
                            } else {
                                mapFromHitPointsToImages = imageMap;
                            }
                            ImageBlockCreator imageBlockCreator = new ImageBlockCreator(finalWidth, finalHeight,
                                    new ImageFilling(mapFromHitPointsToImages), finalStroke, finalHitPoints);
                            blockCreators.put(blocksDefMap.get("symbol"), imageBlockCreator);
                            imageMap = null;
                        } else {
                            final Map<Integer, Color> finalColorMap;
                            if (defaultColorMap != null) {
                                finalColorMap = new HashMap<Integer, Color>();
                                finalColorMap.putAll(defaultColorMap);
                                defaultColorMap.clear();
                            } else {
                                finalColorMap = new HashMap<Integer, Color>();
                                finalColorMap.putAll(colorMap);
                                colorMap.clear();
                            }
                            ColorBlockCreator colorBlockCreator = new ColorBlockCreator(finalWidth,
                                    finalHeight, finalStroke, finalColorMap, finalHitPoints);
                            blockCreators.put(blocksDefMap.get("symbol"), colorBlockCreator);
                        }
                        width = 0;
                        height = 0;
                        hitPoints = 0;
                    }
                    if (line.startsWith("sdef")) {
                        spacersDefMap = SplitLines.split(line);
                        spacerWidths.put(spacersDefMap.get("symbol"), Integer.parseInt(spacersDefMap.get("width")));
                    }
                }
            }
            blocksFromSymbolsFactory = new BlocksFromSymbolsFactory(blockCreators, spacerWidths);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                System.out.println(" Failed closing the file !");
            }
        }
        return blocksFromSymbolsFactory;
    }

    /**
     * @param blocksDefMap - gets the map of blocks definitions
     * @return - int of the width of the block
     */
    private int widthCheck(Map<String, String> blocksDefMap) {
        return Integer.parseInt(blocksDefMap.get("width"));
    }

    /**
     * @param blocksDefMap - gets the map of blocks definitions
     * @return the int of height of the block
     */
    private int heightCheck(Map<String, String> blocksDefMap) {
        return Integer.parseInt(blocksDefMap.get("height"));
    }

    /**
     * @param blocksDefMap - gets the map of blocks definitions
     * @return the int of hit points
     */
    private int hitPointsCheck(Map<String, String> blocksDefMap) {
        return Integer.parseInt(blocksDefMap.get("hit_points"));
    }

    /**
     * @param blocksDefMap - gets the map of blocks definitions
     * @return - the color of stroke
     */
    private Color strokeCheck(Map<String, String> blocksDefMap) {
        String[] str = blocksDefMap.get("stroke").split("color\\(");
        String strColor = str[1].substring(0, str[1].length() - 1);
        return ColorsParser.colorFromString(strColor);
    }

    /**
     * @param imagesMap - gets map of string and string
     * @return - map of string and image
     */
    private Map<Integer, Image> imagesMapCheck(Map<Integer, String> imagesMap) {
        Map<Integer, Image> imageMap = new HashMap<Integer, Image>();
        for (Map.Entry<Integer, String> entry : imagesMap.entrySet()) {
            String strImage = entry.getValue().replace("image(", "");
            strImage = strImage.substring(0, strImage.length() - 1);
            Image img = null;
            InputStream image = ClassLoader.getSystemClassLoader().getResourceAsStream(strImage);
            try {
                img = ImageIO.read(image);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    image.close();
                } catch (IOException e) {
                    System.out.println(" Failed closing the file !");
                }
            }
            imageMap.put(entry.getKey(), img);
        }

        return imageMap;
    }

    /**
     * @param colorsMap - gets map of string and string
     * @return - map of string and color
     */
    private Map<Integer, Color> colorsMapCheck(Map<Integer, String> colorsMap) {
        Map<Integer, Color> colorMap = new HashMap<Integer, Color>();
        for (Map.Entry<Integer, String> entry : colorsMap.entrySet()) {
            String[] str = entry.getValue().split("color\\(");
            String strColor = str[1].substring(0, str[1].length() - 1);
            colorMap.put(entry.getKey(), ColorsParser.colorFromString(strColor));
        }
        return colorMap;
    }

    /**
     * @param blocksDefMap - gets the map of blocks definitions
     * @param hitPoints    - the hit points for each block
     * @return - new map where every fill is mapped to his hit points
     */
    private Map<Integer, String> fillCheck(Map<String, String> blocksDefMap, int hitPoints) {
        Pattern p, s;
        Map<Integer, String> colorsOrImagesMap = new HashMap<Integer, String>();
        if (hitPoints > 0) {
            for (Map.Entry<String, String> entry : blocksDefMap.entrySet()) {
                s = Pattern.compile("(fill)$");
                Matcher a = s.matcher(entry.getKey());
                if (a.find()) {
                    for (int i = 1; i < hitPoints + 1; i++) {
                        colorsOrImagesMap.put(i, entry.getValue());
                    }
                }
            }
            for (Map.Entry<String, String> entry : blocksDefMap.entrySet()) {
                p = Pattern.compile("(fill)(-)(\\d+)");
                Matcher m = p.matcher(entry.getKey());
                if (m.find()) {
                    colorsOrImagesMap.put(Integer.parseInt(m.group(3)), entry.getValue());
                }
            }
        }
        return colorsOrImagesMap;
    }

    /**
     * @param blocksDefMap - gets the map of blocks definitions
     * @return - new map with just the fill values
     */
    private Map<String, String> defaultFillCheck(Map<String, String> blocksDefMap) {
        Pattern p, s;
        Map<String, String> colorsOrImagesFillMap = new HashMap<String, String>();
        for (Map.Entry<String, String> entry : blocksDefMap.entrySet()) {
            s = Pattern.compile("(fill)$");
            Matcher a = s.matcher(entry.getKey());
            if (a.find()) {
                colorsOrImagesFillMap.put(entry.getKey(), entry.getValue());
            }
        }
        for (Map.Entry<String, String> entry : blocksDefMap.entrySet()) {
            p = Pattern.compile("(fill)(-)(\\d+)");
            Matcher m = p.matcher(entry.getKey());
            if (m.find()) {
                colorsOrImagesFillMap.put(entry.getKey(), entry.getValue());
            }
        }
        return colorsOrImagesFillMap;
    }

    /**
     * this method decide whether the value of a numerical field should be the default value or a specific value.
     *
     * @param defaultInt - default value of the field (or 0 if missing)
     * @param regularInt - specific value to the field (or 0 if missing)
     * @return the final value of the field
     */
    private int whosNull(int defaultInt, int regularInt) {
        if ((defaultInt != 0) && (regularInt == 0)) {
            return defaultInt;
        } else {
            return regularInt;
        }
    }
}
