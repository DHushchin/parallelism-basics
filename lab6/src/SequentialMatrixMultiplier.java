public class SequentialMatrixMultiplier {
    public static int A_ROWS = 1000;
    public static int A_COLS = 1000;
    public static int B_COLS = 1000;

    public static boolean PRINT_RESULT = false;
    public static boolean RANDOMIZE_MATRICES = false;

    public static void main(String[] args) {
        Matrix A = new Matrix(A_ROWS, A_COLS);
        Matrix B = new Matrix(A_COLS, B_COLS);

        MatrixHelper.initMatrices(A, B, RANDOMIZE_MATRICES);

        long startTime = System.currentTimeMillis();
        Matrix C = multiply(A, B);
        long endTime = System.currentTimeMillis();

        if (PRINT_RESULT) {
            MatrixHelper.printResult(A, B, C);
        }

        System.out.println("SequentialMatrixMultiplier has finished in " + (endTime - startTime) + "ms.");
    }

    public static boolean validateResult(Matrix A, Matrix B, Matrix C) {
        Matrix D = multiply(A, B);

        for (int i = 0; i < A_ROWS; i++)
            for (int j = 0; j < B_COLS; j++)
                if (C.data[i][j] != D.data[i][j])
                    return false;

        return true;
    }

    private static Matrix multiply (Matrix A, Matrix B) {
        Matrix C = new Matrix(A.rows, B.cols);
        for (int i = 0; i < A.rows; i++) {
            for (int j = 0; j < B.cols; j++) {
                int sum = 0;
                for (int k = 0; k < A.cols; k++)
                    sum += A.data[i][k] * B.data[k][j];
                C.data[i][j] = sum;
            }
        }
        return C;
    }
}
