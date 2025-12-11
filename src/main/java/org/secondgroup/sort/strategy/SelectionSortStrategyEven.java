package org.secondgroup.sort.strategy;

import org.secondgroup.sort.algorithms.selectionsort.SelectionSortEven;
import org.secondgroup.student.model.Student;

import java.util.Comparator;


public class SelectionSortStrategyEven implements SortStrategy {

    @Override
    public <T extends Student> void sort(T[] array, Class<T> classItself, Comparator<T> comparator) {
        System.out.print("Cортировка выбором только четных значений ");
        SelectionSortEven.sort(array, comparator);
    }
}
