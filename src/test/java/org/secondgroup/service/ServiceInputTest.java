package org.secondgroup.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.secondgroup.repository.StudentInputService;
import org.secondgroup.student.model.Student;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

@DisplayName("ServiceInput tests")
public class ServiceInputTest {

    public static final int TEST_GROUP_NUMBER = 123;
    public static final String FAILTEST_GROUP_NUMBER = "";
    public static final int TEST_REC_NUMBER = 123456;
    public static final double TEST_GRADE = 5.0;

    void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    @Test
    @DisplayName("GetStudentWithConsole test")
    public void getStudentWithConsoleTest() {
        provideInput(TEST_GROUP_NUMBER + "\n" + TEST_GRADE + "\n" + TEST_REC_NUMBER);

        Scanner scanner = new Scanner(System.in);

        Student student = new Student.Builder()
                .groupNumber(String.valueOf(TEST_GROUP_NUMBER))
                .averageGrade(TEST_GRADE)
                .recordBookNumber(String.valueOf(TEST_REC_NUMBER))
                .build();

        Assertions.assertEquals(student, StudentInputService.getStudentWithConsole(scanner));
    }

    @Test
    @DisplayName("GetStudentWithConsole test")
    public void getStudentWithConsoleFailTest() {
        provideInput(FAILTEST_GROUP_NUMBER + "\n" + TEST_GRADE + "\n" + TEST_REC_NUMBER);

        Scanner scanner = new Scanner(System.in);

        Assertions.assertThrows(IllegalArgumentException.class, () -> StudentInputService.getStudentWithConsole(scanner));
    }
}
