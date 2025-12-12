package org.secondgroup.sort.algorithms.sharedmethods;

import org.secondgroup.sort.comparators.AvgGradeComparator;
import org.secondgroup.sort.comparators.GroupNumberComparator;
import org.secondgroup.sort.comparators.RegBookComparator;
import org.secondgroup.student.model.Student;

import java.util.Comparator;

public abstract class StudentFieldDefinition {
    public static <T extends Student> int defineField(Comparator<T> compar) {
        if (compar instanceof GroupNumberComparator) {
            System.out.println("по полю \"Номер группы\"");
            return 1;
        } else if (compar instanceof AvgGradeComparator) {
            System.out.println("по полю \"Средний балл\"");
            return 2;
        } else if (compar instanceof RegBookComparator) {
            System.out.println("по полю \"Номер зачетной книжки\"");
            return 3;
        } else {
            throw new IllegalArgumentException("wrong field name or such doesn't exist");
        }
    }
}
