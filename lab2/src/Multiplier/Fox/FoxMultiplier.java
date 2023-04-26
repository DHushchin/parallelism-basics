package Multiplier.Fox;

import Multiplier.IMultiplier;
import Multiplier.Matrix;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FoxMultiplier implements IMultiplier {
    private final int blockSize;

    public FoxMultiplier(int blockSize) {
        this.blockSize = blockSize;
    }

    @Override
    public Matrix multiply(Matrix A, Matrix B) {
        int n = A.getRows();
        Matrix C = new Matrix(n, n, false);

        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<Callable<Object>> todo = new ArrayList<>();

        int numBlocks = n / blockSize;

        int[][][][] ABlocks = A.getBlocks(blockSize);
        int[][][][] BBlocks = B.getBlocks(blockSize);
        int[][][][] CBlocks = C.getBlocks(blockSize);

        for (int i = 0; i < numBlocks; i++) {
            for (int j = 0; j < numBlocks; j++) {
                for (int k = 0; k < numBlocks; k++) {
                    todo.add(Executors.callable(new FoxThread(ABlocks, BBlocks, CBlocks, i, j, k)));
                }
            }
        }

        try {
            executor.invokeAll(todo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // merge blocks
        for (int i = 0; i < numBlocks; i++) {
            for (int j = 0; j < numBlocks; j++) {
                C.setBlock(i, j, CBlocks[i][j]);
            }
        }

        executor.shutdown();

        return C;
    }
}
