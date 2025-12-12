package org.secondgroup.sort;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.secondgroup.sort.algorithms.mergesort.MergeSort;
import org.secondgroup.sort.algorithms.quicksort.QuickSort;
import org.secondgroup.sort.algorithms.selectionsort.SelectionSort;

@DisplayName("Sort algorithms tests")
public class SortAlgorithmsTest {

    private String[] strings = {"rachel", "ross", "monica", "chandler", "joey", "phoebe"};

    @Test
    @DisplayName("Selection sort test")
    public void selectionSort(){
        SelectionSort.sort(strings);

        Assertions.assertArrayEquals(new String[]{"chandler", "joey", "monica", "phoebe", "rachel", "ross"}, strings);
    }

    @Test
    @DisplayName("Quick sort test")
    public void quickSort(){
        QuickSort.quickSort(strings);

        Assertions.assertArrayEquals(new String[]{"chandler", "joey", "monica", "phoebe", "rachel", "ross"}, strings);
    }

    @Test
    @DisplayName("Merge sort test")
    public void mergeSort(){
        MergeSort.mergeSort(strings);

        Assertions.assertArrayEquals(new String[]{"chandler", "joey", "monica", "phoebe", "rachel", "ross"}, strings);
    }
}
