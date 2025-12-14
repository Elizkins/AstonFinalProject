package org.secondgroup.sort.comparators;

import org.secondgroup.student.model.Student;

import java.util.Comparator;

public class RegBookComparator implements Comparator<Student> {

    @Override
    public int compare(Student o1, Student o2) {
        return Integer.valueOf(o1.getRecordBookNumber()).compareTo(Integer.valueOf(o2.getRecordBookNumber()));
    }
}
