package io;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ColorParser convert string to color.
 */
public class ColorsParser {

    /**
     * parse color definition and return the specified color.
     *
     * @param s - string that represents a color.
     * @return the Color the string represents
     */
    public static java.awt.Color colorFromString(String s) {
        Pattern c = Pattern.compile("RGB *\\( *([0-9]+), *([0-9]+), *([0-9]+) *\\)+");
        Matcher m = c.matcher(s);
        if (m.matches()) {
            return new Color(Integer.valueOf(m.group(1)),  // r
                    Integer.valueOf(m.group(2)),  // g
                    Integer.valueOf(m.group(3))); // b
        } else {
            switch (s) {
                case "black":
                    return Color.BLACK;
                case "blue":
                    return Color.BLUE;
                case "cyan":
                    return Color.cyan;
                case "gray":
                    return Color.gray;
                case "lightGray":
                    return Color.lightGray;
                case "green":
                    return Color.green;
                case "orange":
                    return Color.orange;
                case "pink":
                    return Color.pink;
                case "red":
                    return Color.red;
                case "white":
                    return Color.white;
                case "yellow":
                    return Color.yellow;
                default:
                    break;
            }
            return null;
        }
    }
}