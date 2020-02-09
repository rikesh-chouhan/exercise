package org.prep.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RecursiveWordPermutation {

    static int BREAK = 100;

    public static void main(String args[]) {
        for (String arg : args) {
            final AtomicInteger size = new AtomicInteger(0);
            List<String> permutations = new ArrayList();
            recurseThroughArray(new StringBuffer(arg), 0, permutations);
            permutations.stream().forEach(word -> {
                size.set(size.get() + word.length() + 1);
                System.out.printf(word + " ");
                if (size.get() > BREAK) {
                    System.out.println();
                    size.set(0);
                }
                System.out.flush();
            });
            System.out.println();
        }
    }

    private static void recurseThroughArray(StringBuffer str, int left, List<String> list) {
        if (left == str.length()) {
            list.add(str.toString());
            return;
        }
        recurseThroughArray(str, left+1, list);
        for (int i = left+1; i < str.length(); i++) {
            swap(str, left, i);
            recurseThroughArray(str, left+1, list);
            swap(str, i, left);
        }
    }

    private static void swap(StringBuffer buffer, int index1, int index2) {
        char curr = buffer.charAt(index1);
        buffer.setCharAt(index1, buffer.charAt(index2));
        buffer.setCharAt(index2, curr);
    }

}
