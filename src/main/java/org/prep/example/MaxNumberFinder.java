package org.prep.example;

import java.util.List;
import java.util.Scanner;

public class MaxNumberFinder {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        for (String arg: args) {
            int number = Integer.parseInt(arg);
            DigitAdder digitAdder = new DigitAdder(number);
            System.out.println("Enter a digit to add");
            int digit = scanner.nextInt();
            List<Long> list = digitAdder.addDigitToSourceNumber(digit);
            long theMaxNumber = 0l;
            if (number > 0) {
                theMaxNumber = list.stream().max(Long::compareTo).orElseGet(() ->{ return 0l; });
            } else {
                theMaxNumber = -1 * list.stream().min(Long::compareTo).orElseGet(() ->{ return 0l; });
            }
            System.out.println("Max number combination: "+theMaxNumber+" for number: "+number+" digit add: "+digit);
        }
    }
}
