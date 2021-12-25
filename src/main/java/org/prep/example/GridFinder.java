package org.prep.example;

import java.util.AbstractMap;
import java.util.Random;
import java.util.stream.IntStream;

public class GridFinder {

    static Random random = new Random(System.currentTimeMillis());
    static final AbstractMap.SimpleEntry<Integer,Integer> entry = new AbstractMap.SimpleEntry(100,0);
    static final int limit = 10;
    public static void main(String[] args) {

        IntStream.generate(() -> random.nextInt(100)).limit(limit).forEach(num -> {
                    entry.setValue(entry.getValue() + 1);
                    System.out.printf("%d%s", Math.abs(num), entry.getValue() < limit ? "," : "\n");
                }
        );
        int [][] testCord = {{1,4},{5,5},{1,6},{2,2}};
        for (int i=0; i<testCord.length; i++) {
            Pair<Integer, Integer> point = new Pair(testCord[i][0], testCord[i][1]);
            Pair<Integer, Integer> start = getGridStartCoordinate(testCord[i][0], testCord[i][1], 3);
            Pair<Integer, Integer> end = getGridEndCoordinate(testCord[i][0], testCord[i][1], 3);
            System.out.printf("(x,y) = (%d,%d) start: (%d,%d) end: (%d,%d)\n",
                    point.getLeft(), point.getRight(),
                    start.getLeft(), start.getRight(),
                    end.getLeft(), end.getRight()
            );
        }
        //System.out.println();
    }

    static int getGridNumber(int number, int gridLimit) {
        return number % gridLimit == 0 ? number/gridLimit
                : Double.valueOf(Math.floor(number/gridLimit)).intValue() + 1;
    }

    static Pair<Integer, Integer> getGridStartCoordinate(int row, int column, int innerLength) {
        int gridX = getGridNumber(row, innerLength);
        int gridY = getGridNumber(column, innerLength);
        return new Pair(gridX != 1? ((gridX-1) * innerLength + 1) : gridX,
                gridY != 1? ((gridY-1) * innerLength + 1) : gridY);
    }

    static Pair<Integer, Integer> getGridEndCoordinate(int row, int column, int innerLength) {
        int gridX = getGridNumber(row, innerLength);
        int gridY = getGridNumber(column, innerLength);
        return new Pair(gridX * innerLength, gridY * innerLength);
    }

}
/**
 *     col
 * row 11 12 13 14 15 16
 *     21 22 23 24 25 26
 *     31 32 33 34 35 36
 *     41 42 43 44 45 46
 *     51 52 53 54 55 56
 *     61 62 63 64 65 66
 *
 *  grid:
 *      - coordinate: 3,1
 *        grid_start: 1,1,
 *        grid_end: 3,3
 *
 *      - coordinate: 5,5
 *        grid_start: 4,4
 *        grid_end: 6,6
 *
 *      - coordinate: 1,6
 *        grid_start: 1,4
 *        grid_end: 3,6
 */