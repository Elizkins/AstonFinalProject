package org.secondgroup.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.secondgroup.customcollection.FixedListOfElements;
import org.secondgroup.customcollection.processing.StudentsFileToCollection;
import org.secondgroup.repository.StudentInputService;
import org.secondgroup.repository.StudentRepository;
import org.secondgroup.repository.StudentService;
import org.secondgroup.student.model.Student;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Scanner;

import static org.mockito.Mockito.mockStatic;

@DisplayName("ServiceTest tests")
@ExtendWith(MockitoExtension.class)
public class ServiceTest {

    public static final String TEST_INPUT = "q\n-1\n1\n";
    public static final int TEST_GROUP_NUMBER = 123;
    public static final int TEST_REC_NUMBER = 123456;
    public static final double TEST_GRADE = 5.0;

    void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    @Test
    @DisplayName("ReadIntTest test")
    public void readIntTest() throws Exception {
        provideInput(TEST_INPUT);

        StudentService studentService = new StudentService();

        Method method = StudentService.class.getDeclaredMethod("readInt",
                int.class, int.class, String.class);
        method.setAccessible(true);

        int result = (int) method.invoke(studentService, 0, 30, "Some message");

        Assertions.assertEquals(1, result);
    }

    @Test
    @DisplayName("ChangeCapacityCommand test")
    public void changeCapacityCommand() throws Exception {
        provideInput(TEST_INPUT);

        StudentService studentService = new StudentService();

        studentService.changeCapacityCommand();

        Field repo = StudentService.class.getDeclaredField("studentRepository");
        repo.setAccessible(true);
        StudentRepository studentRepository = (StudentRepository) repo.get(studentService);

        Assertions.assertEquals(1, studentRepository.getCapacity());
    }

    @Test
    @DisplayName("AddStudentManuallyCommand test")
    public void addStudentManuallyCommandTest() throws Exception {
        provideInput(TEST_INPUT + TEST_GROUP_NUMBER + "\n" + TEST_GRADE + "\n" + TEST_REC_NUMBER);

        StudentService studentService = new StudentService();
        studentService.addStudentManuallyCommand();

        Field repo = StudentService.class.getDeclaredField("studentRepository");
        repo.setAccessible(true);
        StudentRepository studentRepository = (StudentRepository) repo.get(studentService);

        Student student = new Student.Builder()
                .groupNumber(String.valueOf(TEST_GROUP_NUMBER))
                .averageGrade(TEST_GRADE)
                .recordBookNumber(String.valueOf(TEST_REC_NUMBER))
                .build();

        Assertions.assertEquals(student, studentRepository.getByIndex(0));
    }

    @Test
    @DisplayName("AddRandomStudentsCommand test")
    public void addRandomStudentsCommandTest() throws Exception {
        provideInput(TEST_INPUT);
        Student student = new Student.Builder()
                .groupNumber(String.valueOf(TEST_GROUP_NUMBER))
                .averageGrade(TEST_GRADE)
                .recordBookNumber(String.valueOf(TEST_REC_NUMBER))
                .build();

        try (MockedStatic<StudentInputService> mockedStatic = mockStatic(StudentInputService.class)) {
            mockedStatic.when(StudentInputService::getStudentWithRandom).thenReturn(student);

            StudentService studentService = new StudentService();
            studentService.addRandomStudentsCommand();

            Field repo = StudentService.class.getDeclaredField("studentRepository");
            repo.setAccessible(true);
            StudentRepository studentRepository = (StudentRepository) repo.get(studentService);

            Assertions.assertEquals(student, studentRepository.getByIndex(0));
        }
    }

    @Test
    @DisplayName("LoadFromFileCommand test")
    public void loadFromFileCommandTest() throws Exception {
        provideInput(TEST_INPUT);
        Student student = new Student.Builder()
                .groupNumber(String.valueOf(TEST_GROUP_NUMBER))
                .averageGrade(TEST_GRADE)
                .recordBookNumber(String.valueOf(TEST_REC_NUMBER))
                .build();

        FixedListOfElements<Student> newStudents = new FixedListOfElements<>();
        newStudents.addInTail(student);

        try (MockedStatic<StudentsFileToCollection> mockedStatic = mockStatic(StudentsFileToCollection.class)) {
            mockedStatic.when(() -> StudentsFileToCollection.processFile(Mockito.anyString(), Mockito.anyInt()))
                    .thenReturn(newStudents);

            StudentService studentService = new StudentService();
            studentService.loadFromFileCommand();

            Field repo = StudentService.class.getDeclaredField("studentRepository");
            repo.setAccessible(true);
            StudentRepository studentRepository = (StudentRepository) repo.get(studentService);

            Assertions.assertEquals(student, studentRepository.getByIndex(0));
        }
    }
}
