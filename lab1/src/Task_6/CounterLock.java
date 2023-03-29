package Task_6;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CounterLock implements ICounter {
    private int value = 0;
    private Lock lock = new ReentrantLock();

    public void increment() {
        lock.lock();
        try {
            value++;
        } finally {
            lock.unlock();
        }
    }

    public void decrement() {
        lock.lock();
        try {
            value--;
        } finally {
            lock.unlock();
        }
    }

    public int getValue() {
        return value;
    }
}

