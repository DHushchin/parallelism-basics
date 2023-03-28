package Task_6;

public class Main {
    public static void main(String[] args) {
        System.out.println("Counter:");
        testCounter(new Counter());

        System.out.println("CounterSyncMethod:");
        testCounter(new CounterSyncMethod());

        System.out.println("CounterSyncBlock:");
        testCounter(new CounterSyncBlock());
    }

    public static void testCounter(ICounter counter) {

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.decrement();
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(counter.getValue());
    }
}
