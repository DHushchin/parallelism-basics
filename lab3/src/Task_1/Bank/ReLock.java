package Task_1.Bank;

import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReLock implements IBank {
    public final int TEST_TRANSACTION_COUNT;
    private final int[] accounts;
    private long transactionCount = 0;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition sufficientFunds = lock.newCondition();

    public ReLock(int numAccounts, int initialBalance, int TEST_TRANSACTION_COUNT){
        accounts = new int[numAccounts];
        Arrays.fill(accounts, initialBalance);
        this.TEST_TRANSACTION_COUNT = TEST_TRANSACTION_COUNT;
    }

    public void transfer(int from, int to, int amount) {
        lock.lock();
        try {
            while (accounts[from] < amount) {
                sufficientFunds.await();
            }

            accounts[from] -= amount;
            accounts[to] += amount;
            transactionCount++;

            sufficientFunds.signalAll();

            if (transactionCount % TEST_TRANSACTION_COUNT == 0) {
                test();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
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
