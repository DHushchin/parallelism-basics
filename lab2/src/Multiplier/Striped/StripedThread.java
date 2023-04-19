package Multiplier.Striped;

import Multiplier.Matrix;

public class StripedThread extends Thread {
        private final int rowIndex;
        private final int[] row;
        private final Matrix B;
        private final Matrix result;

        public StripedThread(int[] row, int rowIndex, Matrix B, Matrix result) {
            this.row = row;
            this.rowIndex = rowIndex;
            this.B = B;
            this.result = result;
        }

        @Override
        public void run() {
            for (int j = 0; j < B.getColumns(); j++) {
                int temp = 0;
                for (int i = 0; i < B.getRows(); i++) {
                    temp += row[i] * B.getElem(i, j);
                }
                this.result.setElem(rowIndex, j, temp);
            }
        }
    }
