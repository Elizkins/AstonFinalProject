package org.secondgroup.sort;

import org.secondgroup.sort.strategy.MergeSortStrategy;
import org.secondgroup.sort.strategy.QuickSortStrategy;
import org.secondgroup.sort.strategy.SelectionSortStrategy;
import org.secondgroup.sort.strategy.SortStrategy;

import java.util.Scanner;

// Некоторый объект, использующй стратегии, которые будут взаимозаменяемы независимо от него (объекта)
public class SomeObjectForStrategiesUse {

    protected SortStrategy sortStrategy;

    public SomeObjectForStrategiesUse(SortStrategy sortStrategy) {
        this.sortStrategy = sortStrategy;
    }

    public void changeStrategy(SortStrategy sortStrategy) {
        this.sortStrategy = sortStrategy;
    }

    public <T extends Comparable<? super T>> void execSort(T[] array) {
        this.sortStrategy.sort(array);
    }

    public static <T> void printArray(T[] arr) {
        for (T element : arr) {
            System.out.print(element + " ");
        }
        System.out.println();
    }

    // Test
    public static void main(String[] args) {

        SortStrategy quick = new QuickSortStrategy();
        SortStrategy merge = new MergeSortStrategy();
        SortStrategy selection = new SelectionSortStrategy();

        SomeObjectForStrategiesUse obj = new SomeObjectForStrategiesUse(selection);

        Scanner sc = new Scanner(System.in);

        String[] array = new String[]{"A", "V", "D", "C", "R", "B"};
        Integer[] array2 = new Integer[]{1, 5, 4, 5, 8, 14, 69, 7, 56, 58, 69, 7};

        obj.execSort(array);
        SomeObjectForStrategiesUse.printArray(array);

        loop:
        while (true) {
            System.out.println("0 - Быстря сортировка\n1 - Сортировка слиянием\n2 - Сортировка выбором\n" +
                    "3 - Выполнить сортировку\n4 - Показать отсортированный массив\n5 - Выход");
            System.out.print("Введите значениe > ");
            int i = sc.nextInt();
            switch (i) {
                case 0:
                    obj.changeStrategy(quick);
                    break;
                case 1:
                    obj.changeStrategy(merge);
                    break;
                case 2:
                    obj.changeStrategy(selection);
                    break;
                case 3:
                    obj.execSort(array2);
                    break;
                case 4:
                    SomeObjectForStrategiesUse.printArray(array2);
                    break;
                case 5:
                    break loop;
            }
        }

    }

}
