package Multiplier.Fox;

import java.util.concurrent.Callable;

public class FoxThread implements Callable<Object> {
    private final int[][][][] ABlocks;
    private final int[][][][] BBlocks;
    private final int[][][][] CBlocks;
    private final int i;
    private final int j;
    private final int k;

    public FoxThread(int[][][][] ABlocks, int[][][][] BBlocks, int[][][][] CBlocks, int i, int j, int k) {
        this.ABlocks = ABlocks;
        this.BBlocks = BBlocks;
        this.CBlocks = CBlocks;
        this.i = i;
        this.j = j;
        this.k = k;
    }

    @Override
    public Object call() {
        int blockSize = ABlocks[0][0].length;

        int[][] a = ABlocks[i][j];
        int[][] b = BBlocks[j][k];
        int[][] c = CBlocks[i][k];

        for (int ii = 0; ii < blockSize; ii++) {
            for (int jj = 0; jj < blockSize; jj++) {
                for (int kk = 0; kk < blockSize; kk++) {
                    c[ii][jj] += a[ii][kk] * b[kk][jj];
                }
            }
        }

        CBlocks[i][k] = c;

        return null;
    }
}
