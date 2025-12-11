package org.secondgroup.sort;

import org.secondgroup.sort.strategy.SortStrategy;

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
}
