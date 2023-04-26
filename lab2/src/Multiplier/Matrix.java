package Multiplier;

public class Matrix {
    private final int[][] matrix;
    private final int rows;
    private final int columns;

    public Matrix(int rows, int columns, boolean generate) {
        this.matrix = new int[rows][columns];
        this.rows = rows;
        this.columns = columns;
        if (generate) {
            generateOneMatrix();
        }
    }

    private void generateOneMatrix() {
//        for (int i = 0; i < this.matrix.length; i++) {
//            for (int j = 0; j < this.matrix[i].length; j++) {
//                this.matrix[i][j] = 1;
//            }
//        }

        int count = 1;
        for (int i = 0; i < this.matrix.length; i++) {
            for (int j = 0; j < this.matrix[i].length; j++) {
                this.matrix[i][j] = count;
                count++;
            }
        }
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

    public void setBlock(int row, int col, int[][] block) {
        int rowStart = row * block.length;
        int colStart = col * block[0].length;

        for (int i = 0; i < block.length && rowStart + i < rows; i++) {
            for (int j = 0; j < block[0].length && colStart + j < columns; j++) {
                matrix[rowStart + i][colStart + j] = block[i][j];
            }
        }
    }

    public int[][] getBlock(int row, int col, int blockSize) {
        int[][] block = new int[blockSize][blockSize];
        int rowStart = row * blockSize;
        int colStart = col * blockSize;

        if (rowStart >= rows || colStart >= columns) {
            return block;
        }

        for (int i = 0; i < blockSize && rowStart + i < rows; i++) {
            for (int j = 0; j < blockSize && colStart + j < columns; j++) {
                block[i][j] = matrix[rowStart + i][colStart + j];
            }
        }

        return block;
    }

    public int[][][][] getBlocks(int blockSize) {
        int[][][][] blocks = new int[rows / blockSize][columns / blockSize][blockSize][blockSize];

        for (int i = 0; i < rows / blockSize; i++) {
            for (int j = 0; j < columns / blockSize; j++) {
                blocks[i][j] = getBlock(i, j, blockSize);
            }
        }

        return blocks;
    }

    public void setBlocks(int[][][][] blocks, int blockSize) {
        for (int i = 0; i < rows / blockSize; i++) {
            for (int j = 0; j < columns / blockSize; j++) {
                setBlock(i, j, blocks[i][j]);
            }
        }
    }
}
