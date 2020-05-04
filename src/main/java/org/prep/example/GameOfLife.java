package org.prep.example;

public class GameOfLife {
    boolean[][] seed;

    public GameOfLife(boolean[][] array) {
        seed = array;
    }

    public synchronized boolean[][] setFutureState(int iterations) {
        boolean[][] future = new boolean[seed.length][seed[0].length];
        int counter = 0;
        while (counter < iterations) {
            for (int i = 0; i < seed.length; i++) {
                for (int j = 0; j < seed[i].length; j++) {
                    future[i][j] = getFutureState(i, j);
                }
            }
            updateState(future);
            counter++;
        }

        return seed;
    }

    private void updateState(boolean[][] future) {
        seed = future;
    }

    private boolean getFutureState(int forI, int forJ) {
        boolean toFuture = false;
        boolean currentState = seed[forI][forJ];
        int aliveCount = 0;
        for (int i = forI - 1; i <= forI + 1; i++) {
            if (i < 0 || i >= seed.length) continue;
            for (int j = forJ - 1; j <= forJ + 1; j++) {
                if (j < 0 || j >= seed[forI].length) continue;
                if (i == forI && j == forJ) continue;
                if (seed[i][j]) aliveCount++;
            }
        }

        switch (currentState ? 1 : 0) {
            case 1:
                if (aliveCount == 2 || aliveCount == 3) {
                    toFuture = true;
                } else {
                    toFuture = false;
                }
                break;
            case 0:
                if (aliveCount == 3) {
                    toFuture = true;
                }
                break;
        }
        return toFuture;
    }

    public void print() {
        System.out.printf("[ ");
        for (int i = 0; i < seed.length; i++) {
            System.out.print("[");
            for (int j = 0; j < seed[i].length; j++) {
                System.out.printf("%d%s", seed[i][j] ? 1 : 0, (j < (seed.length - 1) ? "," : ""));
            }
            System.out.printf("]%s", i < (seed.length - 1) ? "," : "");
        }
        System.out.printf(" ]\n");
    }

    public static void main(String args[]) {
        boolean[][] seedData = {
                {false, false, false, false, false},
                {false, false, false, false, false},
                {false, true, true, true, false},
                {false, false, false, false, false},
                {false, false, false, false, false}
        };
        GameOfLife gol = new GameOfLife(seedData);
        gol.print();
        gol.setFutureState(1);
        gol.print();
        gol.setFutureState(1);
        gol.print();
        gol.setFutureState(1);
        gol.print();
    }
}
