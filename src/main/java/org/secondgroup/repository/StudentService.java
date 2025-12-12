package org.secondgroup.repository;

import org.secondgroup.customcollection.FixedListOfElements;
import org.secondgroup.customcollection.processing.StudentsCollectionToFile;
import org.secondgroup.customcollection.processing.StudentsFileToCollection;
import org.secondgroup.sort.TestObjectForStrategiesUse;
import org.secondgroup.student.model.Student;

import java.io.IOException;
import java.util.Scanner;

public class StudentService {
    private static final StudentRepository studentRepository = new StudentRepository();
    private static final Scanner scanner = new Scanner(System.in);

    private int readInt(int minValue, int maxValue, String innerMessage) {
        while (true) {
            System.out.println(innerMessage);

            if (!scanner.hasNextInt()) {
                System.out.println("Ошибка: введите целое число (" + minValue + " - " + maxValue + ")");
                scanner.next();
                continue;
            }

            int value = scanner.nextInt();
            scanner.nextLine();

            if (value >= minValue && value <= maxValue) {
                return value;
            }

            System.out.println("Ошибка: введите целое число (" + minValue + " - " + maxValue + ")");
        }
    }

    public void changeCapacityCommand() {
        int studentsCount = studentRepository.count();
        System.out.println("Текущая вместимость: " + studentRepository.getCapacity());

        int capacity = readInt(studentsCount, FixedListOfElements.MAX_CAPACITY,
                "Введите новую вместимость: ");

        studentRepository.changeCapacity(capacity);

        System.out.println("Текущая вместимость: " + studentRepository.getCapacity());
    }

    public void addStudentManuallyCommand() {
        int count = readInt(0, studentRepository.getAvailableCount(),
                "Введите число студентов: ");

        try {
            for (int i = 1; i <= count; i++) {
                System.out.print("Добавление студента " + i + " вручную\n");

                Student student = StudentInputService.getStudentWithConsole(scanner);

                studentRepository.add(student);
            }
            System.out.println("Успешно добавлено студентов: " + count);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: средний балл должен быть числом.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Ошибка валидации: " + e.getMessage());
        }
    }

    public void addRandomStudentsCommand() {
        int count = readInt(0, studentRepository.getAvailableCount(),
                "Введите число студентов: ");

        int added = 0;
        int failed = 0;

        System.out.println("Генерируем " + count + " случайных студентов...");

        while (added < count) { // максимум 100 попыток на ошибку
            try {
                Student student = StudentInputService.getStudentWithRandom();

                studentRepository.add(student);
                added++;

            } catch (IllegalArgumentException e) {
                // логирование проблемы
                failed++;
                System.out.println("Ошибка генерации #" + failed + ": " + e.getMessage());
                System.out.println("Попытка повторить с новыми данными...");
            } catch (Exception e) {
                // Любая другая неожиданная ошибка
                failed++;
                System.out.println("Неожиданная ошибка при генерации: " + e.getMessage());
            }

            // Если слишком много ошибок — прерываем
            if (failed >= 5) {
                System.out.println("Слишком много ошибок при генерации. Остановлено.");
                break;
            }
        }
        if (added == 0) {
            System.out.println("Не удалось сгенерировать ни одного студента.");
        } else if (failed > 0) {
            System.out.println("Некоторые студенты не были добавлены из-за ошибок валидации.");
        }
        System.out.println("Результат: добавлено " + added + ", ошибок: " + failed);
    }

    public void loadFromFileCommand() {
        System.out.println("Введите название файла");
        String filePath = scanner.nextLine();

        FixedListOfElements<Student> newStudents = new FixedListOfElements<>();
        try {
            newStudents = StudentsFileToCollection.processFile(filePath, studentRepository.getAvailableCount());

            studentRepository.add(newStudents);

        } catch (IOException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
        System.out.println("Успешно загружено студентов: " + newStudents.showValuesCount());
    }

    public void saveToFileCommand() {
        System.out.println("Введите название файла");
        String filePath = scanner.nextLine();

        int requestResult = 0;
        try {
            requestResult = StudentsCollectionToFile.processFile(filePath, studentRepository.getAll());
        } catch (IOException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        if (requestResult == 0) {
            System.out.println("Список пуст – сохранять нечего.");
            return;
        }

        System.out.println("Успешно добавлено " + requestResult + " записей в файл: " + filePath);
    }

    public void printToConsoleCommand() {
        int studentsCount = studentRepository.count();

        if (studentsCount == 0) {
            System.out.println("Список студентов пуст.");
            return;
        }

        System.out.println("=== Список студентов (" + studentsCount + ") ===");

        for (int i = 0; i < studentsCount; i++) {
            System.out.println((i + 1) + ". " + studentRepository.getByIndex(i));
        }
    }

    public void sortStudentsCommand(TestObjectForStrategiesUse strategy) {
        Student[] students = studentRepository.getAll();
        strategy.execSort(students);
        studentRepository.deleteAll();
        studentRepository.add(students);
        System.out.println("Сортировка выполнена.");
    }

    public void clearCommand() {
        studentRepository.deleteAll();
        System.out.println("Список очищен.");
    }

    public void countOcurrencyCommand() {
        int count = readInt(0, Runtime.getRuntime().availableProcessors(),
                "Введите число потоков: ");

        try {
            Student student = StudentInputService.getStudentWithConsole(scanner);

            int occurrencesParallelCount = studentRepository.countAll(student, count);
            System.out.println("Количество вхождений " + student + " равно: " + occurrencesParallelCount);

        } catch (NumberFormatException e) {
            System.out.println("Ошибка: средний балл должен быть числом.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Ошибка валидации: " + e.getMessage());
        }

    }
}