package org.secondgroup.sort.strategy;

import org.secondgroup.sort.algorithms.mergesort.MergeSortEven;
import org.secondgroup.student.model.Student;

import java.util.Comparator;


public class MergeSortStrategyEven implements SortStrategy {

    @Override
    public <T extends Student> void sort(T[] array, Class<T> classItself, Comparator<T> comparator) {
        System.out.print("Сортировка слиянием только четных значений ");
        MergeSortEven.mergeSort(array, classItself, comparator);
    }
}
