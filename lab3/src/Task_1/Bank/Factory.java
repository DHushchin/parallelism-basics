package Task_1.Bank;

public class Factory {
    private static final int TEST_TRANSACTION_COUNT = 1000000;
    public static IBank getBank(String type, int numAccounts, int initialBalance) {
        return switch (type) {
            case "SyncMethod" -> new SyncMethod(numAccounts, initialBalance, TEST_TRANSACTION_COUNT);
            case "SyncBlock" -> new SyncBlock(numAccounts, initialBalance, TEST_TRANSACTION_COUNT);
            case "ReentrantLock" -> new ReLock(numAccounts, initialBalance, TEST_TRANSACTION_COUNT);
            case "NoSync" -> new NoSync(numAccounts, initialBalance, TEST_TRANSACTION_COUNT);
            default -> null;
        };
    }
}
