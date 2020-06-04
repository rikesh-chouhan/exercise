package org.prep.example.streaming;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class InfiniteStream {

    static int BREAK = 100;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Started");
        System.out.println("Please input limit for stream count: ");
        long limit = Long.parseLong(scanner.nextLine());
        AtomicInteger size = new AtomicInteger(0);
        System.out.println("Limit: "+limit);
        randomNumberStream(Integer.MAX_VALUE).filter(value -> value>0).limit(limit).forEach(word -> {
            size.set(size.get() + (""+word).length() + 1);
            System.out.printf(word + " ");
            if (size.get() > BREAK) {
                System.out.println();
                size.set(0);
            }
            System.out.flush();
        });
    }

    private static Stream<Integer> randomNumberStream(int seed) {
        final Random random = new Random(seed);
        return Stream.iterate(0, i -> Math.abs(random.nextInt()));
    }

}
