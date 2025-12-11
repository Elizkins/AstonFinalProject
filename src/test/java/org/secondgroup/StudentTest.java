package org.secondgroup;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.secondgroup.student.model.Student;

@DisplayName("Student validation tests")
public class StudentTest {

    @Test
    @DisplayName("GroupNumberEmpty validation test")
    public void validateGroupNumberForEmpty() {
        Student.Builder builder = new Student.Builder();
        String groupNumber = "";

        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class,
                () -> builder.groupNumber(groupNumber));
        IllegalArgumentException thrownWithNull = Assertions.assertThrows(IllegalArgumentException.class,
                () -> builder.groupNumber(null));

        Assertions.assertAll(
                "Grouped Assertions of GroupNumberEmpty validation test",
                () -> Assertions.assertEquals("Group number cannot be empty", thrown.getMessage()),
                () -> Assertions.assertEquals("Group number cannot be empty", thrownWithNull.getMessage())
        );
    }

    @Test
    @DisplayName("GroupNumberRegex validation test")
    public void validateGroupNumberForRegex() {
        Student.Builder builder = new Student.Builder();
        String groupNumber = "+";

        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class,
                () -> builder.groupNumber(groupNumber));

        Assertions.assertEquals("Invalid group number format", thrown.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"1234", "ASDF", "asdf", "12-23"})
    @DisplayName("GroupNumber validation test")
    public void validateGroupNumberForSuccess(String groupNumber) {
        Student.Builder builder = new Student.Builder();

        Assertions.assertDoesNotThrow(() -> builder.groupNumber(groupNumber));
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1.0, 66, 5.1})
    @DisplayName("AverageGradeRegex validation test")
    public void validateAverageGrade(double grade) {
        Student.Builder builder = new Student.Builder();

        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class,
                () -> builder.averageGrade(grade));

        Assertions.assertEquals("Average grade must be between 0 and 5.0", thrown.getMessage());
    }

    @ParameterizedTest
    @ValueSource(doubles = {2.1, 3, 4.5, 5, 0, 1.1})
    @DisplayName("AverageGrade validation test")
    public void validateAverageGradeForSuccess(double grade) {
        Student.Builder builder = new Student.Builder();

        Assertions.assertDoesNotThrow(() -> builder.averageGrade(grade));
    }

    @Test
    @DisplayName("RecordBookNumberEmpty validation test")
    public void validateRecordBookNumberForEmpty() {
        Student.Builder builder = new Student.Builder();
        String recordNumber = "";

        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class,
                () -> builder.recordBookNumber(recordNumber));
        IllegalArgumentException thrownWithNull = Assertions.assertThrows(IllegalArgumentException.class,
                () -> builder.recordBookNumber(null));

        Assertions.assertAll(
                "Grouped Assertions of RecordBookNumberEmpty validation test",
                () -> Assertions.assertEquals("Record book number cannot be empty", thrown.getMessage()),
                () -> Assertions.assertEquals("Record book number cannot be empty", thrownWithNull.getMessage())
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"1234we", "ASDF", "0--788", "123"})
    @DisplayName("RecordBookNumberRegex validation test")
    public void validateRecordBookNumberForRegex(String recordNumber) {
        Student.Builder builder = new Student.Builder();

        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class,
                () -> builder.recordBookNumber(recordNumber));

        Assertions.assertEquals("Record book number must be 6-10 digits", thrown.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"123456", "1234567890"})
    @DisplayName("RecordBookNumber validation test")
    public void validateRecordBookNumberForSuccess(String recordNumber) {
        Student.Builder builder = new Student.Builder();

        Assertions.assertDoesNotThrow(() -> builder.recordBookNumber(recordNumber));
    }

    @Test
    @DisplayName("Builder GroupNumber validation test")
    public void buildGroupNumber() {
        Student.Builder builder = new Student.Builder();
        double grade = 3.0;
        String recordNumber = "123456";

        IllegalStateException thrownInGroupNumber = Assertions.assertThrows(IllegalStateException.class,
                () -> builder.recordBookNumber(recordNumber).averageGrade(grade).build());

        Assertions.assertEquals("Требуется номер группы", thrownInGroupNumber.getMessage());
    }

    @Test
    @DisplayName("Builder RecordNumber validation test")
    public void buildRecordNumber() {
        Student.Builder builder = new Student.Builder();
        String groupNumber = "1234";
        double grade = 3.0;

        IllegalStateException thrownInRecordNumber = Assertions.assertThrows(IllegalStateException.class,
                () -> builder.groupNumber(groupNumber).averageGrade(grade).build());

        Assertions.assertEquals("Требуется номер книги учета", thrownInRecordNumber.getMessage());
    }
}
