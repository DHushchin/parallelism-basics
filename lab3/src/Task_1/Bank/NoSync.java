package Task_1.Bank;

import java.util.Arrays;

public class NoSync implements IBank {
    public final int TEST_TRANSACTION_COUNT;
    private final int[] accounts;
    private long transactionCount;

    public NoSync(int numAccounts, int initialBalance, int TEST_TRANSACTION_COUNT){
        accounts = new int[numAccounts];
        for (int i = 0; i < accounts.length; i++) {
            accounts[i] = initialBalance;
        }
        this.TEST_TRANSACTION_COUNT = TEST_TRANSACTION_COUNT;
        transactionCount = 0;
    }

    public void transfer(int from, int to, int amount) {
            accounts[from] -= amount;
            accounts[to] += amount;
            transactionCount++;
            if (transactionCount % TEST_TRANSACTION_COUNT == 0)
                test();
    }

    public void test() {
        int sum = 0;
        for (int i = 0; i < accounts.length; i++) {
            sum += accounts[i];
        }
        System.out.println("Transactions: " + transactionCount + " Sum: " + sum);
    }

    public int size(){
        return accounts.length;
    }
}

