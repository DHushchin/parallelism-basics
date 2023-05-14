package Task_1;

import java.util.ArrayList;

import Task_1.Bank.Factory;
import Task_1.Bank.IBank;
public class AsyncBankTest {
    public static final int ACCOUNTS = 10;
    public static final int INITIAL_BALANCE = 10000;
    public static void main(String[] args) {
        AsyncBankTest test = new AsyncBankTest();
        test.test("NoSync");
        test.test("SyncMethod");
        test.test("SyncBlock");
        test.test("ReentrantLock");
    }

    public void test(String type) {
        long startTime = System.currentTimeMillis();
        System.out.println(type);
        IBank b = Factory.getBank(type, ACCOUNTS, INITIAL_BALANCE);

        ArrayList<TransferThread> threads = new ArrayList<>();

        for (int i = 0; i < ACCOUNTS; i++) {
            TransferThread thread = new TransferThread(b, i, INITIAL_BALANCE);
            thread.setPriority(Thread.NORM_PRIORITY + i % 2);
            thread.start();
            threads.add(thread);
        }

        for (TransferThread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted");
            }
        }

        long endTime = System.currentTimeMillis();

        System.out.println("Total time: " + (endTime - startTime) + "ms" + "\n");
    }
}
