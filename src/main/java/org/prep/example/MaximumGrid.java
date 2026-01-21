package org.prep.example;

import java.util.*;

public class MaximumGrid {

  public static void main(String[] args) {
    MaximumGrid grid = new MaximumGrid();
    System.out.println(
        grid.findAreas(4, 4, List.of(1), List.of(1)).get(0)
    );
    System.out.println(
        grid.findAreas(6, 4, List.of(1,4,5), List.of(2)).get(0)
    );
    System.out.println(
        grid.findAreas(7, 7, List.of(1,2,3), List.of(5,1)).get(0)
    );
    System.out.println(
        grid.findAreas(6, 4, List.of(1,4,5), List.of(2,3)).get(0)
    );
  }

  List<Long> findAreas(int rows, int cols, List<Integer> hBars, List<Integer> vBars) {
    List<Long> areas = new ArrayList<>();
    Map<Integer, Integer> areaByH = maxContiguous(hBars, rows);
    Map<Integer, Integer> areaByV = maxContiguous(vBars, cols);
    for (Map.Entry<Integer, Integer> entry : areaByH.entrySet()) {
      for (Map.Entry<Integer, Integer> entry2 : areaByV.entrySet()) {
        areas.add(Long.valueOf(entry.getValue()) * Long.valueOf(entry2.getValue()));
      }
    }
    Collections.sort(areas, Comparator.reverseOrder());
    return areas;
  }

  Map<Integer, Integer> maxContiguous(List<Integer> entries, int total) {
    Map<Integer, Integer> sumForEntry = new LinkedHashMap<>();
    Set<Integer> visited = new HashSet<>();
    for (Integer entry: entries) {
      if (entry >= total || entry <= 0) continue;
      int counter = entry;
      int area = 1;
      boolean increase = true;
      if (visited.contains(entry)) {
        continue;
      }
      visited.add(entry);
      while(true) {
        if (increase) {
          counter++;
          if (counter < total && entries.contains(counter)) {
            visited.add(counter);
            area++;
          } else {
            increase = false;
            counter = entry;
          }
        } else {
          counter--;
          if (counter > 0 && entries.contains(counter)) {
            visited.add(counter);
            area++;
          } else {
            break;
          }
        }
      }
      area++;
      sumForEntry.put(entry, area);
    }
    return sumForEntry;
  }
}
