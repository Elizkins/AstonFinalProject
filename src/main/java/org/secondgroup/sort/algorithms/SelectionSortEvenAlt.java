package org.secondgroup.sort.algorithms;

import org.secondgroup.student.model.Student;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;

import static org.secondgroup.sort.algorithms.SelectionSortEven.defineField;

/**
 * Variation of sort, using Selection sort as base, but also uses additional methods, needed to sort only even values
 * by natural order and leaving the odd values in their original relative positions. First, it extracts only even values
 * from original array. Then it uses Selection sorting algorithm that repeatedly finds the minimum element from
 * the unsorted part of even values and swaps it with the first element of the unsorted part. Then it iterates original
 * array and whenever encounters an even value's position, replace it with the next number from the sorted even values
 * array, in sequence. It requires comparator. Works with Student entity.
 */
public class SelectionSortEvenAlt {

    public static <T extends Student> void sort(T[] arr, Class<T> classItself, Comparator<T> compar) {

        int field = defineField(compar); // define on which field of Student it will treat
        T[] evenArr = extractEven(arr, classItself, field); // extract temporary array contains even elements
        T[] sortedEvenArr = sortEven(evenArr, compar); // Selection sort on temporary array

        int num = -1; // initialize variable, used in switch structure

        int counterEven = 0; // needed to count elements in sorted temporary array of even elements when replacing even
        // elements in original array
        for (int i = 0; i < arr.length; i++) {
            try {
                switch (field) {
                    case (1): {
                        num = Integer.parseInt(arr[i].getGroupNumber());
                        break;
                    }
                    case (2): {
                        num = (int) arr[i].getAverageGrade();
                        break;
                    }
                    case (3): {
                        num = Integer.parseInt(arr[i].getRecordBookNumber());
                        break;
                    }
                }
                if (num % 2 == 0) {
                    arr[i] = sortedEvenArr[counterEven++];
                }
            } catch (ClassCastException e) {
                throw new ClassCastException("Alarm! can not cast");
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static <T extends Student> T[] extractEven(T[] arr, Class<T> classItself, int field) {
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
            } catch (ClassCastException e) {
                throw new ClassCastException("Alarm! can not cast to int");
            }
        }
        return Arrays.copyOf(arrEven, counterEven);
    }

    private static <T extends Student> T[] sortEven(T[] arr, Comparator<T> comparator) {

        int n = arr.length;

        // Moving the border of non-sorted subarray
        for (int i = 0; i < n - 1; i++) {

            // Finding minimal element in non-sorted part of array
            int minIndex = i;

            for (int j = i + 1; j < n; j++) {
                // Using compareTo() for ordering: negative means that arr[j] smaller
                if (comparator.compare(arr[j], arr[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            // Doing swap between founded minimal element and first element of non-sorted array
            T temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
        }
        return arr;
    }
}
