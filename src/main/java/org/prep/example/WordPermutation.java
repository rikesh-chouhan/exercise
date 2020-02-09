package org.prep.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

public class WordPermutation {

    static int BREAK = 100;

    public static void main(String[] args) {
        Arrays.asList(args).stream().forEach(permute -> {
            final AtomicInteger size = new AtomicInteger(0);
            findPermutations(permute.toCharArray()).stream().forEach(word -> {
                size.set(size.get() + word.length() + 1);
                System.out.printf(word + " ");
                if (size.get() > BREAK) {
                    System.out.println();
                    size.set(0);
                }
                System.out.flush();
            });
            System.out.println();
        });
    }

    public static List<String> findPermutations(char[] input) {
        if (input == null || input.length == 1) {
            return input == null ?
                    Collections.emptyList() :
                    Arrays.asList(new String(input));
        }
        List<String> wordList = new ArrayList();
        String word = new String(input); // ab
        Stack<String> fragments = new Stack<>();
        String fragment = word.substring(word.length()-1);
        fragments.push(fragment);
        while (!fragments.isEmpty()) {
            fragment = fragments.pop();
            if (fragment.length() == 0) continue;
            int indexToBreak = word.length() - fragment.length();
            if (indexToBreak > 0) {
                String prevChar = word.substring(indexToBreak-1, indexToBreak);
                for(int i=0; i<=fragment.length(); i++) {
                    String prefix = fragment.substring(0,i);
                    String suffix = fragment.substring(i);
                    String combined = prefix + prevChar + suffix;
                    if (combined.length() == word.length()) {
                        wordList.add(combined);
                    } else {
                        fragments.push(combined);
                    }
                }
            }
        }
        return wordList;
    }

}
