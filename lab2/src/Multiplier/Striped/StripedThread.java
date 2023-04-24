package Multiplier.Striped;

import Multiplier.Matrix;

public class StripedThread extends Thread {
    private final int[] rowA;
    private final int[] rowB;
    private final int i;
    private final int j;
    private final Matrix result;

    public StripedThread(Matrix result, int[] rowA, int[] rowB, int i, int j) {
        this.result = result;
        this.rowA = rowA;
        this.rowB = rowB;
        this.i = i;
        this.j = j;
    }

    @Override
    public void run() {
        int temp = 0;
        for (int k = 0; k < this.rowA.length; k++) {
            temp += this.rowA[k] * this.rowB[k];
        }
        this.result.setElem(this.i, this.j, temp);
    }
}
