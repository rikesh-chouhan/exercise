package org.prep.example;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EVDataExaminer {

  private static final List<String> COL_NAMES = new ArrayList<>();
  static {
    COL_NAMES.add("county");
    COL_NAMES.add("electric utility");
    COL_NAMES.add("state");
    COL_NAMES.add("model year");
    COL_NAMES.add("make");
    COL_NAMES.add("electric vehicle type");
  }

  public static void main(String[] args) {
    String fileName = args.length > 0 ? args[0] : "ev_population_data.csv";
    String path = ClassLoader.getSystemClassLoader().getResource(fileName).getPath();
    List<String[]> rows = Findings.readCsv(path);
    List<String> colNames = new ArrayList<>();
    List<Integer> colsOfInterest = new ArrayList<>();

    int counter = 0;
    for (String row : rows.get(0)) {
      if (COL_NAMES.contains(row.trim().toLowerCase())) {
        colsOfInterest.add(counter);
      }
      colNames.add(row);
      counter++;
    }
    System.out.println("Column Names: \n" + colNames);
    System.out.println("Columns Of Interest: " + colsOfInterest);
    System.out.println("Number of Rows: " + rows.size());
    int processors = Runtime.getRuntime().availableProcessors();
    ExecutorService executor = Executors.newFixedThreadPool(processors);
    int start = 1;
    int end = rows.size() - start;
    List<PortionRunner> runners = new ArrayList<>();
    for (int thread=0; thread<processors; thread++) {
      int size = end/processors;
      if (thread==processors-1) {
        size = end-start;
      }
      PortionRunner runner = new PortionRunner(rows, start, size, colsOfInterest);
      runners.add(runner);
      executor.execute(runner);
      start = start + size;
    }

    Map<String, Set<String>> countiesByState = new HashMap<>(50);
    Map<String, Map<String, Set<String>>> nestedCountiesUtilities = new HashMap<>(50);
    boolean continueRunning = true;
    while (continueRunning) {
      int i = 0;
      int running = 0;
      while (i<runners.size()) {
        PortionRunner runner = runners.get(i);
        if (!runner.executed) {
          running++;
        } else {
          for (Map.Entry<String, Set<String>> entry: runner.countiesByState.entrySet()) {
            if (countiesByState.containsKey(entry.getKey())) {
              countiesByState.get(entry.getKey()).addAll(entry.getValue());
            } else {
              countiesByState.put(entry.getKey(), entry.getValue());
            }
          }
        }
      }

      if (running == 0) {
        continueRunning = false;
      } else {
        try  {
          Thread.sleep(500);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }

  }

  private static class PortionRunner implements Runnable {
    private final List<String[]> rows;
    private final int start;
    private final int leng;
    private final List<Integer> colsOfInterest;
    boolean executed = false;
    Map<String, Set<String>> countiesByState = new HashMap<>(50);
    Map<String, Map<String, Set<String>>> nestedCountiesUtilities = new HashMap<>(50);

    public PortionRunner(List<String[]> rows, int start, int leng, List<Integer> colsOfInterest) {
      this.rows = rows;
      this.start = start;
      this.leng = leng;
      this.colsOfInterest = colsOfInterest;
    }

    @Override
    public void run() {

      int counter = start;
      //*************
      while (counter < start+leng) {
        String[] theRow = rows.get(counter);
        String state = theRow[colsOfInterest.get(1)].trim().toLowerCase();
        String county = theRow[colsOfInterest.get(0)].trim().toLowerCase();
        countiesByState.computeIfAbsent(state, k -> new HashSet<>(100)).add(county);
        String utils = theRow[colsOfInterest.get(2)];
        String[] utilValues = utils.split("\\|");
        nestedCountiesUtilities.computeIfAbsent(state, k -> new HashMap<>(100));
        Map<String, Set<String>> countiesSharingUtilities = nestedCountiesUtilities.get(state);
        for (String aUtil: utilValues) {
          String trimmedUtil = aUtil.trim();
          if (!trimmedUtil.isEmpty()) {
            countiesSharingUtilities.computeIfAbsent(trimmedUtil,
                k -> new HashSet<>(50)).add(county);
          }
        }

        counter++;
        if (counter%1000 == 0) {
          System.out.println("Processed: " + counter);
        }
      }
      executed = true;
      //*************
    }
  }

}
