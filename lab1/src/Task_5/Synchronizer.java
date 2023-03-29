package Task_5;

public class Synchronizer {
    private volatile boolean permission;
    private int symbols;
    private int lines;
    private volatile boolean stop;

    public Synchronizer() {
        permission = true;
        symbols = 0;
        lines = 0;
        stop = false;
    }

    public boolean getPermission() {
        return permission;
    }

    public boolean isStop() {
        return stop;
    }

    public synchronized void printSymbol(boolean control, char s) throws InterruptedException {
        while (getPermission() != control && !isStop()) {
            wait();
        }

        if(isStop()) {
            notifyAll();
            return;
        }

        System.out.print(s);
        permission = !permission;
        symbols++;

        if (symbols == 100) {
            symbols = 0;
            System.out.println();
            lines++;
        }

        if (lines == 100) {
            stop = true;
        }

        notifyAll();
    }
}