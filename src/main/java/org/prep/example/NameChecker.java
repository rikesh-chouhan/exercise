package org.prep.example;

import java.util.ArrayList;
import java.util.List;

public class NameChecker {

    public static void main(String[] args) {

        List<Pair<String, String>> toVerify = List.of(
                new Pair<>("alex", "aalleex"),
                new Pair<>("saeed", "saaedd"),
                new Pair<>("lleellee", "llleelleeee"));

        Solution solution = new Solution();
        for (Pair<String, String> pair: toVerify) {
            boolean output = solution.isLongPressedName(pair.left, pair.right);
            System.out.println("output: " + output + " for name: "+pair.left+" typed: "+ pair.right);
        }
    }
    static class Solution {
        public boolean isLongPressedName(String name, String typed) {
            int nextNameIndex = 0;
            int nextTypedIndex = 0;
            List<Pair<String,Integer>> nameLetterCounts = new ArrayList<>();

            for (int i=0; i<name.length(); i++) {
                nextNameIndex = i;
                while (nextTypedIndex < typed.length() && name.charAt(i) == typed.charAt(nextTypedIndex)) {
                    nextTypedIndex++;
                    if (nameLetterCounts.size() == i) {
                        nameLetterCounts.add(new Pair<>(name.substring(i, i+1), 1));
                    } else {
                        Pair<String, Integer> entry = nameLetterCounts.get(i);
                        entry.right = entry.right + 1;
                        nameLetterCounts.set(i, entry);
                    }
                }
                if (nameLetterCounts.size() == i) {
                    nameLetterCounts.add(new Pair<>(name.substring(i, i+1), 0));
                }
            }

            if (nextNameIndex < name.length()-1) {
                System.out.println("Returning false as name was not traversed fully");
                return false;
            }

            for (int i=0; i<name.length(); i++) {
                if (nameLetterCounts.get(i).right == 0) {
                    // check for successive letters
                    int counter = i; // walk backwards
                    int totalCount = 0;
                    while (counter >= 0 && nameLetterCounts.get(counter).left.equals(name.substring(i, i+1))) {
                        totalCount += nameLetterCounts.get(counter).right;
                        counter--;
                    }
                    if (totalCount < i - (Math.max(counter, 0))) {
                        System.out.println("letter in name:("+name.charAt(i)+") from container:"+nameLetterCounts.get(i)+" counter:"+counter+" totalCount:"+totalCount);
                        return false;
                    } else {
                        System.out.println("letter in name:("+name.charAt(i)+") from container:"+nameLetterCounts.get(i)+" counter:"+counter+" totalCount:"+totalCount);
                    }
                }
            }

            return true;
        }
    }
}
