package Task_1.Bank;

public interface IBank {
    void transfer(int from, int to, int amount);
    int size();
    void test();

    default void incrementActiveThreads() {
    }

    default void decrementActiveThreads() {
    }
}
