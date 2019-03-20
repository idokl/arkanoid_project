package io;

import arkanoid.Block;

import java.util.Map;

/**
 * Factory that can get a symbol and creates the desired block according to the definitions.
 */
public class BlocksFromSymbolsFactory {
    private Map<String, Integer> spacerWidths;
    private Map<String, BlockCreator> blockCreators;

    /**
     * constructor.
     *
     * @param blockCreators - map from symbols to block creators
     * @param spacerWidths  - map from symbols to spacer widths
     */
    public BlocksFromSymbolsFactory(Map<String, BlockCreator> blockCreators, Map<String, Integer> spacerWidths) {
        this.spacerWidths = spacerWidths;
        this.blockCreators = blockCreators;
    }

    /**
     * @param s - string to check whether it's a space symbol
     * @return true if 's' is a valid space symbol, false otherwise.
     */
    public boolean isSpaceSymbol(String s) {
        return spacerWidths.containsKey(s);
    }

    /**
     * @param s - string to check whether it's a block symbol
     * @return true if 's' is a valid block symbol, false otherwise.
     */
    public boolean isBlockSymbol(String s) {
        return blockCreators.containsKey(s);
    }

    /**
     * @param s    - symbol
     * @param xpos - x coordinate of the position the block should be
     * @param ypos - y coordinate of the position the block should be
     * @return a block according to the definitions associated with symbol s.
     * The block will be located at position (xpos, ypos).
     */
    public Block getBlock(String s, int xpos, int ypos) {
        return this.blockCreators.get(s).create(xpos, ypos);
    }

    /**
     * @param s - spacer symbol
     * @return the width in pixels associated with the given spacer-symbol.
     */
    public int getSpaceWidth(String s) {
        return this.spacerWidths.get(s);
    }

}