package org.prep.example;

import java.util.ArrayList;

/*
 * To execute Java, please define "static void main" on a class
 * named Solution.
 *
 * If you need more classes, simply define them inline.


Write a function that checks if string1 is equal to string2 where string1/string2 can be either a compressed / expanded string ie: isEqual(‘advertising’, ‘a1v7g’) = true where 9 here represents # of any letters between a and g and isEqual(‘advertising’, ‘a7ng’) = false

isEqual('many','13y') = true
isEqual('h','1') = true
isEqual('hey', '3') = true
isEqual('','0') = true
 */

public class StringSkipCheck {

    public static void main(String[] args) {
        ArrayList<String> strings = new ArrayList<String>();

        String orig = "origin";
        String comp = "o";
        System.out.println("isEqual(" + orig + ", " + comp + ") = " + isEqual(orig, comp));
        comp = "origin";
        System.out.println("isEqual(" + orig + ", " + comp + ") = " + isEqual(orig, comp));
        comp = "o5";
        System.out.println("isEqual(" + orig + ", " + comp + ") = " + isEqual(orig, comp));
        comp = "o5n";
        System.out.println("isEqual(" + orig + ", " + comp + ") = " + isEqual(orig, comp));
        comp = "o4n";
        System.out.println("isEqual(" + orig + ", " + comp + ") = " + isEqual(orig, comp));
        comp = "o2g1n";
        System.out.println("isEqual(" + orig + ", " + comp + ") = " + isEqual(orig, comp));
        comp = "o2g1n3";
        System.out.println("isEqual(" + orig + ", " + comp + ") = " + isEqual(orig, comp));
        comp = "6";
        System.out.println("isEqual(" + orig + ", " + comp + ") = " + isEqual(orig, comp));
        comp = "0";
        System.out.println("isEqual(" + orig + ", " + comp + ") = " + isEqual(orig, comp));
        comp = "0";
        System.out.println("isEqual(" + "" + ", " + comp + ") = " + isEqual("", comp));
    }

    public static boolean isEqual(String original, String rule) {
        System.out.println("Evaulating original: "+original+" rule: "+rule);
        if (original == rule) return true;
        if (original == null || rule == null) return false;
        if (original.equalsIgnoreCase(rule)) return true;
        if (rule.trim().length() == 0 && original.trim().length() > 0) return false;

        original = original.toLowerCase();
        rule = rule.toLowerCase();

        StringBuilder partString = new StringBuilder();
        StringBuilder numberString = new StringBuilder();

        int origCounter = 0;
        int lengthToSkip = 0;
        char[] ruleArray = rule.toCharArray();
        int counter = 0;
        int iteration = 0;
        while (counter < ruleArray.length && origCounter < original.length()) {
            lengthToSkip = 0;
            while (counter < ruleArray.length && Character.isAlphabetic(ruleArray[counter])) {
                partString.append(ruleArray[counter++]);
            }
            while (counter < ruleArray.length && Character.isDigit(ruleArray[counter])) {
                numberString.append(ruleArray[counter++]);
            }
            if (numberString.length() > 0) {
                lengthToSkip = Integer.parseInt(numberString.toString());
                numberString.delete(0, numberString.length());
            }
            if (partString.length() > 0) {
//                System.out.println("partString: " + partString.toString() + "-- length: " + (origCounter + partString.length()));
                if (!original.substring(origCounter, origCounter + partString.length()).equals(partString.toString())) {
                    return false;
                }
                origCounter = origCounter + partString.length();
                partString.delete(0, partString.length());
            }
            origCounter = origCounter + lengthToSkip;
            iteration++;
//            System.out.println("iteration: " + iteration + " origCounter: " + origCounter + " length: " + lengthToSkip);
        }
//        System.out.println("counter: " + counter + " origCounter: " + origCounter);
        if (original.length() == 0 && counter == 0) return true;
        if (origCounter != original.length() || rule.length() != counter) return false;

        return true;
    }

}
