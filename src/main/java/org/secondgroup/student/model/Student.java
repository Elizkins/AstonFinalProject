package org.secondgroup.student.model;

import java.util.Objects;

public class Student implements Comparable<Student>{

    private final String groupNumber;
    private final double averageGrade;
    private final String recordBookNumber;
    
    private Student(Builder builder) {
        this.groupNumber = builder.groupNumber;
        this.averageGrade = builder.averageGrade;
        this.recordBookNumber = builder.recordBookNumber;
    }
    
    public String getGroupNumber() { return groupNumber; }
    public double getAverageGrade() { return averageGrade; }
    public String getRecordBookNumber() { return recordBookNumber; }

    public static class Builder {
        private String groupNumber;
        private double averageGrade;
        private String recordBookNumber;

        // Валидация номера группы
        private void validateGroupNumber(String groupNumber) {
            if (groupNumber == null || groupNumber.trim().isEmpty()) {
                throw new IllegalArgumentException("Group number cannot be empty");
            }
            if (!groupNumber.matches("[A-Za-z0-9-]+")) {
                throw new IllegalArgumentException("Invalid group number format");
            }
        }

        // Валидация среднего балла
        private void validateAverageGrade(double grade) {
            if (grade < 0 || grade > 5.0) {
                throw new IllegalArgumentException("Average grade must be between 0 and 5.0");
            }
        }

        // Валидация номера зачетки
        private void validateRecordBookNumber(String recordNumber) {
            if (recordNumber == null || recordNumber.trim().isEmpty()) {
                throw new IllegalArgumentException("Record book number cannot be empty");
            }
            if (!recordNumber.matches("\\d{6,10}")) {
                throw new IllegalArgumentException("Record book number must be 6-10 digits");
            }
        }

        public Builder groupNumber(String groupNumber) {
            validateGroupNumber(groupNumber);
            this.groupNumber = groupNumber;
            return this;
        }
        
        public Builder averageGrade(double averageGrade) {
            validateAverageGrade(averageGrade);
            this.averageGrade = averageGrade;
            return this;
        }
        
        public Builder recordBookNumber(String recordBookNumber) {
            validateRecordBookNumber(recordBookNumber);
            this.recordBookNumber = recordBookNumber;
            return this;
        }
        
        public Student build() {
            if (groupNumber == null) {
                throw new IllegalStateException("Требуется номер группы");
            }
            if (recordBookNumber == null) {
                throw new IllegalStateException("Требуется номер книги учета");
            }
            if (groupNumber.trim().isEmpty()) {
                throw new IllegalStateException("Номер группы не может быть пустым");
            }

            if (recordBookNumber.trim().isEmpty()) {
                throw new IllegalStateException("Номер зачетной книги не может быть пустым");
            }

            return new Student(this);
        }
    }
    
    @Override
    public String toString() {
        return String.format("Student[Group: %s, Grade: %.2f, Record: %s]",
            groupNumber, averageGrade, recordBookNumber);
    }

    @Override
    public int compareTo(Student otherStudent) {
        int groupNumberComparison = this.groupNumber.compareTo(otherStudent.groupNumber);
        if (groupNumberComparison != 0) {
            return groupNumberComparison;
        } else {
            int averageGradeComparison = Double.compare(this.averageGrade, otherStudent.averageGrade);
            if (averageGradeComparison != 0) {
                return averageGradeComparison;
            } else {
                return this.recordBookNumber.compareTo(otherStudent.recordBookNumber);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Double.compare(averageGrade, student.averageGrade) == 0 && Objects
                .equals(groupNumber, student.groupNumber) && Objects
                .equals(recordBookNumber, student.recordBookNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupNumber, averageGrade, recordBookNumber);
    }
}
