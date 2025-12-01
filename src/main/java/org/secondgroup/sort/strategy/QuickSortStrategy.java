package org.secondgroup.sort.strategy;

import org.secondgroup.sort.algorithms.QuickSort;


public class QuickSortStrategy implements SortStrategy {

    @Override
    public <T extends Comparable<? super T>> void sort(T[] array) {
        System.out.println("Быстрая сортировка");
        QuickSort.quickSort(array);
    }
}
