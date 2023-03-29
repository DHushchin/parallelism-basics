package Task_5;

public class SymbolPrinterSync implements Runnable {
    private char s;
    private Synchronizer sync;
    private boolean controlValue;

    public SymbolPrinterSync(char symbol, Synchronizer permission, boolean control) {
        this.s = symbol;
        this.sync = permission;
        this.controlValue = control;
    }

    @Override
    public void run() {
        try {
            while (!sync.isStop()) {
                sync.printSymbol(controlValue, s);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

