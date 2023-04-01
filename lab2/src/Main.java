import Multiplier.SequentialAlgo;

public class Main {
    public static void main(String[] args) {
        Matrix matrixA = new Matrix(100);
        Matrix matrixB = new Matrix(100);
        SequentialAlgo sequentialAlgo = new SequentialAlgo();
        long startTime = System.currentTimeMillis();
        int[][] matrixC = sequentialAlgo.multiply(matrixA.getMatrix(), matrixB.getMatrix());
        long endTime = System.currentTimeMillis();
        Result result = new Result(matrixC, endTime - startTime);
        result.writeToFile("result.txt");
    }
}