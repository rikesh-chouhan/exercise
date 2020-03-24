package org.prep.example;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

// a comparator that compares Strings
class ValueComparator implements Comparator<String> {

    TreeMap<String, Integer> map = new TreeMap<String, Integer>(this);

    public ValueComparator() {
    }

    @Override
    public int compare(String s1, String s2) {
        Integer val1 = map.get(s1);
        Integer val2 = map.get(s2);
        if (val1 == null || val2 == null) {
            return s1.compareTo(s2);
        }
        if (val1 > val2) {
            return -1;
        } else {
            if (val1 == val2) {
                return s1.compareTo(s2);
            } else {
                return 1;
            }
        }
    }
}