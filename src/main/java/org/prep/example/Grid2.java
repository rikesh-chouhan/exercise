package org.prep.example;

import java.util.HashSet;
import java.util.Set;

public class Grid2 {

    public static void main(String[] args) {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int gn = getGridNumber2(i, j);
                if (!set.contains(gn)) {
                    set.add(gn);
                    System.out.println("grid number: " + gn);
                }
            }
        }
    }
    static int getGridNumber2(int row, int column) {
        return (row / 3 * 3) + (column / 3) + 1;
    }

}
