package org.prep.example;

import java.util.ArrayList;
import java.util.List;

public class NumberSplitter<T extends Integer> {

    T input;

    public NumberSplitter(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Input data cannot be null");
        }
        input = data;
    }

    public <T> List<Integer> split(boolean print) {
        int tenPower = 10;
        boolean once = true;
        int number = absolute(input.intValue());
        List<Integer> digitList = new ArrayList();
        if (print)
            System.out.println("Individual digits for: "+number);
        for (;tenPower <= number; ) {
            int aDigit = number % tenPower;
            number = number / tenPower;
            digitList.add(0, aDigit);
        }
        digitList.add(0,number);
//        for(int i = (digitList.size() - 1); i>= 0; i--) {
//            System.out.print("--"+digitList.get(i));
//        }
        if (print)
            for(int i = 0; i<digitList.size(); i++)
                System.out.print("--"+digitList.get(i));

        if (print) {
            System.out.print("\n");
            System.out.flush();
        }
        return digitList;
    }

    public static void main(String[] args) {
        System.out.println("Started");
        for (String each: args) {
            int aNumber = Integer.parseInt(each);
            NumberSplitter<Integer> numberSplitter = new NumberSplitter(aNumber);
            numberSplitter.split(true);
        }
        System.out.println("Ended");
    }

    private int absolute(int number) {
        return number >= 0 ? number : (-1) * number;
    }
}
