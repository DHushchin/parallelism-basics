package Multiplier.Sequential;

import Multiplier.IMultiplier;
import Multiplier.Matrix;

public class SequentialMultiplier implements IMultiplier {

    @Override
    public Matrix multiply(Matrix A, Matrix B) {
        Matrix C = new Matrix(A.getRows(), B.getColumns());
        for (int i = 0; i < A.getRows(); i++) {
            for (int j = 0; j < B.getColumns(); j++) {
                for (int k = 0; k < A.getColumns(); k++) {
                    C.setElem(i, j, C.getElem(i, j) + A.getElem(i, k) * B.getElem(k, j));
                }
            }
        }
        return C;
    }
}
