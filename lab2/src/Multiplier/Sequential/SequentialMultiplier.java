package Multiplier.Sequential;

import Multiplier.IMultiplier;
import Multiplier.Matrix;

public class SequentialMultiplier implements IMultiplier {

    @Override
    public Matrix multiply(Matrix A, Matrix B) {
        Matrix C = new Matrix(A.getRows(), B.getColumns(), false);

        for (int i = 0; i < A.getRows(); i++) {
            for (int j = 0; j < B.getColumns(); j++) {
                int temp = 0;
                for (int k = 0; k < A.getColumns(); k++) {
                    temp += A.getElem(i, k) * B.getElem(k, j);
                }
                C.setElem(i, j, temp);
            }
        }

        return C;
    }
}
