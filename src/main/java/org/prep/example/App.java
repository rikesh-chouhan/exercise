/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package org.prep.example;

import org.prep.example.model.ShippingPkg;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class App {

    private static final Map<Integer, String> PRIORITIES
            = Arrays.asList(ShippingPkg.Priority.values()).stream()
            .collect(Collectors.toMap(ShippingPkg.Priority::ordinal, ShippingPkg.Priority::name));


    public String getGreeting() {
        return "Hello world.";
    }

    public static void mainReductionSum(String[] args) {
        App app = new App();
        System.out.println(app.getGreeting());
        List<Integer> list = new ArrayList();
        for (String a : args) {
            list.add(Integer.parseInt(a));
        }
        System.out.println("Sum of passed params: " + app.reduceList(list));
    }

    public static void main(String[] args) {
        App app = new App();
        List<String> list = Arrays.asList(args).stream()
                .collect(Collectors.toList());

        System.out.println("converting ints to priorities: " + app.providePriority(list));
    }

    public int reduceList(List<Integer> list) {
        return list.stream().reduce(0, (sum, element) -> sum + element);
    }

    public List<ShippingPkg.Priority> providePriority(List<String> list) {
        return list.stream().map(Integer::parseInt)
                .map(inferredVal -> getProvidedOrDefault(ShippingPkg.Priority.LOW.ordinal(), inferredVal))
                .collect(Collectors.toList());
    }

    private ShippingPkg.Priority getProvidedOrDefault(Integer defaultVal, Integer provided) {
        if (PRIORITIES.containsKey(provided)) {
            return ShippingPkg.Priority.valueOf(PRIORITIES.get(provided));
        } else {
            return ShippingPkg.Priority.valueOf(PRIORITIES.get(defaultVal));
        }
    }
}
