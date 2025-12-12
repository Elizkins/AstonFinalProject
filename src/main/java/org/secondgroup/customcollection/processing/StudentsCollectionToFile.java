package org.secondgroup.customcollection.processing;

import org.secondgroup.customcollection.FixedListOfElements;
import org.secondgroup.student.model.Student;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Locale;

public abstract class StudentsCollectionToFile {
    private static final String DEFAULT_FILE_PATH = "students.txt";

    public static int processFile(String filePath, Student[] students) throws IOException {
        String path = (filePath == null || filePath.isBlank()) ? DEFAULT_FILE_PATH : filePath;
        int writtenCount = 0;

        if (students.length == 0) {
            return writtenCount;
        }

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
                writtenCount++;
            }
            return writtenCount;
        } catch (IOException e) {
            throw new IOException("Прерванная запись в файл " + e.getMessage());
        }
    }
}
