package Multiplier.Striped;

import Multiplier.IMultiplier;
import Multiplier.Matrix;

import java.util.List;
import java.util.ArrayList;

public class StripedMultiplier implements IMultiplier {
    @Override
    public Matrix multiply(Matrix A, Matrix B) {
        ArrayList<StripedThread> threads = new ArrayList<>();
        Matrix C = new Matrix(A.getRows(), B.getColumns());

        for (int i = 0; i < A.getRows(); i++) {
            StripedThread t = new StripedThread(A.getRow(i), i, B, C);
            threads.add(t);
            threads.get(i).start();
        }

        for (StripedThread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return C;
    }
}
