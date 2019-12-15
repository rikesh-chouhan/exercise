package org.prep.example;

import java.util.ArrayList;
import java.util.List;

public class SendToFiles {

    // METHOD SIGNATURE BEGINS, THIS METHOD IS REQUIRED
    int minimumHours(int rows, int columns, List<List<Integer> > grid) {

        // WRITE YOUR CODE HERE
        List<Integer> currentSetState = new ArrayList<>();
        List<Integer> currentUnset = new ArrayList<>();
        int counter = 0;
        for (List<Integer> entry: grid) {
            for (Integer number: entry) {
                counter++;
                if (number == 1) {
                    currentSetState.add(counter);
                } else {
                    currentUnset.add(counter);
                }
            }
        }

        System.out.println("List: " + grid);

        if (currentUnset.size() == counter) {
            return -1;
        }

        boolean keepGoing = true;
        int minHours = 0;
        List<Integer> futureSet = new ArrayList<>();
        while(keepGoing) {
            if (currentUnset.size()==0) {
                break;
            }
            minHours++;
            for (Integer entry : currentUnset) {
                if (currentSetState.contains(entry-1) || currentSetState.contains(entry+1)
                || currentSetState.contains(entry+rows) || currentSetState.contains(entry-rows)) {
                    futureSet.add(entry);
                }
            }
            currentSetState.addAll(futureSet);
            currentUnset.removeAll(futureSet);
            futureSet.clear();
        }
        return minHours;
    }
    // METHOD SIGNATURE ENDS

    public static void main(String args[]) {
        List<List<Integer>> list = new ArrayList<>();
        int rows = 5;
        int columns = 3;
        for (int i = 0; i < rows; i++) {
            List<Integer> list2 = new ArrayList<>();
            for (int j = 0; j < columns; j++) {
                double rnd = Math.random();
                String rndString = Double.toString(rnd);
                int lastDigit = Integer.parseInt("" + rndString.charAt(rndString.length()-1));
                list2.add(lastDigit % 2 == 0 ? 1 : 0);
            }
            list.add(list2);
        }
        SendToFiles stf = new SendToFiles();
        int hours = stf.minimumHours(rows, columns, list);
        System.out.println("Hours for propogation: " + hours);
    }
}
