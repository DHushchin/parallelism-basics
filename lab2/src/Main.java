import Multiplier.Factory;
import Multiplier.IMultiplier;
import Multiplier.Matrix;


public class Main {
    public static void main(String[] args) {
        Matrix A = new Matrix(1000, 1000);
        Matrix B = new Matrix(1000, 1000);
        Main main = new Main();
        main.test(A, B, "sequential");
        main.test(A, B, "striped");
    }

    public void test(Matrix A, Matrix B, String multiplierType) {
        IMultiplier multiplier = Factory.getMultiplier(multiplierType);
        assert multiplier != null;

        long startTime = System.currentTimeMillis();
        Matrix C = multiplier.multiply(A, B);
        long endTime = System.currentTimeMillis();

        Result result = new Result(C, endTime - startTime);
        result.writeToFile(multiplierType+".txt");
    }
}