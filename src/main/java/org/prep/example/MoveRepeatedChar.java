package org.prep.example;

import java.util.List;
import java.util.Stack;

/**
 * Move the chars in a string so that it has non-repeating chars
 */
public class MoveRepeatedChar {

    public static void main(String[] args) {
        List.of("bcaaa", "aabca", "aaabc", "abaca")
                .stream()
                .forEach(input -> {
                    String output = reArrange(input);

                    System.out.println("input: " + input + " output: " + output);
                });
    }

    public static String reArrange(String input) {
        StringBuffer buffer = new StringBuffer();
        int curr = 0;
        int buffCurr = 0;
        Stack<String> repeats = new Stack<>();
        while (curr < input.length() || !repeats.isEmpty()) {
            char toCheck = curr < input.length() ? input.charAt(curr) : repeats.pop().charAt(0);
            boolean added = false;
            if (buffer.isEmpty()) {
                buffer.append(toCheck);
                added = true;
            } else {
                if (toCheck != buffer.charAt(buffCurr)) {
                    buffer.append(toCheck);
                    added = true;
                } else {
                    for (int i = 0; i < buffCurr; i++) {
                        if (toCheck != buffer.charAt(i)) {
                            // check previous char as well
                            if (i == 0 || buffer.charAt(i - 1) != toCheck) {
                                added = true;
                                buffer.insert(i, toCheck);
                                break;
                            }
                        }
                    }
                    if (!added) {
                        repeats.push("" + toCheck);
                    }
                }
            }
            if (added) {
                buffCurr = buffer.length() - 1;
            }
            curr++;
        }
        return buffer.toString();
    }
}
