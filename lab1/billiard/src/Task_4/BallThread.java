package Task_4;

public class BallThread extends Thread {
    private final Ball b;
    private final BallThread previousThread;
    private volatile boolean isRunning = true;
    // volatile means that value can be changed by other threads

    public BallThread(Ball ball, BallThread previousThread) {
        this.b = ball;
        this.previousThread = previousThread;
    }

    @Override
    public void run(){
        try {
            if (previousThread != null) {
                previousThread.join();
            }

            while (isRunning){
                b.move();
                System.out.println("Thread name = "
                        + Thread.currentThread().getName());
                Thread.sleep(5);

            }
        } catch(InterruptedException ex) {
            System.out.println("Thread interrupted");
        }
    }

    public void stopRunning() {
        isRunning = false;
    }
}
