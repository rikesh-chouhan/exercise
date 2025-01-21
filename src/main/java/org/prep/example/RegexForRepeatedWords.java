package org.prep.example;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexForRepeatedWords {

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner scanner = new Scanner(System.in);
        int items = 5;
        List<String> lines = List.of(
                "Goodbye bye bye world World world",
                "Sam went went to to to his business",
                "Reya is is the the best player in eye eye game",
                "in inthe",
                "Hello hello Ab aB"
        );
        InputChecker ic = new InputChecker();
        for (String line : lines) {
            String output = ic.evaluate(line);
            System.out.println("original: " + line);
            System.out.println("redacted: " + output);
        }
        scanner.close();
    }

    static class InputChecker {
        Pattern pattern = Pattern.compile("[^a-zA-Z]+");
        String wordBoundary = "\\b";

        String evaluate(final String toEvaluate) {
            String input = toEvaluate;
            Matcher matcher = pattern.matcher(toEvaluate);
            int start = 0;
            int end;
            StringBuffer buffer = new StringBuffer();
            while (matcher.find()) {
                end = matcher.end();
                String word = input.substring(start, matcher.start());
                buffer.append(word);
                Pattern forEntry = Pattern.compile(wordBoundary + word + wordBoundary);
                input = input.substring(matcher.end()).replaceAll("(?i)"+forEntry.pattern(), "");
                if (matcher.group().trim().length() == 0) {
                    input = input.stripLeading();
                    buffer.append(" ");
                } else {
                    buffer.append(matcher.group().trim());
                }
                input = buffer + input;
                start = end;
                matcher = pattern.matcher(input);
                matcher.region(start, input.length());
            }

            return input;
        }
    }
}