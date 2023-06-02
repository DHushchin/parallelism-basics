import mpi.MPI;

public class BlockingMatrixMultiplier {
    public static int A_ROWS = 1000;
    public static int A_COLS = 1000;
    public static int B_COLS = 1000;
    public static int MASTER = 0;
    public enum Tags {
        FROM_MASTER_TAG,
        FROM_WORKER_TAG
    }

    public static boolean PRINT_RESULT = false;
    public static boolean RANDOMIZE_MATRICES = false;
    public static boolean VALIDATE_RESULT = true;


    public static void main(String[] args) {
        Matrix A = new Matrix(A_ROWS, A_COLS);
        Matrix B = new Matrix(A_COLS, B_COLS);
        Matrix C = new Matrix(A_ROWS, B_COLS);

        MPI.Init(args);
        int[] rows = {0}, offset = {0};
        int taskId = MPI.COMM_WORLD.Rank();
        int tasksNumber = MPI.COMM_WORLD.Size();
        int workersNumber = tasksNumber - 1;

        if (tasksNumber < 2) {
            System.err.println("Need at least two MPI tasks. Quitting...");
            MPI.COMM_WORLD.Abort(1);
            return;
        }

        if (taskId == MASTER) {
            System.out.println("MPI_BMM has started with " + tasksNumber + " tasks.");

            MatrixHelper.initMatrices(A, B, RANDOMIZE_MATRICES);

            long startTime = System.currentTimeMillis();
            int rowsPerTask = A_ROWS / workersNumber;
            int extraRows = A_ROWS % workersNumber;

            for (int destination = 1; destination <= workersNumber; destination++) {
                rows[0] = (destination <= extraRows) ? rowsPerTask + 1 : rowsPerTask;
                MPI.COMM_WORLD.Send(offset, 0, 1, MPI.INT, destination, Tags.FROM_MASTER_TAG.ordinal());
                MPI.COMM_WORLD.Send(rows, 0, 1, MPI.INT, destination, Tags.FROM_MASTER_TAG.ordinal());
                MPI.COMM_WORLD.Send(A.data, offset[0], rows[0], MPI.OBJECT, destination, Tags.FROM_MASTER_TAG.ordinal());
                MPI.COMM_WORLD.Send(B.data, 0, A_COLS, MPI.OBJECT, destination, Tags.FROM_MASTER_TAG.ordinal());
                offset[0] += rows[0];
            }

            for (int source = 1; source <= workersNumber; source++) {
                MPI.COMM_WORLD.Recv(offset, 0, 1, MPI.INT, source, Tags.FROM_WORKER_TAG.ordinal());
                MPI.COMM_WORLD.Recv(rows, 0, 1, MPI.INT, source, Tags.FROM_WORKER_TAG.ordinal());
                MPI.COMM_WORLD.Recv(C.data, offset[0], rows[0], MPI.OBJECT, source, Tags.FROM_WORKER_TAG.ordinal());
            }

            if (PRINT_RESULT) {
                MatrixHelper.printResult(A, B, C);
            }

            long endTime = System.currentTimeMillis();

            System.out.println("Execution time: " + (endTime - startTime) + "ms");

            if (VALIDATE_RESULT) {
                System.out.println("Result is valid: " + SequentialMatrixMultiplier.validateResult(A, B, C));
            }

        } else {
            MPI.COMM_WORLD.Recv(offset, 0, 1, MPI.INT, MASTER, Tags.FROM_MASTER_TAG.ordinal());
            MPI.COMM_WORLD.Recv(rows, 0, 1, MPI.INT, MASTER, Tags.FROM_MASTER_TAG.ordinal());
            MPI.COMM_WORLD.Recv(A.data, 0, rows[0], MPI.OBJECT, MASTER, Tags.FROM_MASTER_TAG.ordinal());
            MPI.COMM_WORLD.Recv(B.data, 0, A_COLS, MPI.OBJECT, MASTER, Tags.FROM_MASTER_TAG.ordinal());

            for (int k = 0; k < B_COLS; k++) {
                for (int i = 0; i < rows[0]; i++) {
                    for (int j = 0; j < A_COLS; j++) {
                        C.data[i][k] += A.data[i][j] * B.data[j][k];
                    }
                }
            }

            MPI.COMM_WORLD.Send(offset, 0, 1, MPI.INT, MASTER, Tags.FROM_WORKER_TAG.ordinal());
            MPI.COMM_WORLD.Send(rows, 0, 1, MPI.INT, MASTER, Tags.FROM_WORKER_TAG.ordinal());
            MPI.COMM_WORLD.Send(C.data, 0, rows[0], MPI.OBJECT, MASTER, Tags.FROM_WORKER_TAG.ordinal());
        }

        MPI.Finalize();
    }
}