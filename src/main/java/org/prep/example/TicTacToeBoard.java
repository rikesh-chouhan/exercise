package org.prep.example;

import javax.annotation.Nonnull;

public class TicTacToeBoard {

    private int board[][];
    public static final int USER_MOVE_1 = 1;
    public static final int USER_MOVE_2 = 0;

    public TicTacToeBoard() {
        initBoard();
    }

    private void initBoard() {
        board = new int[3][3];
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) board[i][j] = -1;
        }
    }

    public boolean isLegalMove(@Nonnull Location location) {
        if (location == null) throw new NullPointerException("Move location cannot be null");
        if (0 <= location.row  && location.row < 3 && 0<= location.column && location.column < 3) {
            if (board[location.row][location.column] == -1) {
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    public void moveUser1(Location location) throws IllegalStateException {
        move(USER_MOVE_1, location);
    }

    public void moveUser2(Location location) throws IllegalStateException {
        move(USER_MOVE_2, location);
    }

    private void move(int moveValue, Location location) throws IllegalStateException {
        if (isLegalMove(location)) {
            board[location.row][location.column] = moveValue;
        } else {
            throw new IllegalStateException("Move: " + location.row + ":" + location.column + " not allowed");
        }
    }

    public boolean areAnyMovesLeft() {
        for (int i=0; i<3; i++)
            for(int j=0; j<3; j++) {
                if (board[i][j] == (-1)) return true;
            }

        return false;
    }

    public void printBoard() {
        for(int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                switch (board[i][j]) {
                    case USER_MOVE_1:
                        System.out.printf("X");
                        break;
                    case USER_MOVE_2:
                        System.out.print("O");
                        break;
                    default:
                        System.out.printf("-");
                        break;
                }
                if (j<2) {
                    System.out.printf("|");
                } else {
                    System.out.println();
                }
            }
        }
    }


    public static void main(String[] args) {
        TicTacToeBoard ticTacToeBoard = new TicTacToeBoard();
        try {
            ticTacToeBoard.moveUser1(new Location(2, 1));
            ticTacToeBoard.printBoard();
        } catch (Exception e) {

        }
    }
}

class Location {
    int row;
    int column;
    Location(int row, int column) {
        this.row = row;
        this.column = column;
    }
}
