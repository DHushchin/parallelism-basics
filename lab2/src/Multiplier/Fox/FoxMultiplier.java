package Multiplier.Fox;

import Multiplier.IMultiplier;
import Multiplier.Matrix;

public class FoxMultiplier implements IMultiplier {

    @Override
    public Matrix multiply(Matrix matrixA, Matrix matrixB) {
        Matrix matrixC = new Matrix(matrixA.getRows(), matrixB.getColumns());

        if (matrixA.getColumns() != matrixB.getRows())
            throw new IllegalArgumentException("Multiplier.Matrix A's column count must match matrix B's row count.");

        return matrixC;
    }
}
