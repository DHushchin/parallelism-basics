package Task_6;

public class CounterSyncMethod implements ICounter {
    private int value = 0;

    public synchronized void increment() {
        value++;
    }

    public synchronized void decrement() {
        value--;
    }

    public int getValue() {
        return value;
    }
}
