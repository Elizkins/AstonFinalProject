package org.secondgroup.sort.algorithms.mergesort;

import org.secondgroup.sort.algorithms.sharedmethods.StudentFieldDefinition;
import org.secondgroup.student.model.Student;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
/**
 * Variation of sort, using Merge sort as base, but also uses additional methods, needed to sort only even values
 * by natural order and leaving the odd values in their original relative positions. First, it extracts only even values
 * from original array. Then it uses Merge sorting algorithm that recursively breaking down an array into single-element
 * sub-arrays and then merging those sub-arrays back together in sorted order. Then it iterates original array and
 * whenever encounters an even value's position, replace it with the next number from the sorted even values array, in
 * sequence. It requires comparator. Works with Student entity.
 */
public class MergeSortEven {

    public static <T extends Student> void mergeSort(T[] array, Class<T> classItself, Comparator<T> compar) {

        if (array == null || array.length == 0) {
            return;
        }

        int field = StudentFieldDefinition.defineField(compar); // define on which field of Student it will treat
        T[] evenArr = extractEven(array, classItself, field); // extract temporary array contains even elements
        sort(evenArr, 0, evenArr.length - 1, classItself, compar); // Quick sort on temporary array

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

    private static <T extends Student> void sort(T[] array, int left, int right, Class<T> classItself,
                                                 Comparator<T> compar) {

        // Defining base case and recursion exit conditions
        if (left < right) {
            int mid = (left + right) / 2;

            // Recursive halves sorting
            sort(array, left, mid, classItself, compar);
            sort(array, mid + 1, right, classItself, compar);

            // Then merging sorted halves
            merge(array, left, mid, right, classItself, compar);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T extends Student> void merge(T[] array, int left, int mid, int right, Class<T> classItself,
                                                  Comparator<T> compar) {

        // Creating temporary arrays
        int length1 = mid - left + 1;
        int length2 = right - mid;


        T[] leftSubArray = (T[]) Array.newInstance(classItself, length1);
        T[] rightSubArray = (T[]) Array.newInstance(classItself, length2);

        // Copying in temp arrays
        System.arraycopy(array, left, leftSubArray, 0, length1);
        System.arraycopy(array, mid + 1, rightSubArray, 0, length2);

        // Merging temp arrays back in origin array
        int i = 0, j = 0, k = left;
        while (i < length1 && j < length2) {
            if (compar.compare(leftSubArray[i], rightSubArray[j]) <= 0) { // Using comparator for ordering
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
