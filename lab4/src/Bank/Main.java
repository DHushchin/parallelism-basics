package Bank;

import java.util.ArrayList;
import java.util.concurrent.ForkJoinTask;

public class Main {
    public static final int ACCOUNTS_NUMBER = 1000;
    public static final int INITIAL_BALANCE = 10000;

    public static void main(String[] args) throws InterruptedException {
        testThreads();
        testPool();
    }

    public static void testPool() {
        Bank bank = new Bank(ACCOUNTS_NUMBER, INITIAL_BALANCE);

        ArrayList<TransferMoneyTask> transferMoneyTasks = new ArrayList<>();

        for (int i = 0; i < ACCOUNTS_NUMBER; i++) {
            TransferMoneyTask t = new TransferMoneyTask(bank, i, INITIAL_BALANCE);
            transferMoneyTasks.add(t);
        }

        long start = System.currentTimeMillis();
        ForkJoinTask.invokeAll(transferMoneyTasks);
        long end = System.currentTimeMillis();

        System.out.println("Pool time: " + (end - start) + " ms");
    }

    public static void testThreads() throws InterruptedException {
        Bank bank = new Bank(ACCOUNTS_NUMBER, INITIAL_BALANCE);

        ArrayList<TransferMoneyThread> threads = new ArrayList<>();

        for (int i = 0; i < ACCOUNTS_NUMBER; i++) {
            TransferMoneyThread t = new TransferMoneyThread(bank, i, INITIAL_BALANCE);
            threads.add(t);
        }

        long start = System.currentTimeMillis();
        for (TransferMoneyThread thread : threads) {
            thread.start();
        }
        for (TransferMoneyThread thread : threads) {
            thread.join();
        }
        long end = System.currentTimeMillis();

        System.out.println("Threads time: " + (end - start) + " ms");
    }
}
