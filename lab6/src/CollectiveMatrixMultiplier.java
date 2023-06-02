import mpi.MPI;

public class CollectiveMatrixMultiplier {
    public static int A_ROWS = 1000;
    public static int A_COLS = 1000;
    public static int B_COLS = 1000;
    public static int MASTER = 0;

    public static boolean PRINT_RESULT = false;
    public static boolean RANDOMIZE_MATRICES = false;
    public static boolean VALIDATE_RESULT = true;

    public static void main(String[] args) {
        Matrix A = new Matrix(A_ROWS, A_COLS);
        Matrix B = new Matrix(A_COLS, B_COLS);
        Matrix C = new Matrix(A_ROWS, B_COLS);

        MPI.Init(args);
        int taskId = MPI.COMM_WORLD.Rank();
        int tasksNumber = MPI.COMM_WORLD.Size();

        if (tasksNumber < 2) {
            System.err.println("Need at least two MPI tasks. Quitting...");
            MPI.COMM_WORLD.Abort(1);
            return;
        }

        long startTime = 0;

        if (taskId == MASTER) {
            System.out.println("MPI_CMM has started with " + tasksNumber + " tasks.");
            startTime = System.currentTimeMillis();
            MatrixHelper.initMatrices(A, B, RANDOMIZE_MATRICES);
        }

        int rowsPerTask = A_ROWS / tasksNumber;
        int extraRows = A_ROWS % tasksNumber;

        var rowsCounts = new int[tasksNumber];
        var rowsOffsets = new int[tasksNumber];

        for (int i = 0; i < tasksNumber; i++) {
            rowsCounts[i] = i < extraRows ? rowsPerTask + 1 : rowsPerTask;
            rowsOffsets[i] = i == 0 ? 0 : rowsOffsets[i - 1] + rowsCounts[i - 1];
        }

        var rowsInTask = rowsCounts[taskId];

        var aRowsBuffer = new int[rowsInTask][A_COLS];

        MPI.COMM_WORLD.Scatterv(
                A.data,
                0,
                rowsCounts,
                rowsOffsets,
                MPI.OBJECT,
                aRowsBuffer,
                0,
                rowsInTask,
                MPI.OBJECT,
                MASTER
        );

        MPI.COMM_WORLD.Bcast(B.data, 0, A_COLS, MPI.OBJECT, MASTER);

        var cRowsBuffer = new int[rowsInTask][B_COLS];

        for (int k = 0; k < B_COLS; k++) {
            for (int i = 0; i < rowsInTask; i++) {
                for (int j = 0; j < A_COLS; j++) {
                    cRowsBuffer[i][k] += aRowsBuffer[i][j] * B.data[j][k];
                }
            }
        }

        MPI.COMM_WORLD.Gatherv(
                cRowsBuffer,
                0,
                rowsInTask,
                MPI.OBJECT,
                C.data,
                0,
                rowsCounts,
                rowsOffsets,
                MPI.OBJECT,
                MASTER
        );

        if (taskId == MASTER) {
            long endTime = System.currentTimeMillis();
            System.out.println("MPI_CMM has finished in " + (endTime - startTime) + "ms.");

            if (PRINT_RESULT) {
                MatrixHelper.printResult(A, B, C);
            }

            if (VALIDATE_RESULT) {
                System.out.println("Result is valid: " + SequentialMatrixMultiplier.validateResult(A, B, C));
            }
        }

        MPI.Finalize();
    }
}
