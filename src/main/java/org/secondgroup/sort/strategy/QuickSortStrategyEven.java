package org.secondgroup.sort.strategy;

import org.secondgroup.sort.algorithms.quicksort.QuickSortEven;
import org.secondgroup.student.model.Student;

import java.util.Comparator;


public class QuickSortStrategyEven implements SortStrategy {

    @Override
    public <T extends Student> void sort(T[] array, Class<T> classItself, Comparator<T> comparator) {
        System.out.print("Быстрая сортировка только четных значений ");
        QuickSortEven.quickSort(array, classItself, comparator);
    }
}
