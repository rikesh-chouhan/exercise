package org.prep.example;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class GridFinder {

    static Random random = new Random(System.currentTimeMillis());
    static final AbstractMap.SimpleEntry<Integer, Integer> entry = new AbstractMap.SimpleEntry(0, 0);
    static final int limit = 16, rows = 16, cols = 16, gridLimit = 4;

    public static void main(String[] args) {

        final int[][] array = new int[rows][cols];
        List<Pair<Integer,Integer>> indexes = new ArrayList();

        for (int i = 0; i< rows; i++) {
            final int lambdaCounter = i;
            entry.setValue(0);
            IntStream.generate(() -> random.nextInt(1000)).limit(limit).forEach(num -> {
                if (num % 13 == 0) {
                    indexes.add(new Pair(lambdaCounter, entry.getValue()));
                }
                array[lambdaCounter][entry.getValue()] = num;
                entry.setValue(entry.getValue() + 1);
                System.out.printf("%d%s", Math.abs(num), entry.getValue() < limit ? "," : "\n");
                }
            );
        }
        /*
        int[][] testCord = {{1, 4}, {5, 5}, {1, 6}, {2, 2}};
        for (int i = 0; i < testCord.length; i++) {
            Pair<Integer, Integer> point = new Pair(testCord[i][0], testCord[i][1]);
            Pair<Integer, Integer> start = getGridStartCoordinate(testCord[i][0], testCord[i][1], 3);
            Pair<Integer, Integer> end = getGridEndCoordinate(testCord[i][0], testCord[i][1], 3);
            System.out.printf("(x,y) = (%d,%d) start: (%d,%d) end: (%d,%d)\n",
                    point.getLeft(), point.getRight(),
                    start.getLeft(), start.getRight(),
                    end.getLeft(), end.getRight()
            );
        }

         */
        for (int i=0; i<indexes.size(); i++) {
            Pair<Integer, Integer> point = new Pair(indexes.get(i).left + 1, indexes.get(i).right + 1);
            Pair<Integer, Integer> start = getGridStartCoordinate(point.left, point.right, gridLimit);
            Pair<Integer, Integer> end = getGridEndCoordinate(point.left, point.right, gridLimit);
            System.out.printf("(x,y) = (%d,%d) start: (%d,%d) end: (%d,%d)\n",
                    point.getLeft()-1, point.getRight()-1,
                    start.getLeft()-1, start.getRight()-1,
                    end.getLeft()-1, end.getRight()-1
            );
        }
        //System.out.println();

    }

    static int getGridNumber(int number, int gridLimit) {
        return number % gridLimit == 0 ? number / gridLimit
                : Double.valueOf(Math.floor(number / gridLimit)).intValue() + 1;
    }

    static Pair<Integer, Integer> getGridStartCoordinate(int row, int column, int innerLength) {
        int gridX = getGridNumber(row, innerLength);
        int gridY = getGridNumber(column, innerLength);
        return new Pair(gridX != 1 ? ((gridX - 1) * innerLength + 1) : gridX,
                gridY != 1 ? ((gridY - 1) * innerLength + 1) : gridY);
    }

    static Pair<Integer, Integer> getGridEndCoordinate(int row, int column, int innerLength) {
        int gridX = getGridNumber(row, innerLength);
        int gridY = getGridNumber(column, innerLength);
        return new Pair(gridX * innerLength, gridY * innerLength);
    }

}
/**
 * col
 * row 11 12 13 14 15 16
 * 21 22 23 24 25 26
 * 31 32 33 34 35 36
 * 41 42 43 44 45 46
 * 51 52 53 54 55 56
 * 61 62 63 64 65 66
 * <p>
 * grid:
 * - coordinate: 3,1
 * grid_start: 1,1,
 * grid_end: 3,3
 * <p>
 * - coordinate: 5,5
 * grid_start: 4,4
 * grid_end: 6,6
 * <p>
 * - coordinate: 1,6
 * grid_start: 1,4
 * grid_end: 3,6
 */