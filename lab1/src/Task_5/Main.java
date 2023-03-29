package Task_5;

public class Main {
    public static void main(String[] args) {
        boolean sync = true;
        System.out.println("Sync: " + sync);

        if (sync) {
            Synchronizer permission = new Synchronizer();
            SymbolPrinterSync dashPrinter = new SymbolPrinterSync('-', permission, true);
            SymbolPrinterSync pipePrinter = new SymbolPrinterSync('|', permission, false);

            Thread dashThread = new Thread(dashPrinter);
            Thread pipeThread = new Thread(pipePrinter);

            dashThread.start();
            pipeThread.start();
        } else {
            Thread dashThread = new Thread(new SymbolPrinter('-'));
            Thread pipeThread = new Thread(new SymbolPrinter('|'));

            dashThread.start();
            pipeThread.start();
        }
    }
}

