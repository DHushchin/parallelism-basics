package Multiplier.Striped;

import Multiplier.IMultiplier;
import Multiplier.Matrix;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StripedMultiplier implements IMultiplier {
    @Override
    public Matrix multiply(Matrix A, Matrix B) {

        if (A.getColumns() != B.getRows())
            throw new IllegalArgumentException("Multiplier.Matrix A's column count must match matrix B's row count.");

        Matrix C = new Matrix(A.getRows(), B.getColumns(), false);

        int[][] rowsA = new int[A.getRows()][A.getColumns()];
        for (int i = 0; i < A.getRows(); i++) {
            rowsA[i] = A.getRow(i);
        }

        int[][] columnsB = new int[B.getColumns()][B.getRows()];
        for (int i = 0; i < B.getColumns(); i++) {
            columnsB[i] = B.getColumn(i);
        }

        ExecutorService executor = Executors.newFixedThreadPool(100);
        List<Callable<Object>> todo = new ArrayList<>(A.getRows());


        for (int i = 0; i < A.getRows(); i++) {
            for (int j = 0; j < A.getRows(); j++) {
                int second_num = (j + i) % A.getRows();
                todo.add(Executors.callable(new StripedThread(C, rowsA[i], columnsB[second_num], i, second_num)));
            }
        }

        try {
            executor.invokeAll(todo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executor.shutdown();

        return C;
    }
}

