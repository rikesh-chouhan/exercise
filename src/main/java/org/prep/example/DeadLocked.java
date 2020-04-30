package org.prep.example;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Deadlock {

    private Object lock1 = new Object();
    private Object lock2 = new Object();

    public Deadlock() {

    }

    public void sayHello() {
        synchronized (lock1) {
            System.out.println("Obtained lock 1 - sayHello " + getThreadDetails());
            System.out.println("Past lock 1 going for lock 2 - sayHello " + getThreadDetails());
            synchronized (lock2) {
                System.out.println("Obtained lock 2 - sayHello " + getThreadDetails());
            }
        }
        System.out.println("Done with sayHello " + getThreadDetails());
    }

    public void sayWorld() {
        synchronized (lock2) {
            System.out.println("Obtained lock 2 - sayWorld " + getThreadDetails());
            System.out.println("Past lock 1 going for lock 2 - sayWorld " + getThreadDetails());
            synchronized (lock1) {
                System.out.println("Obtained lock 1 - sayWorld " + getThreadDetails());
            }
        }
        System.out.println("Done with sayWorld " + getThreadDetails());
    }

    public String getThreadDetails() {
        return Thread.currentThread().getName();
    }
}

public class DeadLocked {

    public static void main(String args[]) {
        int countOfThreads = 5;
        int countOfLoops = 5;
        if (args.length >= 2) {
            countOfThreads = Integer.parseInt(args[0]);
            countOfLoops = Integer.parseInt(args[1]);
        } else {
            System.out.println("Usage: \njava Deadlocked numberOfThreads numberOfLoopsForEach");
        }
        ExecutorService executorService = Executors.newFixedThreadPool(countOfThreads);

        final Deadlock deadlocked = new Deadlock();
        Runner array[] = new Runner[countOfThreads];
        for (int i = 0; i < countOfThreads; i++) {
            array[i] = new Runner(deadlocked, countOfLoops, i % 2 == 0);
        }
        System.out.printf("Created %d threads\n", countOfThreads);
        Arrays.asList(array).stream().forEach(thread -> executorService.execute(thread));
        System.out.println("Called start on all threads");
        awaitTerminationAfterShutdown(executorService);
    }

    public static void awaitTerminationAfterShutdown(ExecutorService threadPool) {
        System.out.printf("Calling shutdown on ThreadPool\n");
        threadPool.shutdown();
        try {
            if (!threadPool.awaitTermination(90, TimeUnit.SECONDS)) {
                System.out.println("Calling shutdownNow");
                threadPool.shutdownNow();
            } else {
                System.out.println("Threads finished in 90 seconds");
            }
        } catch (InterruptedException ex) {
            threadPool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    // Return a map entry (key-value pair) from the specified values
    public static <T, U> Map.Entry<T, U> of(T first, U second) {
        return new AbstractMap.SimpleEntry<>(first, second);
    }

    // to sleep a thread
    public static void sleep(long millis)
    {
        try
        {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Runner implements Runnable {
    Deadlock deadlocked;
    int loops;
    boolean toggle;

    Runner(Deadlock deadlock) {
        this(deadlock, 1);
    }

    Runner(Deadlock deadlock, int howManyTimes) {
        this(deadlock, howManyTimes, false);
    }

    Runner(Deadlock deadlock, int howManyTimes, boolean toggle) {
        deadlocked = deadlock;
        loops = howManyTimes;
        this.toggle = toggle;
    }

    @Override
    public void run() {
        int counter = 0;
        System.out.println("About to run: " + deadlocked.getThreadDetails() + " " + loops + " times");
        while (counter < loops) {
            if (toggle) {
                deadlocked.sayWorld();
                //DeadLocked.sleep(60l);
                deadlocked.sayHello();
            } else {
                deadlocked.sayHello();
                //DeadLocked.sleep(50l);
                deadlocked.sayWorld();
            }
            counter++;
        }
    }
}

