package Task_5;

public class SymbolPrinter implements Runnable {
    private char s;

    public SymbolPrinter(char symbol) {
        this.s = symbol;
    }

    @Override
    public void run() {
        for(int i = 0; i< 100; i++){
            for(int j = 0; j < 100; j++){
                System.out.print(s);
            }
            System.out.println();
        }
    }
}
