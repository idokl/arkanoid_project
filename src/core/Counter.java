package core;

/**
 * Object that saves a numerical value (integer) and supplies methods that enable to increase/decrease it.
 */
public class Counter {
    private int value = 0;

    /**
     * @param value - the initial integer the counter saves (=the number that the count starts from)
     */
    public Counter(int value) {
        this.value = value;
    }

    /**
     * add number to current count.
     *
     * @param number - number to add to the current count
     */
    public void increase(int number) {
        this.value += number;
    }

    /**
     * subtract number from current count.
     *
     * @param number - number to subtract from the current count
     */
    public void decrease(int number) {
        this.value -= number;

    }

    /**
     * get current count.
     *
     * @return the current count (the saved number).
     */
    public int getValue() {
        return this.value;
    }
}