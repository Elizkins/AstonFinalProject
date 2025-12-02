package org.secondgroup.sort.algorithms;

/**
 * Selection sort is an in-place, comparison-based sorting algorithm that repeatedly finds the minimum element from
 * the unsorted part of a list and swaps it with the first element of the unsorted part. Also, here it
 * uses Generics, so classes meant to be sorted should implement {@link Comparable} interface because of use
 * {@link Comparable#compareTo(Object)} in {@link SelectionSort#sort(Comparable[])} method.
 */
public class SelectionSort {

    public static <T extends Comparable<? super T>> void sort(T[] arr) {

        int n = arr.length;

        // Moving the border of non-sorted subarray
        for (int i = 0; i < n - 1; i++) {

            // Finding minimal element in non-sorted part of array
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {

                // Using compareTo() for ordering: negative means that arr[j] smaller
                if (arr[j].compareTo(arr[minIndex]) < 0) {
                    minIndex = j;
                }
            }

            // Doing swap between founded minimal element and first element of non-sorted array
            T temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
        }
    }
}
