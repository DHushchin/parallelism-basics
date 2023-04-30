package Task_2;

import java.util.Random;

public class Producer implements Runnable {
    private Drop drop;

    public Producer(Drop drop) {
        this.drop = drop;
    }

    public void run() {
        int[] importantInfo = generateData(100);
        Random random = new Random();

        for (int i = 0; i < importantInfo.length; i++) {
            drop.put(importantInfo[i]);
            try {
                // Sleep for a random amount of time between 0 and 0.5 seconds.
                Thread.sleep(random.nextInt(500));
            } catch (InterruptedException e) {}
        }
        drop.put(-1);
    }

    private int[] generateData(int arraySize) {
        int[] data = new int[arraySize];
        for (int i = 0; i < arraySize; i++) {
            data[i] = i;
        }
        return data;
    }
}
