package io;

import java.util.List;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Map;

/**
 * split lines to map of block definitions.
 */
public class SplitLines {

    /**
     * split lines to map of block definitions.
     *
     * @param line - line from a file of definitions
     * @return map - of fields and values
     */
    public static Map<String, String> split(String line) {
        Map<String, String> blocksDefMap = new HashMap<String, String>();
        List<String> splitLine = new LinkedList<>(Arrays.asList(line.split("\\s")));
        splitLine.remove(0);
        for (String string : splitLine) {
            String[] str = string.split(":");
            blocksDefMap.put(str[0], str[1]);
        }
        return blocksDefMap;
    }
}
