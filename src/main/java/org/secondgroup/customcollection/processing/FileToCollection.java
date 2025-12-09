package org.secondgroup.customcollection.processing;

import org.secondgroup.customcollection.EnhancedModArray;
import org.secondgroup.customcollection.FixedListOfElements;
import org.secondgroup.customcollection.collectors.StudentCollector;
import org.secondgroup.student.model.Student;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileToCollection {

    public static EnhancedModArray<Student> processFile(String filepath) {
        return tryGetStudents(filepath);
    }

    public static FixedListOfElements<Student> processFile(String filepath, int capacity) {
        return tryGetStudents(filepath, capacity);
    }

    public static EnhancedModArray<Student> tryGetStudents(String filePath) {
        try {
            Stream<String> lines = Files.lines(Paths.get(filePath));
            EnhancedModArray<Student> dest = lines
                    .map(FileToCollection::fromString)
                    .collect(StudentCollector.getStudentEnhancedModArrayCollector());
            return dest;
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }

    public static FixedListOfElements<Student> tryGetStudents(String filePath, int capacity) {
        try {
            Stream<String> lines = Files.lines(Paths.get(filePath));
            FixedListOfElements<Student> dest = lines
                    .map(FileToCollection::fromString)
                    .collect(StudentCollector.getStudentFixedListOfElementsCollector(capacity));
            return dest;
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }

    private static Student fromString(String line) {
        line = line.trim();
        String[] parts = line.split(";");

        Student s = new Student.Builder()
                .groupNumber(parts[0].trim())
                .averageGrade(Double.parseDouble(parts[1].trim()))
                .recordBookNumber(parts[2].trim())
                .build();
        return s;
    }

}
