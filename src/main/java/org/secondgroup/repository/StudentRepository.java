package org.secondgroup.repository;

import org.secondgroup.student.model.Student;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class StudentRepository {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String DEFAULT_FILE_PATH = "students.txt";
    private final List<Student> students = new ArrayList<>();

    // 1. Добавление вручную
    public void addStudentManually() {
        System.out.println("Введите число студентов: ");

        while (!scanner.hasNextInt()) {
            System.out.println("Ошибка: введите целое число!");
            scanner.next();
            System.out.println("Введите число студентов: ");
        }
        int count = scanner.nextInt();
        scanner.nextLine();

        try {
            for (int i = 1; i <= count; i++) {
                System.out.print("Добавление студента " + i + " вручную\n" + "Номер группы (1-6 символов): ");
                String group = scanner.nextLine().trim();

                System.out.print("Средний балл (0.0 – 5.0): ");
                double grade = Double.parseDouble(scanner.nextLine().trim());

                System.out.print("Номер зачётной книжки (6-10 цифр): ");
                String recordBook = scanner.nextLine().trim();

                Student student = new Student.Builder()
                        .groupNumber(group)
                        .averageGrade(grade)
                        .recordBookNumber(recordBook)
                        .build();

                students.add(student);
            }
            System.out.println("Успешно добавлено студентов: " + count);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: средний балл должен быть числом.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Ошибка валидации: " + e.getMessage());
        }
    }

    // 2. Добавление случайных студентов
    // ======================= Рандом (с правильной обработкой ошибок) =======================
    public void addRandomStudents() {
        System.out.println("Введите число студентов: ");
        int count = 0;

        while (!scanner.hasNextInt()) {
            System.out.println("Ошибка: введите целое число!");
            scanner.next();
            System.out.println("Введите число студентов: ");
        }

        count = scanner.nextInt();
        scanner.nextLine();

        if (count <= 0) {
            System.out.println("Количество должно быть больше 0.");
            return;
        }

        Random rnd = new Random();
        int added = 0;
        int failed = 0;

        System.out.println("Генерируем " + count + " случайных студентов...");

        while (added < count) { // максимум 100 попыток на ошибку
            try {
                String group = String.valueOf(rnd.nextInt(1, 999999));

                double grade = Math.round((2.0 + rnd.nextDouble() * 3.0) * 100.0) / 100.0;

                int digits = 6 + rnd.nextInt(5); // 6–10 цифр
                long minNum = (long) Math.pow(10, digits - 1);
                long maxNum = (long) Math.pow(10, digits);

                String recordBook = String.format("%0" + digits + "d", rnd.nextLong(minNum, maxNum));

                Student s = new Student.Builder()
                        .groupNumber(group)
                        .averageGrade(grade)
                        .recordBookNumber(recordBook)
                        .build();

                students.add(s);
                added++;

            } catch (IllegalArgumentException e) {
                // логирование проблемы
                failed++;
                System.out.println("Ошибка генерации #" + failed + ": " + e.getMessage());
                System.out.println("Попытка повторить с новыми данными...");

                // Можно добавить задержку, чтобы не спамить консоль
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ignored) {
                }
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

        System.out.println("Результат: добавлено " + added + ", ошибок: " + failed);

        if (added == 0) {
            System.out.println("Не удалось сгенерировать ни одного студента.");
        } else if (failed > 0) {
            System.out.println("Некоторые студенты не были добавлены из-за ошибок валидации.");
        }
    }

    // 3. Загрузка из файла
    public void loadFromFile() {
        System.out.println("Введите название файла");
        String filePath = scanner.nextLine();

        String path = (filePath == null || filePath.isBlank()) ? DEFAULT_FILE_PATH : filePath;
        Path p = Paths.get(path);

        if (!Files.exists(p)) {
            System.out.println("Файл не найден: " + path);
            return;
        }

        int loaded = 0;
        try (BufferedReader br = Files.newBufferedReader(p)) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(";");
                if (parts.length != 3) {
                    System.out.println("Студент пропущен - ошибка в данных файла: The number of parameters is not equal to 3");
                    continue;
                }

                try {
                    Student s = new Student.Builder()
                            .groupNumber(parts[0].trim())
                            .averageGrade(Double.parseDouble(parts[1].trim()))
                            .recordBookNumber(parts[2].trim())
                            .build();
                    students.add(s);
                    loaded++;
                } catch (IllegalStateException | IllegalArgumentException e) {
                    System.out.println("Студент пропущен - ошибка в данных файла: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
            return;
        } catch (Exception e) {
            System.out.println("Ошибка в данных файла: " + e.getMessage());
        }
        System.out.println("Успешно загружено студентов: " + loaded);
    }

    // 4. Сохранение в файл (режим append)
    // (доп. задание 2)
    public void saveToFile() {
        System.out.println("Введите название файла");
        String filePath = scanner.nextLine();

        if (students.isEmpty()) {
            System.out.println("Список пуст – сохранять нечего.");
            return;
        }

        String path = (filePath == null || filePath.isBlank()) ? DEFAULT_FILE_PATH : filePath;

        try (BufferedWriter bw = Files.newBufferedWriter(
                Paths.get(path),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND)) {

            bw.newLine();
            for (Student s : students) {
                bw.write(String.format(Locale.US, "%s;%.2f;%s\n",
                        s.getGroupNumber(),
                        s.getAverageGrade(),
                        s.getRecordBookNumber()));
            }
            System.out.println("Успешно добавлено " + students.size() + " записей в файл: " + path);
        } catch (IOException e) {
            System.out.println("Ошибка записи в файл: " + e.getMessage());
        }
    }

    // 5. Вывод списка
    public void printAllStudents() {
        if (students.isEmpty()) {
            System.out.println("Список студентов пуст.");
            return;
        }
        System.out.println("=== Список студентов (" + students.size() + ") ===");
        for (int i = 0; i < students.size(); i++) {
            System.out.println((i + 1) + ". " + students.get(i));
        }
    }

    // Геттер для остальных модулей (сортировка и т.д.)
    public List<Student> getStudents() {
        return Collections.unmodifiableList(students);
    }

    public void clear() {
        students.clear();
    }

    public void addStudents(Student[] students) {
        this.students.addAll(Arrays.asList(students));
    }
}