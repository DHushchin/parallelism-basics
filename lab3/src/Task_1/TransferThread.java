package Task_1;

import Task_1.Bank.IBank;

class TransferThread extends Thread {
    private final IBank bank;
    private final int fromAccount;
    private final int maxAmount;
    private static final int REPS = 1000;
    public TransferThread(IBank b, int from, int max) {
        bank = b;
        fromAccount = from;
        maxAmount = max;
    }
    @Override
    public void run() {
        try {
            while (true) {
                for (int i = 0; i < REPS; i++) {
                    int toAccount = (int) (bank.size() * Math.random());
                    int amount = (int) (maxAmount * Math.random() / REPS);
                    bank.transfer(fromAccount, toAccount, amount);
                    if (Thread.interrupted()) {
                        throw new InterruptedException();
                    }
                }
            }

        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }
    }
}
