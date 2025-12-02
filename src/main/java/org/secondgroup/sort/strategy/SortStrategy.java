package org.secondgroup.sort.strategy;

/**
 * Interface for implementation to realize selection mechanism of sorting type strategies. Each strategy encapsulates
 * separate sorting algorithm. Allows to interchange algorithms directly during program execution.
 */

public interface SortStrategy {

    <T extends Comparable<? super T>> void sort(T[] array);
}
