package org.prep.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MagicSquare {

    // Complete the formingMagicSquare function below.
    static int formingMagicSquare(int[][] s) {
        int distance = 0;
        List<Integer> missing = Arrays.asList(1,2,3,4,5,6,7,8,9);
        Map<Integer, String> repeated = new HashMap();
        for (int i=0; i<s.length; i++) {
            for (int j=0; j<s[i].length; j++) {
                int key = s[i][j];
                int index = missing.indexOf(key);
                if (index > -1) {
                    missing.remove(index);
                } else {
                    repeated.put(key, ""+i+"-"+j);
                }
            }
        }
        System.out.println("missing: " + missing);
        System.out.println("repeated: " + repeated);
        return distance;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int[][] s = new int[3][3];

        for (int i = 0; i < 3; i++) {
            String[] sRowItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int j = 0; j < 3; j++) {
                int sItem = Integer.parseInt(sRowItems[j]);
                s[i][j] = sItem;
            }
        }

        int result = formingMagicSquare(s);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
