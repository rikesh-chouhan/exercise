package org.prep.example;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CounterForWords {

    public static void main(String[] args) {
        if (args.length > 0) {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(args[0])));
                String line;
                Map<String, Long> wordCount = new HashMap<>();
                while ((line = br.readLine()) != null) {
                    String[] array = line.split("\\W");
                    Map<String, Long> lineCounter = Arrays.asList(array).stream()
                            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
                    Map<String, Long> merged = Stream.concat(lineCounter.entrySet().stream(), wordCount.entrySet().stream())
                            .collect(Collectors.toMap(
                               Map.Entry::getKey,
                               Map.Entry::getValue,
                                    (entry1, entry2) -> entry1+entry2
                            ));
                    wordCount.putAll(merged);
                }
                LinkedHashMap<String, Long> finalCount = wordCount.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1,
                                LinkedHashMap::new));

                printMap(finalCount);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    }

    static int BREAK = 100;

    private static void printMap(Map<String, Long> map) {
        final AtomicInteger size = new AtomicInteger(0);
        map.entrySet().stream().forEach(entry -> {
            String output = entry.getKey() + " " + entry.getValue() + ", ";
            size.set(size.get() + output.length());
            System.out.printf(output);
            if (size.get() > BREAK) {
                System.out.println();
                size.set(0);
            }
            System.out.flush();
        });
        System.out.println();
    }
}
