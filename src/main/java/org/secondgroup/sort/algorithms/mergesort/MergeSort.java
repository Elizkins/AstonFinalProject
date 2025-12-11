package org.secondgroup.sort.algorithms.mergesort;

/**
 * Merge Sort is an efficient, <b>stable</b>, divide-and-conquer sorting algorithm that works by recursively breaking
 * down an array into single-element sub-arrays and then merging those sub-arrays back together in sorted order. It has
 * a time complexity of O(n log n) in all cases (best, average, and worst). Here it uses Generics, so classes meant
 * to be sorted should implement {@link Comparable} interface because of use {@link Comparable#compareTo(Object)} in
 * {@link MergeSort#merge(Comparable[], int, int, int)} method.
 */
public class MergeSort {

    public static <T extends Comparable<? super T>> void mergeSort(T[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        sort(array, 0, array.length - 1);
    }

    private static <T extends Comparable<? super T>> void sort(T[] array, int left, int right) {

        // Defining base case and recursion exit conditions
        if (left < right) {
            int mid = (left + right) / 2;

            // Recursive halves sorting
            sort(array, left, mid);
            sort(array, mid + 1, right);

            // Then merging sorted halves
            merge(array, left, mid, right);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T extends Comparable<? super T>> void merge(T[] array, int left, int mid, int right) {

        // Creating temporary arrays
        int length1 = mid - left + 1;
        int length2 = right - mid;

        // Why @SuppressWarnings: here we are certain that the cast is safe and will not lead to a ClassCastException
        // because of using it only with Student class in a study project
        T[] leftSubArray = (T[]) new Comparable[length1];
        T[] rightSubArray = (T[]) new Comparable[length2];

        // Copying in temp arrays
        System.arraycopy(array, left, leftSubArray, 0, length1);
        System.arraycopy(array, mid + 1, rightSubArray, 0, length2);

        // Merging temp arrays back in origin array
        int i = 0, j = 0, k = left;
        while (i < length1 && j < length2) {
            if (leftSubArray[i].compareTo(rightSubArray[j]) <= 0) { // Using compareTo for ordering
                array[k] = leftSubArray[i];
                i++;
            } else {
                array[k] = rightSubArray[j];
                j++;
            }
            k++;
        }

        // Copying remaining elements of left subarray
        while (i < length1) {
            array[k] = leftSubArray[i];
            i++;
            k++;
        }

        // Copying remaining elements of right subarray
        while (j < length2) {
            array[k] = rightSubArray[j];
            j++;
            k++;
        }
    }
}
