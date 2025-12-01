package org.secondgroup.sort.strategy;

import org.secondgroup.sort.algorithms.MergeSort;


public class MergeSortStrategy implements SortStrategy {

    @Override
    public <T extends Comparable<? super T>> void sort(T[] array) {
        System.out.println("Сортировка слиянием");
        MergeSort.mergeSort(array);
    }
}
