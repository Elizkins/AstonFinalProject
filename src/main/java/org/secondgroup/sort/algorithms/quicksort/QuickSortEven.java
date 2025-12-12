package org.secondgroup.sort.algorithms.quicksort;

import org.secondgroup.sort.algorithms.sharedmethods.StudentFieldDefinition;
import org.secondgroup.student.model.Student;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
/**
 * Variation of sort, using Quick sort as base, but also uses additional methods, needed to sort only even values
 * by natural order and leaving the odd values in their original relative positions. Quick Sort relies heavily on
 * swapping elements across the entire array based on a pivot, which would disrupt the original relative positions of
 * the odd numbers. Our variation of this sort method isolates the elements to be sorted, allowing for their independent
 * sorting and then careful re-integration, thereby preserving the positions of the odd numbers. First, it extracts only
 * even values from original array. Then it uses Quick sorting algorithm that partitions an array around a chosen pivot
 * element and recursively sorts the resulting sub-arrays. In this case we have chosen a middle of array as a pivot.
 * Then it iterates original array and whenever encounters an even value's position, replace it with the next number
 * from the sorted even values array, in sequence. It requires comparator. Works with Student entity. */
public class QuickSortEven {

    public static <T extends Student> void quickSort(T[] array, Class<T> classItself, Comparator<T> compar) {

        if (array == null || array.length == 0) {
            return;
        }

        int field = StudentFieldDefinition.defineField(compar); // define on which field of Student it will treat
        T[] evenArr = extractEven(array, classItself, field); // extract temporary array contains even elements
        sort(evenArr, 0, evenArr.length - 1, compar); // Quick sort on temporary array

        int num = -1; // initialize variable, used in switch structure

        int counterEven = 0; // needed to count elements in sorted temporary array of even elements when replacing even
        // elements in original array
        for (int i = 0; i < array.length; i++) {
            try {
                switch (field) {
                    case (1): {
                        num = Integer.parseInt(array[i].getGroupNumber());
                        break;
                    }
                    case (2): {
                        num = (int) array[i].getAverageGrade();
                        break;
                    }
                    case (3): {
                        num = Integer.parseInt(array[i].getRecordBookNumber());
                        break;
                    }
                }
                if (num % 2 == 0) {
                    array[i] = evenArr[counterEven++];
                }
            } catch (NumberFormatException e) {
                throw new NumberFormatException("can not parse value");
            }
        }
    }

    public static <T extends Student> void sort(T[] array, int low, int high, Comparator<T> compar) {

        // Defining base case and recursion exit conditions
        if (low >= high) {
            return;
        }
        // Get pivot element index
        int pivotIndex = partition(array, low, high, compar);
        // Recursive sort call for left and right subarrays
        sort(array, low, pivotIndex - 1, compar);
        sort(array, pivotIndex + 1, high, compar);
    }

    private static <T extends Student> int partition(T[] arr, int low, int high, Comparator<T> compar) {

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
            if (compar.compare(arr[j], pivot) <= 0) { // Using comparator for ordering
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

    @SuppressWarnings("unchecked")
    public static <T extends Student> T[] extractEven(T[] arr, Class<T> classItself, int field) {
        T[] arrEven = (T[]) Array.newInstance(classItself, arr.length);
        int num = -1;
        int counterEven = 0;
        for (T t : arr) {
            try {
                switch (field) {
                    case (1): {
                        num = Integer.parseInt(t.getGroupNumber());
                        break;
                    }
                    case (2): {
                        num = (int) t.getAverageGrade();
                        break;
                    }
                    case (3): {
                        num = Integer.parseInt(t.getRecordBookNumber());
                        break;
                    }
                }
                if (num % 2 == 0) {
                    arrEven[counterEven++] = t;
                }
            } catch (NumberFormatException e) {
                throw new NumberFormatException("can not parse value");
            }
        }
        return Arrays.copyOf(arrEven, counterEven);
    }

}
