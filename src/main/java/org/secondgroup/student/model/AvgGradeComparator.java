package org.secondgroup.student.model;

import java.util.Comparator;

public class AvgGradeComparator implements Comparator<Student> {
    @Override
    public int compare(Student o1, Student o2) {
        return Double.compare(o1.getAverageGrade(), o2.getAverageGrade());
    }
}
