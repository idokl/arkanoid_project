package menu;

import java.util.List;

import io.LevelInformation;
/**
 * struct to maintain information about one set of levels that the Arkanoid menu offers to play.
 */
public class LevelSetOption {

    private List<LevelInformation> levelsInformationList;
    private String key;
    private String description;

    /**
     * @param levelsInformationList - maintain exact descriptions of the game levels
     * @param key                   - selection key
     * @param description           - short description of the levels sets
     */
    public LevelSetOption(List<LevelInformation> levelsInformationList, String key, String description) {
        this.levelsInformationList = levelsInformationList;
        this.key = key;
        this.description = description;
    }

    /**
     * @return list of levelsInformation of the game's levels
     */
    public List<LevelInformation> getLevelsInformationList() {
        return this.levelsInformationList;
    }

    /**
     * @return selection key
     */
    public String getKey() {
        return this.key;
    }

    /**
     * @return description of the levels set
     */
    public String getDescription() {
        return this.description;
    }
}
