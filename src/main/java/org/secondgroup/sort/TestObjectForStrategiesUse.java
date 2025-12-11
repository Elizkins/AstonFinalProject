package org.secondgroup.sort;

import org.secondgroup.sort.strategy.SortStrategy;
import org.secondgroup.student.model.Student;

import java.util.Comparator;

// Some object that uses strategies, which meant to be interchangeable independently of this object
public class TestObjectForStrategiesUse {

    protected SortStrategy sortStrategy;

    public TestObjectForStrategiesUse(SortStrategy sortStrategy) {
        this.sortStrategy = sortStrategy;
    }

    public void changeStrategy(SortStrategy sortStrategy) {
        this.sortStrategy = sortStrategy;
    }

    public <T extends Comparable<? super T>> void execSort(T[] array) {
        this.sortStrategy.sort(array);
    }

    public <T extends Student> void execSortOnEven(T[] array,Class<T> classItself, Comparator<T> comparator) {
        this.sortStrategy.sort(array, classItself, comparator);
    }

    public static <T> void printArray(T[] arr) {
        for (T element : arr) {
            System.out.println(element);
        }
        System.out.println();
    }

}
