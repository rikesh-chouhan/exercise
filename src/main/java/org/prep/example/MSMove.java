package org.prep.example;

import java.util.Arrays;

public class MSMove {

    static int[] helper = null;

    public static void main(String []args) {
        System.out.println("Started");
        int[] arr = {4, 2};
        helper = new int[arr.length];
        int helpCounter = 0;
        for (int each: arr) helper[helpCounter++] = each;

        mergeSort(arr, 0, arr.length-1);
        Arrays.stream(arr).boxed().forEach(a -> System.out.printf("%d ",a));
        System.out.println();
        int[] array = {4, 10, 23, 1, 12, 5, 8, 20, -3};
        helper = new int[array.length];
        helpCounter = 0;
        for (int each: array) helper[helpCounter++] = each;

        mergeSort(array, 0, array.length-1);
        Arrays.stream(array).boxed().forEach(a -> System.out.printf("%d ",a));
        System.out.println();
    }

    public static void mergeSort(int[] array, int left, int right) {
         if (left < right) {
             int mid = left + (right - left)/2;
             mergeSort(array, left, mid);
             mergeSort(array, mid+1, right);
             mergeArray(array, left, mid, right);
         }
    }

    public static void mergeArray(int[] array, int left, int mid, int right) {
        for (int i = left; i <= right; i++) {
            helper[i] = array[i];
        }

        int i = left;
        int j = mid+1;
        int k = left;

        while(i <= mid && j<= right) {
            if (helper[i] <= helper[j]) {
                array[k] = helper[i];
                i++;
            } else {
                array[k] = helper[j];
                j++;
            }
            k++;
        }

        while (i <= mid) {
            array[k] = helper[i];
            k++;
            i++;
        }
    }
}
