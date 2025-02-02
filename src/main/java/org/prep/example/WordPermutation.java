package org.prep.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WordPermutation {

    static int BREAK = 100;

    public static void main(String[] args) {

        Arrays.asList(args).stream().forEach(permute -> {
            final AtomicInteger size = new AtomicInteger(0);
            findPermutations("abcdef".toCharArray(), 2).stream().forEach(word -> {
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

        //new WordPermutation().addCombos(List.of("a", "b", "c"), List.of("d","e","f"));

    }

    public static List<String> findPermutations(char[] input, int length) {
        if (input == null || input.length == 1) {
            return input == null ?
                    Collections.emptyList() :
                    Arrays.asList(new String(input));
        }
        List<String> wordList = new ArrayList();
        String word = new String(input); // ab
        Stack<String> fragments = new Stack<>();
        String fragment = word.substring(word.length()-length);
        fragments.push(fragment);
        while (!fragments.isEmpty()) {
            fragment = fragments.pop();
            //System.out.println(fragment+"--");
            if (fragment.length() == 0) continue;
            int indexToBreak = word.length() - fragment.length();
            if (indexToBreak > 0) {
                String prevChar = word.substring(indexToBreak-length    , indexToBreak);
                for(int i=0; i<=fragment.length(); i += length) {
                    String prefix = fragment.substring(0,i);
                    String suffix = fragment.substring(i);
                    String combined = prefix + prevChar + suffix;
                    //System.out.println(prefix+"--"+prevChar+"--"+suffix+"--");
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

    protected void addCombos(List<String> letters, List<String> existing) {
        System.out.println("addCombos");
        letters.stream()
                .flatMap(letter -> existing.isEmpty() ? Stream.of(letter) : existing.stream().map(est -> letter + est))
                .collect(Collectors.toCollection(ArrayList::new))
                .forEach(string -> System.out.println("word-" + string + "-"));

    }

}
