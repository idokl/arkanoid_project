package animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * animation that can wrap another animation and can stop when a aspecific key is pressed.
 */
public class KeyPressStoppableAnimation implements Animation {

    private KeyboardSensor keyboardSensor;
    private Animation decoratedAnimation;
    private String key;
    private boolean isKeyPressedActively;
    private boolean isAlreadyPressed;

    /**
     * @param sensor    - KeyboardSensor
     * @param key       - single character or one of the static values of the KeyboardSensor interface for special keys
     * @param animation - wrapped animation
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.decoratedAnimation = animation;
        this.key = key;
        this.keyboardSensor = sensor;
        this.isKeyPressedActively = false;
        this.isAlreadyPressed = true;
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        this.decoratedAnimation.doOneFrame(d, dt);
        if (!this.isAlreadyPressed && this.keyboardSensor.isPressed(key)) {
            //if the key hasn't be already pressed but now it is pressed, then it is pressed actively
            this.isKeyPressedActively = true;
        } else if (!this.keyboardSensor.isPressed(key)) {
            //if the key isn't pressed now, update the isAlreadyPressed field (if this field hasn't been updated yet)
            this.isAlreadyPressed = false;
        }
    }

    @Override
    public boolean shouldStop() {
        return (this.decoratedAnimation.shouldStop() || this.isKeyPressedActively);
    }
}