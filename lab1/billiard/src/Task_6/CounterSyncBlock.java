package Task_6;

public class CounterSyncBlock implements ICounter {
    private int value = 0;

    public void increment() {
        synchronized (this) {
            value++;
        }
    }

    public void decrement() {
        synchronized (this) {
            value--;
        }
    }

    public int getValue() {
        return value;
    }
}
