package org.prep.example;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class WordPermutation {

  static int BREAK = 100;

  public static void main(String[] args) {

    Arrays.asList(args).stream().forEach(permute -> {
      final AtomicInteger size = new AtomicInteger(0);
      findPermutations(permute).stream().forEach(word -> {
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

  public static List<String> findPermutations(String input) {
    if (input == null || input.length() == 1) {
      return input == null ?
          Collections.emptyList() :
          Arrays.asList(input);
    }
    Set<String> wordList = new HashSet<>();
    // ab
    int calledAdd = 0;
    Stack<String> fragments = new Stack<>();
    String fragment = new String(input);
    fragments.push(fragment.substring(fragment.length() - 1));
      while (!fragments.isEmpty()) {
        fragment = fragments.pop();
        //System.out.println(fragment + "--");
        if (fragment.isEmpty()) continue;
        int indexToBreak = input.length() - fragment.length();
        if (indexToBreak > 0) {
          String prevChar = input.substring(indexToBreak - 1, indexToBreak);
          for (int i = 0; i <= fragment.length(); i++) {
            String prefix = fragment.substring(0, i);
            String suffix = fragment.substring(i);
            String combined = prefix + prevChar + suffix;
            if (combined.length() == input.length()) {
              calledAdd++;
              wordList.add(combined);
            } else {
              fragments.push(combined);
            }
          }
        }
      }
    System.out.println("Words added: " + calledAdd + " set length: " + wordList.size());
    return new ArrayList<>(wordList);
  }

}
