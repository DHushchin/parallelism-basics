import Multiplier.Factory;
import Multiplier.IMultiplier;
import Multiplier.Matrix;

public class Main {
    public static void main(String[] args) {
        int[] sizes = {500, 1000, 1500, 2000};
        int[] threads = {4, 16, 32, 64};
//        for (int i : sizes) {
//            System.out.println("-----" + i + "x" + i + "-----");
//
//            Matrix A = new Matrix(i, i, true);
//            Matrix B = new Matrix(i, i, true);
//            Main main = new Main();
//            long sequentialTime = main.test(A, B, "sequential", 100);
//            long stripedTime = main.test(A, B, "striped", 100);
//            long foxTime = main.test(A, B, "fox", 100);
//
//            System.out.println("Sequential time: " + sequentialTime + " ms");
//            System.out.println("Striped time: " + stripedTime + " ms");
//            System.out.println("Fox time: " + foxTime + " ms");
//
//            System.out.println("Speedup of striped: " + (double) sequentialTime / stripedTime);
//            System.out.println("Speedup of fox: " + (double) sequentialTime / foxTime);
//        }

        for (int i : threads) {
            System.out.println("-----" + i + " threads-----");

            Matrix A = new Matrix(1500, 1500, true);
            Matrix B = new Matrix(1500, 1500, true);
            Main main = new Main();

            long sequentialTime = main.test(A, B, "sequential", i);
            long stripedTime = main.test(A, B, "striped", i);
            long foxTime = main.test(A, B, "fox", i);

            System.out.println("Sequential time: " + sequentialTime + " ms");
            System.out.println("Striped time: " + stripedTime + " ms");
            System.out.println("Fox time: " + foxTime + " ms");

            System.out.println("Speedup of striped: " + (double) sequentialTime / stripedTime);
            System.out.println("Speedup of fox: " + (double) sequentialTime / foxTime);
        }
    }

    public long test(Matrix A, Matrix B, String multiplierType, int threads) {
        IMultiplier multiplier = Factory.getMultiplier(multiplierType, threads);
        assert multiplier != null;

        long startTime = System.currentTimeMillis();
        Matrix C = multiplier.multiply(A, B);
        long endTime = System.currentTimeMillis();

        Result result = new Result(C, endTime - startTime);
        result.writeToFile("data/" + multiplierType + ".txt");

        return endTime - startTime;
    }
}