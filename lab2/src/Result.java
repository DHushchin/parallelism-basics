import java.io.File;
import java.io.FileWriter;

import Multiplier.Matrix;

public class Result {
    private final Matrix matrix;
    private final long time;

    public Result(Matrix matrix, long time) {
        this.matrix = matrix;
        this.time = time;
    }

    public void writeToFile(String fileName) {
        try {
            File file = new File(fileName);
            FileWriter fileWriter = new FileWriter(file);

            fileWriter.write("Time: " + time + " ms\r");
            fileWriter.write("Matrix:\r");

            for (int i = 0; i < matrix.getRows(); i++) {
                for (int j = 0; j < matrix.getColumns(); j++) {
                    fileWriter.write(matrix.getElem(i, j) + " ");
                }
                fileWriter.write("\r");
            }

            fileWriter.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}