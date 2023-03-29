package Task_2;

public class BallThread extends Thread {
    private final Ball b;
    private volatile boolean isRunning = true;
    // volatile means that value can be changed by other threads

    public BallThread(Ball ball){
        b = ball;
    }

    @Override
    public void run(){
        try{
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
