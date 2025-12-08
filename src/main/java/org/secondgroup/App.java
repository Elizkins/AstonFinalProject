package org.secondgroup;

import org.secondgroup.menu.Handler;
import org.secondgroup.menu.Menu;
import org.secondgroup.repository.StudentRepository;
import org.secondgroup.sort.TestObjectForStrategiesUse;
import org.secondgroup.sort.strategy.MergeSortStrategy;
import org.secondgroup.sort.strategy.QuickSortStrategy;
import org.secondgroup.sort.strategy.SelectionSortStrategy;
import org.secondgroup.student.model.Student;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    static StudentRepository studentRepository = new StudentRepository();
    static TestObjectForStrategiesUse strategy = new TestObjectForStrategiesUse(new SelectionSortStrategy());

    public static void main( String[] args )
    {

        boolean[] isRunning = {true}; // создаем массив из одного элемента чтобы можно было менять значение в лямбде

        //главное меню
        Menu mainMenu = new Menu("Выберите действие\n" +
                "1. Ввод коллекции\n" +
                "2. Вывод коллекции\n" +
                "3. Сортировка коллекции\n" +
                "4. Выход", "Некорректный ввод. Введите число от 1 до 4");
        mainMenu.addHandler(new Handler("4", () -> {
            isRunning[0] = false;
        }));

        //выбор способа ввода
        Menu inputMenu = new Menu("Выберите способ ввода\n" +
                "1. Ввод вручную\n" +
                "2. Ввод из файла\n" +
                "3. Случайная генерация", "Некорректный ввод. Введите число от 1 до 3");
        inputMenu.addHandler(new Handler("1", studentRepository::addStudentManually));
        inputMenu.addHandler(new Handler("2", () -> {
            System.out.println("Введите название файла");
            Scanner scanner = new Scanner(System.in);
            String file = scanner.nextLine();
            scanner.close();
            studentRepository.loadFromFile(file);
        }));
        inputMenu.addHandler(new Handler("3", () -> {
            System.out.println("Введите число студентов");
            Scanner scanner = new Scanner(System.in);
            int number = scanner.nextInt();
            scanner.close();
            studentRepository.addRandomStudents(number);
        }));

        //Выбор способа вывода
        Menu outputMenu = new Menu("Выберите способ вывода\n" +
                "1. Вывод в консоль\n" +
                "2. Вывод в файл", "Некорректный ввод. Введите число от 1 до 2");
        outputMenu.addHandler(new Handler("1", studentRepository::printAllStudents));
        outputMenu.addHandler(new Handler("2", () -> {
            System.out.println("Введите название файла");
            Scanner scanner = new Scanner(System.in);
            String file = scanner.nextLine();
            scanner.close();
            studentRepository.saveToFile(file);
        }));

        //Выбор способа сортировки
        Menu sortMenu = new Menu("Выберите способ сортировки\n" +
                "1. Сортировка слиянием\n" +
                "2. Быстрая сортировка\n" +
                "3. Сортировка выбором",
                "Некорректный ввод. Введите число от 1 до 3"
        );
        sortMenu.addHandler(new Handler("1", () -> {
            strategy.changeStrategy(new MergeSortStrategy());
            App.sort();
        }));
        sortMenu.addHandler(new Handler("2", () -> {
            strategy.changeStrategy(new QuickSortStrategy());
            App.sort();
        }));
        sortMenu.addHandler(new Handler("3", () -> {
            strategy.changeStrategy(new SelectionSortStrategy());
            App.sort();
        }));

        mainMenu.addHandler(new Handler("1", inputMenu::run));
        mainMenu.addHandler(new Handler("2", outputMenu::run));
        mainMenu.addHandler(new Handler("3", sortMenu::run));


        while (isRunning[0]) mainMenu.run();
    }
    private static void sort(){
        Student[] students = studentRepository.getStudents().toArray(new Student[0]);
        strategy.execSort(students);
        studentRepository.clear();
        studentRepository.addStudents(students);
    }
}
