package org.secondgroup.sort.strategy;

import org.secondgroup.sort.algorithms.SelectionSort;


public class SelectionSortStrategy implements SortStrategy {

    @Override
    public <T extends Comparable<? super T>> void sort(T[] array) {
        System.out.println("Cортировка выбором");
        SelectionSort.sort(array);
    }
}
