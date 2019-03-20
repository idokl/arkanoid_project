package arkanoid;

import biuoop.KeyboardSensor;

/**
 * Created by ohad on 10/07/2017.
 */
public class MyKS implements KeyboardSensor {
    private KeyboardSensor ks;
    String LEFT_KEY = "right";
    String RIGHT_KEY = "left";

    public MyKS(KeyboardSensor ks){
        ks = ks;
    }

    @Override
    public boolean isPressed(String s) {

        return false;
    }
}
