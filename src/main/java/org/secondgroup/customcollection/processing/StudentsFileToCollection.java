package org.secondgroup.customcollection.processing;

import org.secondgroup.customcollection.EnhancedModArray;
import org.secondgroup.customcollection.FixedListOfElements;
import org.secondgroup.customcollection.collectors.StudentCollector;
import org.secondgroup.student.model.Student;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * This class uses stream trying to fill custom collections with entity {@link Student}. It reads lines form file, try
 * to parse them and convert to entity {@link Student}. If it can not parse line correctly - such data can not be used
 * to create an entity {@link Student} and it will not be added in custom collection (an error message will be displayed
 * in console). After that it uses {@link StudentCollector} to get supplier, needed for creation and initializing a
 * custom collection, where elements from a stream will be stored (provides the initial state before accumulating
 * elements (accumulator) and combining the results (combiner)). This allows a lazy creation of a custom collection that
 * will only be filled when it is actually needed.
 */
public class StudentsFileToCollection {
    private static final String DEFAULT_FILE_PATH = "students.txt";

    public static EnhancedModArray<Student> processFile(String filePath) {
        String path = (filePath == null || filePath.isBlank()) ? DEFAULT_FILE_PATH : filePath;
        Path p = Paths.get(path);

        if (!Files.exists(p)) {
            System.out.println("Файл не найден: " + path);
            return null;
        }
        return tryGetStudents(p.toString());
    }

    public static FixedListOfElements<Student> processFile(String filePath, int capacity) {
        String path = (filePath == null || filePath.isBlank()) ? DEFAULT_FILE_PATH : filePath;
        Path p = Paths.get(path);

        if (!Files.exists(p)) {
            System.out.println("Файл не найден: " + path);
            return null;
        }
        return tryGetStudents(p.toString(), capacity);
    }

    public static EnhancedModArray<Student> tryGetStudents(String filePath) {
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            return lines
                    .map(StudentsFileToCollection::fromString)
                    .filter(Objects::nonNull)
                    .collect(StudentCollector.getStudentEnhancedModArrayCollector());
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }

    public static FixedListOfElements<Student> tryGetStudents(String filePath, int capacity) {
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            return lines
                    .map(StudentsFileToCollection::fromString)
                    .filter(Objects::nonNull)
                    .collect(StudentCollector.getStudentFixedListOfElementsCollector(capacity));
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }
        return null;
    }

    private static Student fromString(String line) {
        line = line.trim();
        if (line.isEmpty()) return null;
        String[] parts = line.split(";");
        if (parts.length != 3) {
            System.out.println("Студент пропущен - ошибка в данных файла: The number of parameters is not equal to 3");
            return null;
        }
        try {
            return new Student.Builder()
                    .groupNumber(parts[0].trim())
                    .averageGrade(Double.parseDouble(parts[1].trim()))
                    .recordBookNumber(parts[2].trim())
                    .build();
        } catch (IllegalStateException | IllegalArgumentException e) {
            System.out.println("Студент пропущен - ошибка в данных файла: " + e.getMessage());
            return null;
        }
    }

}
