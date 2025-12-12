package org.secondgroup.sort.algorithms.selectionsort;

import org.secondgroup.sort.algorithms.sharedmethods.StudentFieldDefinition;
import org.secondgroup.student.model.Student;

import java.util.Comparator;

/**
 * Variation of sort, using Selection sort as base, but also uses additional methods, needed to sort only even values
 * by natural order and leaving the odd values in their original relative positions. It uses Selection sorting
 * algorithm that repeatedly finds the minimal even element from the unsorted part of values and swaps it with the
 * first even element of the unsorted part. Odd elements are ignored. It requires comparator. Works with Student entity.
 */
public class SelectionSortEven {

    public static <T extends Student> void sort(T[] arr, Comparator<T> compar) {

        int field = StudentFieldDefinition.defineField(compar); // define on which field of Student it will treat
        int n = arr.length;

        // Moving the border of non-sorted part of array
        int minIndex;
        for (int i = 0; i < n - 1; i++) {

            // Finding minimal element in non-sorted part of array
            if (getElementValue(arr, i, field) % 2 == 0) {
                minIndex = i;
            } else continue;

            for (int j = i + 1; j < n; j++) {
                int tmp = getElementValue(arr, j, field);
                // Using comparator for ordering: negative means that arr[j] smaller
                if (tmp % 2 == 0 && compar.compare(arr[j], arr[minIndex]) < 0) {
                    minIndex = j;
                }
            }

            // Doing swap between founded minimal element and first element of non-sorted array
            T temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
        }
    }

    private static <T extends Student> int getElementValue(T[] array, int index, int field) {
        int num;
        switch (field) {
            case (1): {
                num = Integer.parseInt(array[index].getGroupNumber());
                break;
            }
            case (2): {
                num = (int) array[index].getAverageGrade();
                break;
            }
            case (3): {
                num = Integer.parseInt(array[index].getRecordBookNumber());
                break;
            }
            default:
                throw new IllegalArgumentException("wrong field name or such doesn't exist");
        }
        return num;
    }

}
