package org.prep.example;

import java.util.Stack;

public class MatrixNextStepMapper {

    public static void main(String[] args) {

    }

    static int[][] getMatrix() {
        return new int[][]{
                {7,4, 1},
                {6, 5, 3},
                {8, 9, 7}
                };
    }

    static int[][] movementArray(int[][] toProcess, int startRow, int startCol) {
        int[][] result = new int[toProcess.length][toProcess.length];

        Stack<Pair<RowCol, Integer>> candidates = new Stack<>();
        return result;
    }

    static class RowCol {
        Pair<Integer, Integer> rowColumn;

        RowCol(int row, int col) {
            rowColumn = new Pair<>(row, col);
        }

        int getRow() {
            return rowColumn.left;
        }

        int getColumn() {
            return rowColumn.right;
        }
    }
}
