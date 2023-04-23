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
        Matrix C = new Matrix(A.getRows(), B.getColumns(), false);

        int[][] rowsA = new int[A.getRows()][A.getColumns()];
        for (int i = 0; i < A.getRows(); i++) {
            rowsA[i] = A.getRow(i);
        }

        int[][] columnsB = new int[B.getColumns()][B.getRows()];
        for (int i = 0; i < B.getColumns(); i++) {
            columnsB[i] = B.getColumn(i);
        }

        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<Callable<Object>> todo = new ArrayList<>(A.getRows());

        // Кожний процес пам’ятає тільки один рядок матриці А та один стовпчик матриці В на кожній ітерації.
        // Після кожної ітерації виконується циклічна передача стовпчиків матриці В між процесами

        for (int i = 0; i < A.getRows(); i++) {
            int finalI = i;
            todo.add(() -> {
                for (int j = 0; j < B.getColumns(); j++) {
                    int temp = 0;
                    for (int k = 0; k < A.getColumns(); k++) {
                        temp += rowsA[finalI][k] * columnsB[j][k];
                    }
                    C.setElem(finalI, j, temp);
                }
                return null;
            });
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

