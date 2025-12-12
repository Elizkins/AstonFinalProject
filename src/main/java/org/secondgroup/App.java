package org.secondgroup;

import org.secondgroup.menu.Handler;
import org.secondgroup.menu.Menu;
import org.secondgroup.repository.StudentService;
import org.secondgroup.sort.TestObjectForStrategiesUse;
import org.secondgroup.sort.comparators.AvgGradeComparator;
import org.secondgroup.sort.comparators.GroupNumberComparator;
import org.secondgroup.sort.comparators.RegBookComparator;
import org.secondgroup.sort.strategy.*;
import org.secondgroup.student.model.Student;

import java.util.Comparator;

public class App {
    static StudentService studentService = new StudentService();

    public static void main(String[] args) {
        boolean[] isRunning = {true}; // создаем массив из одного элемента чтобы можно было менять значение в лямбде

        //главное меню
        Menu mainMenu = new Menu("Выберите действие\n" +
                "1. Ввод коллекции\n" +
                "2. Вывод коллекции\n" +
                "3. Сортировка коллекции\n" +
                "4. Очистка коллекции\n" +
                "5. Задать размер массива\n" +
                "6. Узнать количество вхождений элемента в коллекцию\n" +
                "7. Выход", "Некорректный ввод. Введите число от 1 до 6");
        mainMenu.addHandler(new Handler("4", studentService::clearCommand));
        mainMenu.addHandler(new Handler("5", studentService::changeCapacityCommand));
        mainMenu.addHandler(new Handler("6", studentService::countOcurrencyCommand));
        mainMenu.addHandler(new Handler("7", () -> isRunning[0] = false));

        //выбор способа ввода
        Menu inputMenu = new Menu("Выберите способ ввода\n" +
                "1. Ввод вручную\n" +
                "2. Ввод из файла\n" +
                "3. Случайная генерация\n" +
                "4. Вернуться назад", "Некорректный ввод. Введите число от 1 до 4");
        inputMenu.addHandler(new Handler("1", studentService::addStudentManuallyCommand));
        inputMenu.addHandler(new Handler("2", studentService::loadFromFileCommand));
        inputMenu.addHandler(new Handler("3", studentService::addRandomStudentsCommand));
        inputMenu.addHandler(new Handler("4", () -> {
            return;
        }));

        //Выбор способа вывода
        Menu outputMenu = new Menu("Выберите способ вывода\n" +
                "1. Вывод в консоль\n" +
                "2. Вывод в файл\n" +
                "3. Вернуться назад", "Некорректный ввод. Введите число от 1 до 3");
        outputMenu.addHandler(new Handler("1", studentService::printToConsoleCommand));
        outputMenu.addHandler(new Handler("2", studentService::saveToFileCommand));
        outputMenu.addHandler(new Handler("3", () -> {
            return;
        }));

        //Выбор способа сортировки
        Menu sortMenu = new Menu("Выберите способ сортировки\n" +
                "1. Сортировка слиянием\n" +
                "2. Быстрая сортировка\n" +
                "3. Сортировка выбором\n" +
                "4. Сортировка слиянием (усложненная)\n" +
                "5. Быстрая сортировка (усложненная)\n" +
                "6. Сортировка выбором (усложненная)\n" +
                "7. Изменить поле для сортировки\n" +
                "8. Вернуться назад",
                "Некорректный ввод. Введите число от 1 до 8"
        );
        sortMenu.addHandler(new Handler("1", () -> {
            studentService.sortStudentsCommand(new MergeSortStrategy());
        }));
        sortMenu.addHandler(new Handler("2", () -> {
            studentService.sortStudentsCommand(new QuickSortStrategy());
        }));
        sortMenu.addHandler(new Handler("3", () -> {
            studentService.sortStudentsCommand(new SelectionSortStrategy());
        }));
        sortMenu.addHandler(new Handler("4", () -> {
            studentService.additionalSortStudentsCommand(new MergeSortStrategyEven());
        }));
        sortMenu.addHandler(new Handler("5", () -> {
            studentService.additionalSortStudentsCommand(new QuickSortStrategyEven());
        }));
        sortMenu.addHandler(new Handler("6", () -> {
            studentService.additionalSortStudentsCommand(new SelectionSortStrategyEven());
        }));
        sortMenu.addHandler(new Handler("8", () -> {
            return;
        }));

        Menu comparatorMenu = new Menu("Выберите поле для сортировки\n" +
                "1. По группе\n" +
                "2. По оценке\n" +
                "3. По номеру зачетной книжки\n" +
                "4. Вернуться назад",
                "Некорректный ввод. Введите число от 1 до 4"
        );
        comparatorMenu.addHandler(new Handler("1", () -> {
            studentService.changeComparatorCommand(new GroupNumberComparator());
        }));
        comparatorMenu.addHandler(new Handler("2", () -> {
            studentService.changeComparatorCommand(new AvgGradeComparator());
        }));
        comparatorMenu.addHandler(new Handler("3", () -> {
            studentService.changeComparatorCommand(new RegBookComparator());
        }));
        comparatorMenu.addHandler(new Handler("4", () -> {
            return;
        }));

        sortMenu.addHandler(new Handler("7", comparatorMenu::run));

        mainMenu.addHandler(new Handler("1", inputMenu::run));
        mainMenu.addHandler(new Handler("2", outputMenu::run));
        mainMenu.addHandler(new Handler("3", sortMenu::run));

        while (isRunning[0]) mainMenu.run();
    }
}
