package menu;

import animation.Animation;
import animation.KeyPressStoppableAnimation;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.util.ArrayList;

/**
 * @param <T>
 */
public class MenuAnimation<T> implements Menu<T> {
    private KeyboardSensor keyboardSensor;
    private Animation decoratedAnimation;
    private T status;
    private Menu<T> currentSubmenu;
    private ArrayList<OptionInfo<T>> options = new ArrayList<OptionInfo<T>>();
    private ArrayList<MenuInfo<T>> submenus = new ArrayList<MenuInfo<T>>();

    /**
     * constructor.
     *
     * @param title          the menu title
     * @param keyboardSensor biuoop.keyboardSensor
     */
    public MenuAnimation(String title, KeyboardSensor keyboardSensor) {
        this.keyboardSensor = keyboardSensor;
        this.decoratedAnimation = new DefaultMenuAnimation(title);
    }

    /**
     * @param key       - key to select this new selection
     * @param massage   - indicates what will happen if the user will choose the key
     * @param returnVal - the expected status of the menu, when the new selection is selected
     */
    @Override
    public void addSelection(String key, String massage, T returnVal) {
        //wrap the default Animation of menu in order to make this animation stop in case the user press the key
        this.decoratedAnimation = new KeyPressStoppableAnimation(this.keyboardSensor, key, this.decoratedAnimation);

        //add OptionInfo that saves information about the selection option that was added
        this.options.add(new OptionInfo<T>(key, massage, returnVal));
    }

    @Override
    public void addSubMenu(String key, String message, Menu<T> subMenu) {
        submenus.add(new MenuInfo<T>(key, message, subMenu));
    }

    @Override
    public T getStatus() {
        if (this.currentSubmenu == null) {
            return this.status;
        } else {
            return this.currentSubmenu.getStatus();
        }
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        if (this.currentSubmenu == null) {
            //call doOneFrame method of the wrapped Animation
            this.decoratedAnimation.doOneFrame(d, dt);

            int lineIndex = 0;
            //submenus
            for (MenuInfo<T> submenuInfo : submenus) {
                String menuKey = submenuInfo.getKey();
                String menuMessage = submenuInfo.getMassage();
                Menu<T> submenu = submenuInfo.getMenu();
                d.drawText(100, 120 + 50 * lineIndex, "(" + menuKey + ") " + menuMessage, 30);
                lineIndex++;
                //if the user selected this submenu, change the current submenu to the appropriate menu;
                if (this.keyboardSensor.isPressed(menuKey)) {
                    this.currentSubmenu = submenu;
                }
            }
            //selections
            for (OptionInfo<T> option : this.options) {
                String selectionKey = option.getKey();
                String selectionMessage = option.getMessage();
                T selectionReturnVal = option.getReturnVal();
                //write on the screen every key with its massage
                //(the massage indicates what will happen if the user will choose this key)
                d.drawText(100, 120 + 50 * lineIndex, "(" + selectionKey + ") " + selectionMessage, 30);
                lineIndex++;
                //if the user selected this selection, change the status to the appropriate returnVal;
                if (this.keyboardSensor.isPressed(selectionKey)) {
                    this.status = selectionReturnVal;
                }
            }
        } else {
            currentSubmenu.doOneFrame(d, dt);
        }
    }

    @Override
    public boolean shouldStop() {
        if (this.currentSubmenu == null) {
            return this.decoratedAnimation.shouldStop();
        } else {
            return this.currentSubmenu.shouldStop();
        }
    }

    //(S and T are same..)
    /**
     * struct to maintain information about one option in a menu.
     * @param <S> type of the status that we expect to get from the menu that contain this option.
     */
    private final class OptionInfo<S> {

        private String key;
        private String message;
        private S returnVal;

        /**
         * @param key       - selection key
         * @param message   - short description of the option
         * @param returnVal - value of the expected menu's status after this option is selected
         */
        private OptionInfo(String key, String message, S returnVal) {
            this.key = key;
            this.message = message;
            this.returnVal = returnVal;
        }

        /**
         * @return selection key
         */
        private String getKey() {
            return this.key;
        }

        /**
         * @return massage
         */
        private String getMessage() {
            return this.message;
        }

        /**
         * @return return value (expected status)
         */
        private S getReturnVal() {
            return this.returnVal;
        }
    }

    /**
     * struct to maintain information about one submenu.
     * @param <S> type of the return value of the submenu
     */
    private final class MenuInfo<S> {
        private String key;
        private String message;
        private Menu<S> menu;

        /**
         * @param key     - selection key
         * @param message - short description of the submenu
         * @param menu    - submenu
         */
        private MenuInfo(String key, String message, Menu<S> menu) {
            this.key = key;
            this.message = message;
            this.menu = menu;
        }

        /**
         * @return selection key
         */
        private String getKey() {
            return this.key;
        }

        /**
         * @return massage
         */
        private String getMassage() {
            return this.message;
        }

        /**
         * @return menu
         */
        private Menu<S> getMenu() {
            return this.menu;
        }
    }
}
