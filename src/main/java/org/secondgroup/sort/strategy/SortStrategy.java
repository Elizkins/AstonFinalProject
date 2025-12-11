package org.secondgroup.sort.strategy;

import org.secondgroup.student.model.Student;

import java.util.Comparator;

/**
 * Interface for implementation to realize selection mechanism of sorting type strategies. Each strategy encapsulates
 * separate sorting algorithm. Allows to interchange algorithms directly during program execution.
 */

public interface SortStrategy {

    default <T extends Comparable<? super T>> void sort(T[] array) {
        System.out.println("Default sort strategy for Comparable implementators, does nothing by default:-)");
    }

    default <T extends Student> void sort(T[] array, Class<T> classItself, Comparator<T> comparator) {
        System.out.println("Default sort strategy for objects with custom comparators, does nothing too :-)");
    }


}
