package Task_1.Bank;

import java.util.Arrays;

public class SyncBlock implements IBank {
    public final int TEST_TRANSACTION_COUNT;
    private final int[] accounts;
    private long transactionCount = 0;

    public SyncBlock(int numAccounts, int initialBalance, int TEST_TRANSACTION_COUNT){
        accounts = new int[numAccounts];
        Arrays.fill(accounts, initialBalance);
        this.TEST_TRANSACTION_COUNT = TEST_TRANSACTION_COUNT;
    }

    public void transfer(int from, int to, int amount) {
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0;

        synchronized (this) {
            while(accounts[from] < amount){
                try {
                    long remainingTime = 5000 - elapsedTime;

                    if (remainingTime <= 0) {
                        Thread.currentThread().interrupt();
                        return;
                    }

                    wait(remainingTime);
                    elapsedTime = System.currentTimeMillis() - startTime;
                } catch (InterruptedException e) {
                    // Если поток был прерван во время ожидания
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("Thread interrupted");
                        return;
                    }
                }
            }

            accounts[from] -= amount;
            accounts[to] += amount;
            transactionCount++;
            notifyAll();

            if (transactionCount % TEST_TRANSACTION_COUNT == 0)
                test();
        }
    }

    public void test(){
        int sum = 0;
        for (int account : accounts) {
            sum += account;
        }
        System.out.println("Transactions: " + transactionCount + " Sum: " + sum);
    }

    public int size(){
        return accounts.length;
    }
}
