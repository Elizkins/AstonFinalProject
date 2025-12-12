package org.secondgroup.repository;

import org.secondgroup.customcollection.FixedListOfElements;
import org.secondgroup.student.model.Student;


public class StudentRepository {

    private final FixedListOfElements<Student> students;

    public StudentRepository() {
        students = new FixedListOfElements<>(30);
    }

    public int count() {
        return students.showValuesCount();
    }

    public int countAll(Student student, int threadCount) {
        return students.countOccurrencesParallel(student, threadCount);
    }

    public void add(Student newStudent) {
        students.addInTail(newStudent);
    }

    public void add(FixedListOfElements<Student> newStudents) {
        students.addInTail(newStudents);
    }

    public void add(Student[] newStudents) {
        students.addInTail(newStudents);
    }

    public Student getByIndex(int index) {
        return students.getByPosition(index);
    }

    public Student[] getAll() {
        return students.getStandardArray(Student.class);
    }

    public void deleteAll() {
        students.clear();
    }

    public int getCapacity() {
        return students.showLength();
    }

    public void changeCapacity(int capacity) {
        students.changeCapacity(capacity);
    }

    public int getAvailableCount() {
        return students.showAvailableCount();
    }
}