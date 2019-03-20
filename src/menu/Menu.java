package menu;

import animation.Animation;

/**
 * the menu can include some options to select: submenus and selections. when a key of a
 * specific submenu is pressed, the menu display it. when a key of a specific selection is
 * pressed, the menu changes its status accordingly.
 * @param <T> type of the status of the menu.
 */
public interface Menu<T> extends Animation {
   /**
    * add option to the menu. if the selection key will be pressed, the status of this
    * menu has to change change to the returnVal of the option.
    *
    * @param key       - key to select this selection
    * @param message   - indicates what will happen if the user will choose the key
    * @param returnVal - the expected status of the menu, when this selection is selected
    */
    void addSelection(String key, String message, T returnVal);

    /**
     * @return the status of this menu according to the selected selection
     */
    T getStatus();

    /**
     * add submenu to this menu. pressing the key has to pass us to the submenu.
     *
     * @param key     - key to select the submenu
     * @param message - short description of the submenu
     * @param subMenu - the submenu to add to this main menu
     */
    void addSubMenu(String key, String message, Menu<T> subMenu);

}