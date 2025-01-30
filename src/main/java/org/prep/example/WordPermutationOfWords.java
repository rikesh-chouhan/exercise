package org.prep.example;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WordPermutationOfWords {

    static int BREAK = 100;

    public static void main(String[] args) {

        String[] arr = {"a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a"};
        String[] arr2 = {"ab", "cd"};

        List<String> concated = new WordPermutationOfWords().recurseWithRollover(new ArrayList<>(List.of(arr)));
        concated.stream()
                .forEach(System.out::println);

//        List<String> concated2 = new WordPermutationOfWords()
//                .recurseList(new ArrayList<>(List.of(arr)), arr.length);
//        concated2.stream()
//                .forEach(System.out::println);
    }

    protected List<String> recurseWithRollover(List<String> entries) {
        List<String> computed = recurse(entries.toArray(new String[0]), entries.size());

        return computed;
    }

    protected List<String> recurse(String[] words, int index) {
        if (index <= 1) {
            List<String> result = new ArrayList<>();
            String word = String.join("", words);
            result.add(word);
            return result;
        } else {
            List<String> result = new ArrayList<>();
            for (String one : recurse(words, index - 1)) {
                result.add(one);
            }
            for (int i = 0; i < index - 1; i++) {
                if (index % 2 == 0) {
                    String temp = words[i];
                    words[i] = words[index - 1];
                    words[index - 1] = temp;
                } else {
                    String temp = words[0];
                    words[0] = words[index - 1];
                    words[index - 1] = temp;
                }
                for (String one : recurse(words, index - 1)) {
                    result.add(one);
                }
            }
            return result;
        }
    }

    protected List<String> recurseList(List<String> words, int index) {
        if (index <= 1) {
            List<String> result = new ArrayList<>();
            result.add(String.join("", words));
            return result;
        } else {
            List<String> result = new ArrayList<>();
            for (String one : recurseList(words, index - 1)) {
                result.add(one);
            }
            for (int i = 0; i < index - 1; i++) {
                if (index % 2 == 0) {
                    String temp = words.get(i);
                    words.set(i, words.get(index - 1));
                    words.set(index - 1, temp);
                } else {
                    String temp = words.get(0);
                    words.set(0, words.get(index - 1));
                    words.set(index - 1, temp);
                }
                for (String one : recurseList(words, index - 1)) {
                    result.add(one);
                }
            }
            return result;
        }
    }

}