package org.secondgroup.student.model;

import java.util.Comparator;

public class GroupNumberComparator implements Comparator<Student> {
    @Override
    public int compare(Student o1, Student o2) {
        return o1.getGroupNumber().compareTo(o2.getGroupNumber());
    }
}
