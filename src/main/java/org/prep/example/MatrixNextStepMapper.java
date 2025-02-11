package org.prep.example;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class MatrixNextStepMapper {

    public static void main(String[] args) {

        System.out.println("matrix: "+ Arrays.deepToString(getMatrix()));
        System.out.println("result: "+Arrays.deepToString(movementArray(getMatrix(), 1, 1)));
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
        for (int i=0; i<toProcess.length; i++) {
            for (int j=0; j<toProcess[i].length; j++) {
                result[i][j] = -1;
            }
        }
        result[startRow][startCol] = 0;
        Stack<Pair<RowCol, Integer>> candidates = new Stack<>();
        candidates.push(new Pair<>(new RowCol(startRow, startCol), 1));
        while (!candidates.isEmpty()) {
            Pair<RowCol, Integer> current = candidates.pop();
            List<Pair<Integer,Integer>> posns = List.of(
                    new Pair(current.left.getRow()-1,current.left.getColumn()),
                    new Pair(current.left.getRow()+1,current.left.getColumn()),
                    new Pair(current.left.getRow(),current.left.getColumn()-1),
                    new Pair(current.left.getRow(),current.left.getColumn()+1)
            );
            for (Pair<Integer,Integer> aPosn : posns) {
                int row = aPosn.left;
                if (row < 0 || row >= toProcess.length) {
                    continue;
                }
                int column = aPosn.right;
                if (column < 0 || column >= toProcess[row].length) {
                    continue;
                }
                if (toProcess[row][column] < toProcess[startRow][startCol] && result[row][column] == (-1)) {
                    result[row][column] = current.right;
                    candidates.push(new Pair<>(new RowCol(row,column), current.right+1));
                }
            }
        }

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
