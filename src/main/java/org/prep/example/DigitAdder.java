package org.prep.example;

import org.jooq.meta.derby.sys.Sys;

import java.util.ArrayList;
import java.util.List;

public class DigitAdder {

    private NumberSplitter numberSplitter;

    public DigitAdder(int theNumberToAddTo) {
        numberSplitter = new NumberSplitter(theNumberToAddTo);
    }

    public List<Long> addDigitToSourceNumber(int thisDigit) {
        java.util.List<Integer> theDigits = numberSplitter.split(false);
        List<Long> numbers = new ArrayList<>();
        List<int[]> listOfDigits = new ArrayList();
        int outerLoop = theDigits.size() + 1;
        for (int i = 0; i < outerLoop; i++) {
            int[] innerList = new int[outerLoop];
            innerList[i] = thisDigit;
            int j = 0;
            for(; j<i; j++) {
                innerList[j] = theDigits.get(j);
            }
            for(int k=i+1; k<outerLoop; k++) {
                innerList[k] = theDigits.get(j++);
            }
            listOfDigits.add(innerList);
        }
        for (int[] array: listOfDigits) {
            long numberCreated = 0l;
            for (int i=0; i<array.length; i++) {
                numberCreated += array[i] * Math.pow(10, (array.length-1)-i);
            }
            numbers.add(numberCreated);
        }
        return numbers;
    }

    public static void main(String[] args) {
        DigitAdder digitAdder = new DigitAdder(1);
        List<Long> composedNumbers = digitAdder.addDigitToSourceNumber(5);
        composedNumbers.stream().forEach(System.out::println);

        digitAdder = new DigitAdder(999);
        composedNumbers = digitAdder.addDigitToSourceNumber(5);
        composedNumbers.stream().forEach(System.out::println);
    }
}
/**
 * 0
 */