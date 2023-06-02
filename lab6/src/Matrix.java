public class Matrix {
    public int[][] data;
    public int rows;
    public int cols;

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new int[rows][cols];
    }

    public void initializeWithNumber(int number) {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++)
                this.data[i][j] = number;
        }
    }

    public void initializeWithRandom(int min, int max) {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++)
                this.data[i][j] = (int) (Math.random() * (max - min + 1) + min);
        }
    }

    public void print() {
        for (int i = 0; i < this.rows; i++) {
            System.out.print("[");
            for (int j = 0; j < this.cols; j++)
                if (j == this.cols - 1)
                    System.out.print(this.data[i][j]);
                else
                System.out.print(this.data[i][j] + " ");
            System.out.println("]");
        }
        System.out.println('\n');
    }

}
