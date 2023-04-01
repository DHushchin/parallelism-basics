package Multiplier;

public class SequentialAlgo {

    public int[][]  multiply(int[][] matrixA, int[][] matrixB) {
        int[][] matrixC = new int[matrixA.length][matrixB[0].length];

        for (int i = 0; i < matrixA.length; i++) {
            for (int j = 0; j < matrixB[0].length; j++) {
                for (int k = 0; k < matrixA[0].length; k++) {
                    matrixC[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }

        return matrixC;
    }
}
