package org.secondgroup.sort.strategy;

import org.secondgroup.sort.algorithms.selectionsort.SelectionSortEvenAlt;
import org.secondgroup.student.model.Student;

import java.util.Comparator;


public class SelectionSortStrategyEvenAlt implements SortStrategy {

    @Override
    public <T extends Student> void sort(T[] array, Class<T> classItself, Comparator<T> comparator) {
        System.out.print("Cортировка выбором только четных значений (альтернативная) ");
        SelectionSortEvenAlt.sort(array, classItself, comparator);
    }
}
