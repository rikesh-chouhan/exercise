package org.prep.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StateList {

    // METHOD SIGNATURE BEGINS, THIS METHOD IS REQUIRED
    public List<Integer> cellCompete(int[] states, int days)
    {
        // WRITE YOUR CODE HERE
        Map<Integer,Boolean> stateMap = new HashMap();
        stateMap.put( -1, Boolean.FALSE);
        stateMap.put( states.length, Boolean.FALSE);
        for (int i = 0; i < states.length; i++) {
            stateMap.put( i, states[i] == 0 ? Boolean.FALSE : Boolean.TRUE);
        }

        for (int j = 1; j <= days; j++) {
            for (int i = 0; i < states.length; i++) {
                Boolean prevState = stateMap.get(i);
                if (stateMap.get(i-1) == stateMap.get(i+1)) {
                    stateMap.put(i, false);
                } else {
                    stateMap.put(i, true);
                }
            }
        }

        int minNum = Integer.MAX_VALUE;

        return stateMap.values().stream().map(value -> value ? 1 : 0).collect(Collectors.toList());
    }
    // METHOD SIGNATURE ENDS
}