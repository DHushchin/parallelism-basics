package Task_2;

import java.util.Random;

public class Consumer implements Runnable {
    private Drop drop;

    public Consumer(Drop drop) {
        this.drop = drop;
    }

    public void run() {
        Random random = new Random();
        for (int message = drop.take(); message != -1; message = drop.take()) {
            System.out.format("MESSAGE RECEIVED: %s%n", message);
            try {
                // Sleep for a random amount of time between 0 and 0.5 seconds.
                Thread.sleep(random.nextInt(500));
            } catch (InterruptedException e) {}
        }
    }
}
