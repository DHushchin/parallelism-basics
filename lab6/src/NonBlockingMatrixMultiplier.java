import mpi.MPI;
import mpi.Request;

import java.util.ArrayList;
public class NonBlockingMatrixMultiplier {
    public static int A_ROWS = 1500;
    public static int A_COLS = 1500;
    public static int B_COLS = 1500;
    public static int MASTER = 0;
    public enum Tags {
        A_FROM_MASTER,
        B_FROM_MASTER,
        ROWS_FROM_MASTER,
        OFFSET_FROM_MASTER,
        C_FROM_WORKER,
        ROWS_FROM_WORKER,
        OFFSET_FROM_WORKER,
    }

    public static boolean PRINT_RESULT = false;
    public static boolean RANDOMIZE_MATRICES = false;
    public static boolean VALIDATE_RESULT = true;

    public static void main(String[] args) {

        int[] rows = {0}, offset = {0};
        Matrix A = new Matrix(A_ROWS, A_COLS);
        Matrix B = new Matrix(A_COLS, B_COLS);
        Matrix C = new Matrix(A_ROWS, B_COLS);

        MPI.Init(args);
        int taskId = MPI.COMM_WORLD.Rank();
        int tasksNumber = MPI.COMM_WORLD.Size();
        int workersNumber = tasksNumber - 1;

        if (tasksNumber < 2) {
            System.err.println("Need at least two MPI tasks. Quitting...");
            MPI.COMM_WORLD.Abort(1);
            return;
        }

        if (taskId == MASTER) {
            System.out.println("MPI_NBMM has started with " + tasksNumber + " tasks.");

            MatrixHelper.initMatrices(A, B, RANDOMIZE_MATRICES);

            long startTime = System.currentTimeMillis();
            int rowsPerTask = A_ROWS / workersNumber;
            int extraRows = A_ROWS % workersNumber;

            for (int destination = 1; destination <= workersNumber; destination++) {
                rows[0] = (destination <= extraRows) ? rowsPerTask + 1 : rowsPerTask;
                var offsetRequest = MPI.COMM_WORLD.Isend(offset, 0, 1, MPI.INT, destination, Tags.OFFSET_FROM_MASTER.ordinal());
                var rowsRequest = MPI.COMM_WORLD.Isend(rows, 0, 1, MPI.INT, destination, Tags.ROWS_FROM_MASTER.ordinal());
                MPI.COMM_WORLD.Isend(A.data, offset[0], rows[0], MPI.OBJECT, destination, Tags.A_FROM_MASTER.ordinal());
                MPI.COMM_WORLD.Isend(B.data, 0, A_COLS, MPI.OBJECT, destination, Tags.B_FROM_MASTER.ordinal());
                offsetRequest.Wait();
                rowsRequest.Wait();
                offset[0] += rows[0];
            }

            ArrayList<Request> subTasksRequests = new ArrayList<>();
            for (int source = 1; source <= workersNumber; source++) {
                var offsetRequest = MPI.COMM_WORLD.Irecv(offset, 0, 1, MPI.INT, source, Tags.OFFSET_FROM_WORKER.ordinal());
                var rowsRequest = MPI.COMM_WORLD.Irecv(rows, 0, 1, MPI.INT, source, Tags.ROWS_FROM_WORKER.ordinal());
                offsetRequest.Wait();
                rowsRequest.Wait();
                subTasksRequests.add(MPI.COMM_WORLD.Irecv(C.data, offset[0], rows[0], MPI.OBJECT, source, Tags.C_FROM_WORKER.ordinal()));
            }

            for (var request : subTasksRequests) {
                request.Wait();
            }

            long endTime = System.currentTimeMillis();

            if (PRINT_RESULT) {
                MatrixHelper.printResult(A, B, C);
            }

            System.out.println("Execution time: " + (endTime - startTime) + "ms");

            if (VALIDATE_RESULT) {
                System.out.println("Result is valid: " + SequentialMatrixMultiplier.validateResult(A, B, C));
            }

        } else {
            var offsetRequest = MPI.COMM_WORLD.Irecv(offset, 0, 1, MPI.INT, MASTER, Tags.OFFSET_FROM_MASTER.ordinal());
            var rowsRequest = MPI.COMM_WORLD.Irecv(rows, 0, 1, MPI.INT, MASTER, Tags.ROWS_FROM_MASTER.ordinal());
            var bRequest = MPI.COMM_WORLD.Irecv(B.data, 0, A_COLS, MPI.OBJECT, MASTER, Tags.B_FROM_MASTER.ordinal());
            offsetRequest.Wait();
            rowsRequest.Wait();
            MPI.COMM_WORLD.Isend(offset, 0, 1, MPI.INT, MASTER, Tags.OFFSET_FROM_WORKER.ordinal());
            MPI.COMM_WORLD.Isend(rows, 0, 1, MPI.INT, MASTER, Tags.ROWS_FROM_WORKER.ordinal());
            var aRequest = MPI.COMM_WORLD.Irecv(A.data, 0, rows[0], MPI.OBJECT, MASTER, Tags.A_FROM_MASTER.ordinal());
            aRequest.Wait();
            bRequest.Wait();

            for (int k = 0; k < B_COLS; k++) {
                for (int i = 0; i < rows[0]; i++) {
                    for (int j = 0; j < A_COLS; j++) {
                        C.data[i][k] += A.data[i][j] * B.data[j][k];
                    }
                }
            }

            MPI.COMM_WORLD.Isend(C.data, 0, rows[0], MPI.OBJECT, MASTER, Tags.C_FROM_WORKER.ordinal());
        }
        MPI.Finalize();
    }
}
