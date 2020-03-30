package org.prep.example;

import java.util.TreeMap;
import java.util.TreeSet;

public class TreeMapComp {

    TreeMap<Integer,String> map;

    TreeMapComp() {
        map = new TreeMap((e1, e2) -> ((Integer) e2).compareTo((Integer) e1));
    }

    public static void main(String[] args) {
        TreeMapComp tmc = new TreeMapComp();
        int counter = 1;
        for(String a: args) {
            tmc.map.put( counter, a);
            counter++;
        }

        System.out.println(tmc.map);
    }
}
