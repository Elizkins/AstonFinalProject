package org.secondgroup.sort.algorithms;

/**
 * Quicksort is a popular, efficient, <b>not stable</b>, divide-and-conquer sorting algorithm that partitions an array around a chosen
 * pivot element and recursively sorts the resulting sub-arrays. Its average-case time complexity is O(n log n), but
 * it strongly depends on pivot selecting. In this case we have chosen a middle of array as a pivot. Also, here it
 * uses Generics, so classes meant to be sorted should implement {@link Comparable} interface because of use
 * {@link Comparable#compareTo(Object)} in {@link QuickSort#partition(Comparable[], int, int)} method.
 */
public class QuickSort {

    public static <T extends Comparable<? super T>> void quickSort(T[] array) {

        if (array == null || array.length == 0) {
            return;
        }
        sort(array, 0, array.length - 1);
    }

    private static <T extends Comparable<? super T>> void sort(T[] array, int low, int high) {
        // Defining base case and recursion exit conditions
        if (low >= high) {
            return;
        }
        // Get pivot element index
        int pivotIndex = partition(array, low, high);
        // Recursive sort call for left and right subarrays
        sort(array, low, pivotIndex - 1);
        sort(array, pivotIndex + 1, high);
    }

    private static <T extends Comparable<? super T>> int partition(T[] arr, int low, int high) {

        // Worst case, if pivot element is last or first
        // T pivot = arr[high];

        // Average case, select pivot as middle - significantly reduces recursion calls stack
        int middle = low + (high - low) / 2;
        T pivot = arr[middle];

        // Exchanging of pivot element with last element
        T tmp = arr[middle];
        arr[middle] = arr[high];
        arr[high] = tmp;

        // Minimal element index
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j].compareTo(pivot) <= 0) { // Using compareTo for ordering
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    private static <T> void swap(T[] arr, int i, int j) {
        T tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
