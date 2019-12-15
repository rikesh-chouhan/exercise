package org.prep.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TopNToys {

    public ArrayList<String> popularNToys(int numToys,
                                          int topToys,
                                          List<String> toys,
                                          int numQuotes,
                                          List<String> quotes)
    {
        // WRITE YOUR CODE HERE
        if (topToys == 0) return new ArrayList<String>();

        TreeMap<String, Integer> toysCount = new TreeMap<>();
        for (String toy: toys) {
            String lct = toy != null ? toy.toLowerCase() : null;
            if (lct != null) {
                for (String quote : quotes) {
                    if (quote != null && quote.toLowerCase().contains(lct)) {
                        if (toysCount.containsKey(lct)) {
                            toysCount.put( lct, toysCount.get(lct) + 1);
                        } else {
                            toysCount.put( lct, 1);
                        }
                    }
                }
            }
        }

        ArrayList<String> returnSet = new ArrayList<>();
        if (topToys > toys.size()) {
            for (String toy : toys) {
                if (toysCount.containsKey(toy.toLowerCase())) {
                    returnSet.add(toy);
                }
            }
        } else {
            TreeMap<Integer,String> reverseMap = new TreeMap(
                    (e1, e2) -> ((Integer) e2).compareTo((Integer) e1)
            );
            for(Map.Entry<String, Integer> entry : toysCount.entrySet()) {
                reverseMap.put(entry.getValue(), entry.getKey());
            }
            int counter = 0;
            for (String value: reverseMap.values()) {
                counter++;
                returnSet.add(value);
                if (counter == numToys) {
                    break;
                }
            }
        }

        return returnSet;
    }
    // METHOD SIGNATURE ENDS

}
