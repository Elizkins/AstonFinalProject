package org.secondgroup;

import org.secondgroup.menu.Handler;
import org.secondgroup.menu.Menu;
import org.secondgroup.repository.StudentRepository;
import org.secondgroup.sort.TestObjectForStrategiesUse;
import org.secondgroup.sort.strategy.MergeSortStrategy;
import org.secondgroup.sort.strategy.QuickSortStrategy;
import org.secondgroup.sort.strategy.SelectionSortStrategy;
import org.secondgroup.student.model.Student;

public class App {
    static StudentRepository studentRepository = new StudentRepository();
    static TestObjectForStrategiesUse strategy = new TestObjectForStrategiesUse(new SelectionSortStrategy());

    public static void main(String[] args) {
        boolean[] isRunning = {true}; // создаем массив из одного элемента чтобы можно было менять значение в лямбде

        //главное меню
        Menu mainMenu = new Menu("Выберите действие\n" +
                "1. Ввод коллекции\n" +
                "2. Вывод коллекции\n" +
                "3. Сортировка коллекции\n" +
                "4. Очистка коллекции\n" +
                "5. Задать размер массива\n" +
                "6. Выход", "Некорректный ввод. Введите число от 1 до 6");
        mainMenu.addHandler(new Handler("4", studentRepository::clear));
        mainMenu.addHandler(new Handler("5", studentRepository::changeCapacity));
        mainMenu.addHandler(new Handler("6", () -> isRunning[0] = false));

        //выбор способа ввода
        Menu inputMenu = new Menu("Выберите способ ввода\n" +
                "1. Ввод вручную\n" +
                "2. Ввод из файла\n" +
                "3. Случайная генерация\n" +
                "4. Вернуться назад", "Некорректный ввод. Введите число от 1 до 4");
        inputMenu.addHandler(new Handler("1", studentRepository::addStudentManually));
        inputMenu.addHandler(new Handler("2", studentRepository::loadFromFile));
        inputMenu.addHandler(new Handler("3", studentRepository::addRandomStudents));
        inputMenu.addHandler(new Handler("4", () -> {
            return;
        }));

        //Выбор способа вывода
        Menu outputMenu = new Menu("Выберите способ вывода\n" +
                "1. Вывод в консоль\n" +
                "2. Вывод в файл\n" +
                "3. Вернуться назад", "Некорректный ввод. Введите число от 1 до 3");
        outputMenu.addHandler(new Handler("1", studentRepository::printAllStudents));
        outputMenu.addHandler(new Handler("2", studentRepository::saveToFile));
        outputMenu.addHandler(new Handler("3", () -> {
            return;
        }));

        //Выбор способа сортировки
        Menu sortMenu = new Menu("Выберите способ сортировки\n" +
                "1. Сортировка слиянием\n" +
                "2. Быстрая сортировка\n" +
                "3. Сортировка выбором\n" +
                "4. Вернуться назад",
                "Некорректный ввод. Введите число от 1 до 4"
        );
        sortMenu.addHandler(new Handler("1", () -> {
            strategy.changeStrategy(new MergeSortStrategy());
            studentRepository.sortStudents(strategy);
        }));
        sortMenu.addHandler(new Handler("2", () -> {
            strategy.changeStrategy(new QuickSortStrategy());
            studentRepository.sortStudents(strategy);
        }));
        sortMenu.addHandler(new Handler("3", () -> {
            strategy.changeStrategy(new SelectionSortStrategy());
            studentRepository.sortStudents(strategy);
        }));
        sortMenu.addHandler(new Handler("4", () -> {
            return;
        }));

        mainMenu.addHandler(new Handler("1", inputMenu::run));
        mainMenu.addHandler(new Handler("2", outputMenu::run));
        mainMenu.addHandler(new Handler("3", sortMenu::run));

        while (isRunning[0]) mainMenu.run();
    }
}
