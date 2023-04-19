package Multiplier;

public class Matrix {
    private int[][] matrix;
    private int rows;
    private int columns;

    public Matrix(int rows, int columns) {
        this.matrix = new int[rows][columns];
        this.rows = rows;
        this.columns = columns;
        generateMatrix();
    }

    private void generateMatrix() {
        for (int i = 0; i < this.matrix.length; i++) {
            for (int j = 0; j < this.matrix[i].length; j++) {
                this.matrix[i][j] = 1;
            }
        }
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void transpose() {
        int[][] transposedMatrix = new int[this.matrix[0].length][this.matrix.length];
        for (int i = 0; i < this.matrix.length; i++) {
            for (int j = 0; j < this.matrix[i].length; j++) {
                transposedMatrix[j][i] = this.matrix[i][j];
            }
        }
        matrix = transposedMatrix;
    }

    public int[] getColumn(int column) {
        int[] columnArray = new int[this.matrix.length];
        for (int i = 0; i < this.matrix.length; i++) {
            columnArray[i] = this.matrix[i][column];
        }
        return columnArray;
    }

    public void printMatrix() {
        for (int i = 0; i < this.matrix.length; i++) {
            for (int j = 0; j < this.matrix[i].length; j++) {
                System.out.print(this.matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int[] getRow(int row) {
        return this.matrix[row];
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getElem(int row, int column) {
        return this.matrix[row][column];
    }

    public void setElem(int row, int column, int value) {
        this.matrix[row][column] = value;
    }
}
