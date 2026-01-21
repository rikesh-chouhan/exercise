package org.prep.example;

import java.util.LinkedHashSet;
import java.util.Set;

public class JumpGame {

  public boolean canJump(int[] nums) {
    if (nums.length == 0) return false;
    if (nums.length == 1) {
      return true;
    }
    if (nums[0] == 0) return false;

    int i = 0;
    Set<Integer> canReachEnd = new LinkedHashSet<>();
    for (i=(nums.length-2);i>(-1);i--) {
      int maxJump = nums[i];
      boolean canReach = false;
      if (maxJump>0) {
        for (int j=maxJump;j>-1; j--) {
          if (i+1+j >= nums.length) {
            canReach = true;
            break;
          }
        }
      }
      if (canReach) {
        canReachEnd.add(i);
      }
    }

    i = 0;
    while (i < nums.length-2) {
      int maxJump = nums[i];
      if (maxJump > 0) {
        for (int j=maxJump;j>-1;j--) {
          if (canReachEnd.contains(i+j)) {
            return true;
          }
        }
      }
    }

    return false;
  }
}