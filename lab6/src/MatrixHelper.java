public class MatrixHelper {
    public static void initMatrices (Matrix A, Matrix B, boolean RANDOMIZE_MATRICES) {
        if (RANDOMIZE_MATRICES) {
            A.initializeWithRandom(50, 500);
            B.initializeWithRandom(50, 500);
        } else {
            A.initializeWithNumber(10);
            B.initializeWithNumber(10);
        }
    }

    public static void printResult (Matrix A, Matrix B, Matrix C) {
        System.out.println("Matrix A:");
        A.print();
        System.out.println("Matrix B:");
        B.print();
        System.out.println("Matrix C:");
        C.print();
    }
}
