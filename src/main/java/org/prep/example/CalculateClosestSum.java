package org.prep.example;

import java.util.Arrays;

public class CalculateClosestSum {
//Given an integer array, find two integers in the array whose sum is closest to zero
/*
Examples:

input: [2,8,7,0,-1,-5,3,-2]

output: 2, -2

input: [13,1,4,6,-2,-9,-20]

output: -2, 1
*/


    // Main class should be named 'Solution'
        public static void main(String[] args) {
            int[] array = new int[] {13,1,4,6,-2,-9,-20};
            int margin = 0;
            String result = findClosestToZeroSumIndexes(array, margin);
            System.out.println("Result: " + result);
        }

        private static String findClosestToZeroSumIndexes(int[] array, int margin) {
            StringBuilder builder = new StringBuilder();
            int currSmallSum = Integer.MAX_VALUE;

            Arrays.sort(array);
            int headIndex = 0;
            int tailIndex = array.length -1;

            while (headIndex<tailIndex) {
                if (Math.abs(array[headIndex] + array[tailIndex]) < Math.abs(currSmallSum)) {
                    currSmallSum = Math.abs(array[headIndex] + array[tailIndex]);
                    builder.delete(0, builder.length());
                    builder.append(array[headIndex]).append(",").append(array[tailIndex]);
                } else {
                    if (Math.abs(array[headIndex]) < Math.abs(array[tailIndex])) {
                        tailIndex--;
                    } else if (Math.abs(array[headIndex]) > Math.abs(array[tailIndex])) {
                        headIndex++;
                    } else {
                        headIndex++;
                        tailIndex--;
                    }
                }
            }

            return builder.toString();
        }

}
