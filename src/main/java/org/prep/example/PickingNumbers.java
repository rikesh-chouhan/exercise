package org.prep.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class PickingNumbers {

    public static void main(String[] args) throws IOException {
        System.out.println("Started");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        //BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> a = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                .map(Integer::parseInt)
                .collect(toList());

        int result = NumberPicker.pickingNumbers(a);
        System.out.println("Max length: " + result);
        //bufferedWriter.write(String.valueOf(result));
        //bufferedWriter.newLine();

        bufferedReader.close();
        //bufferedWriter.close();
    }
}

class NumberPicker {

    static int MAX_ABS_DIFF = 1;
    /*
     * Complete the 'pickingNumbers' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts INTEGER_ARRAY a as parameter.
     */

    public static int pickingNumbers(List<Integer> a) {
        // Write your code here
        if (a == null || a.size() == 0) return 0;
        if (a.size() == 1) return 1;
        int maxLeng = 0;
        List<Integer> members = new ArrayList();
        for (int i=0; i<a.size()-1; i++) {
            int prevValue = a.get(i);
            int minValue = prevValue;
            int maxValue = prevValue;
            members.add(prevValue);
            for(int j=i+1; j<a.size(); j++) {
                if (Math.abs(prevValue-a.get(j)) <= MAX_ABS_DIFF) {
                    members.add(a.get(j));
                    if (minValue > a.get(j)) {
                        minValue = a.get(j);
                    }
                    if (maxValue < a.get(j)) {
                        maxValue = a.get(j);
                    }
                }
            }
            Map<Integer, Long> countMap = members.stream()
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            countMap = countMap.entrySet().stream().sorted(Map.Entry.comparingByValue())
                        .collect(Collectors.toMap(
                                Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

            if (Math.abs(minValue - maxValue) > MAX_ABS_DIFF) {
                long countMin = countMap.get(minValue);
                long countMax = countMap.get(maxValue);
                if (countMin >= countMax) {
                    countMap.remove(maxValue);
                } else if (countMax > countMin) {
                    countMap.remove(minValue);
                }
            }
            int maxCount = countMap.values().stream().reduce(0l, Long::sum).intValue();
            if (maxCount>maxLeng) {
                maxLeng = maxCount;
            }
            members.clear();

        }

        return maxLeng;
    }

}
