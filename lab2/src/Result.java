import java.io.File;
import java.io.FileWriter;

public class Result {
    private final int[][] matrix;
    private final long time;

    public Result(int[][] matrix, long time) {
        this.matrix = matrix;
        this.time = time;
    }

    public void writeToFile(String fileName) {
        try {
            File file = new File(fileName);
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write("Time: " + time + " ms\r");
            for (int[] ints : matrix) {
                for (int anInt : ints) {
                    fileWriter.write(anInt + " ");
                }
                fileWriter.write("\r");
            }
            fileWriter.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}