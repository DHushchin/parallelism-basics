package Task_6;

import java.util.concurrent.atomic.AtomicInteger;

public class CounterAtomic implements ICounter {
    private AtomicInteger count = new AtomicInteger(0);

    public void increment() {
        count.incrementAndGet();
    }

    public void decrement() {
        count.decrementAndGet();
    }

    public int getValue() {
        return count.get();
    }
}