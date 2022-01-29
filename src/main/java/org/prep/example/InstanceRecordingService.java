package org.prep.example;

import org.prep.example.model.InstanceEntry;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.*;

public class InstanceRecordingService {

    public static final long HOUR = 60 * 60 * 1000;
    public static final long DAY = 24 * HOUR;
    private static ExecutorService executor;

    public static void main(String args[]) {
        boolean showUsage = false;
        Set<InstanceEntry> instanceEntries = new HashSet();
        int counter = 0;
        executor = Executors.newSingleThreadExecutor();
        CompletionService<Boolean> completionService = new ExecutorCompletionService(executor);
        if (args.length > 0) {
            for (String arg : args) {
                String[] split = arg.split(":");
                if (split.length < 2) {
                    showUsage = true;
                    break;
                }
                System.out.println("Split data: " + split[0] + "--" + split[1]);
                int theId = Integer.parseInt(split[0]);
                long startTime = Long.parseLong(split[1]);
                InstanceEntry instanceEntry = new InstanceEntry(theId);
                instanceEntries.add(instanceEntry);
                System.out.println("Adding increments");
                addIncrements(instanceEntry, startTime, completionService);
                counter++;
            }
        } else {
            showUsage = true;
        }
        if (showUsage) {
            System.out.println("Usage: 1:180424845 2:230424845 120:4520424845");
        }
        if (counter > 0) {
            int received = 0;
            boolean errors = false;

            while (received < counter && !errors) {
                try {
                    System.out.println("Next up blocking call");
                    completionService.take().get(); //blocks if none available
                    received++;
                    System.out.println("Unblocked counter: " + counter + " tasks finished: " + received);
                } catch (Exception e) {
                    //log
                    e.printStackTrace();
                    errors = true;
                }
            }
            System.out.println("Shutting down executor service");
            executor.shutdown();
        }

    }

    private static void addIncrements(final InstanceEntry entry, final long startTime,
                                      final CompletionService<Boolean> executorService) {
        CompletableFuture.runAsync(() -> {
            System.out.println("Running async");
            Random random = new Random(startTime + DAY);
            long previousTime = startTime;
            for (int i = 0; i < 10; i++, previousTime += DAY) {
                Calendar prev = Calendar.getInstance();
                prev.setTimeInMillis(previousTime);
                System.out.println("Entry: " + entry.getId() + " Previous time: " + prev.getTime());
                long enterTime = previousTime;
                long exitTime = enterTime + (long) random.nextFloat() * HOUR * 8;
                entry.addEntry(enterTime);
                entry.addExit(exitTime);
            }
            executorService.submit(() -> true);
        });
    }

}

/*
React - React native
Product led growth tools
Breaking down silos
Developing common APIs
Growth by acquisition - unifying disparate systems.
Teams are at their own pace of adopting newer technologies.
Email security product is already in Azure, some of the services are in AWS.
18 petabytes of data.
For mentoring - get the team and individual goals aligned for less friction and preventing
burn out.
Have quicker release cycles to reduce risk in bigger releases.
*/