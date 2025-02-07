package org.prep.example;

public class Pyramid {

    public static void main(String[] args) {
        printPyramid(3, "*", " ");
    }

    static void printPyramid(int level, String charToPrint, String other) {
        for (int i = 1; i<=level; i++) {
            String line = "";
            for (int j=i; j<level; j++) {
                line += other;
            }
            for(int j=1; j<=i;j++) {
                line += charToPrint;
            }
            for (int j=i; j>1; j--) {
                line += charToPrint;
            }
            System.out.println(line);
        }
    }
}
