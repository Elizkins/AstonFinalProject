package sort;

import sort.strategy.MergeSortStrategy;
import sort.strategy.QuickSortStrategy;
import sort.strategy.SelectionSortStrategy;
import sort.strategy.SortStrategy;

import java.util.Scanner;
/** Некоторый объект, который будет использовать стратегии, которые будут взаимозаменяемы независимо от него (объекта)*/
public class SomeObjectForStrategiesUse {

    protected SortStrategy sortStrategy;

    public SomeObjectForStrategiesUse(SortStrategy sortStrategy) {
        this.sortStrategy = sortStrategy;
    }

    public void changeStrategy(SortStrategy sortStrategy) {
        this.sortStrategy = sortStrategy;
    }

    public void execSort() {
        this.sortStrategy.execute();
    }

    // Test
    public static void main(String[] args) {

        SortStrategy quick = new QuickSortStrategy();
        SortStrategy merge = new MergeSortStrategy();
        SortStrategy selection = new SelectionSortStrategy();

        SomeObjectForStrategiesUse obj = new SomeObjectForStrategiesUse(quick);

        Scanner sc = new Scanner(System.in);

        loop:
        while (true) {
            System.out.println("0 - Быстря сортировка\n1 - Сортировка слиянием\n2 - Сортировка выбором\n" +
                    "3 - Выполнить сортировку\n 4 - Выйти");
            System.out.print("Введите значениe > ");
            int i = sc.nextInt();
            switch (i) {
                case 0 :
                    obj.changeStrategy(quick);
                    break;
                case 1 :
                    obj.changeStrategy(merge);
                    break;
                case 2 :
                    obj.changeStrategy(selection);
                    break;
                case 3 :
                    obj.execSort();
                    break;
                case 4:
                    break loop;
            }
        }
    }

}
