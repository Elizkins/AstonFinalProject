package org.secondgroup.sort;

import org.junit.jupiter.api.*;
import org.secondgroup.sort.algorithms.mergesort.MergeSortEven;
import org.secondgroup.sort.algorithms.quicksort.QuickSortEven;
import org.secondgroup.sort.algorithms.selectionsort.SelectionSortEven;
import org.secondgroup.sort.algorithms.selectionsort.SelectionSortEvenAlt;
import org.secondgroup.sort.comparators.AvgGradeComparator;
import org.secondgroup.sort.comparators.GroupNumberComparator;
import org.secondgroup.sort.comparators.RegBookComparator;
import org.secondgroup.student.model.Student;

@DisplayName("AdditionalSortAlgorithms tests")
public class AdditionalSortAlgorithmsTest {

    private static final int ARRAY_SIZE = 8;
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RESET = "\u001B[0m";

    private static Student[] expectedStudents = new Student[ARRAY_SIZE];
    private static Student[] students = new Student[ARRAY_SIZE];

    @BeforeEach
    public void setUpArray() {
        for (int i = 1; i <= ARRAY_SIZE; i += 2) {
            expectedStudents[i - 1] = new Student.Builder()
                    .groupNumber(String.valueOf(i + 1))
                    .averageGrade((i + 1) * 0.1)
                    .recordBookNumber(String.valueOf((i + 1) * 100001))
                    .build();
            expectedStudents[i] = new Student.Builder()
                    .groupNumber(String.valueOf(ARRAY_SIZE - i))
                    .averageGrade((ARRAY_SIZE - i) * 0.1)
                    .recordBookNumber(String.valueOf((ARRAY_SIZE - i) * 100001))
                    .build();
        }

        for (int i = ARRAY_SIZE; i > 0; i--) {
            students[ARRAY_SIZE - i] = new Student.Builder()
                    .groupNumber(String.valueOf(i))
                    .averageGrade(i * 0.1)
                    .recordBookNumber(String.valueOf(i * 100001))
                    .build();
        }
    }

    private static void printArrays() {
        for (int i = 0; i < students.length; i++) {
            String beginStr = "";
            if(students[i].equals(expectedStudents[i])){
                beginStr = ANSI_GREEN;
            } else{
                beginStr = ANSI_RED;
            }
            System.out.println(beginStr + students[i] + " | " + expectedStudents[i] + ANSI_RESET);
        }
    }


    @Test
    @DisplayName("SortByGroup SelectionSortEven test")
    public void sortByGroupSelectionSortEven() {
        SelectionSortEvenAlt.sort(students, Student.class, new GroupNumberComparator());

        printArrays();

        Assertions.assertArrayEquals(expectedStudents, students);
    }


    @Test
    @DisplayName("SortByGrade SelectionSortEven test")
    public void sortByGradeSelectionSortEven() {
        SelectionSortEvenAlt.sort(students, Student.class, new AvgGradeComparator());

        printArrays();

        Assertions.assertArrayEquals(expectedStudents, students);
    }

    @Test
    @DisplayName("SortByRecNum SelectionSortEven test")
    public void sortByRecNumSelectionSortEven() {
        SelectionSortEvenAlt.sort(students, Student.class, new RegBookComparator());

        printArrays();

        Assertions.assertArrayEquals(expectedStudents, students);
    }

    @Test
    @DisplayName("SortByGroup MergeSortEven test")
    public void sortByGroupMergeSortEven() {
        MergeSortEven.mergeSort(students, Student.class, new GroupNumberComparator());

        printArrays();

        Assertions.assertArrayEquals(expectedStudents, students);
    }


    @Test
    @DisplayName("SortByGrade MergeSortEven test")
    public void sortByGradeMergeSortEven() {
        MergeSortEven.mergeSort(students, Student.class, new AvgGradeComparator());

        printArrays();

        Assertions.assertArrayEquals(expectedStudents, students);
    }

    @Test
    @DisplayName("SortByRecNum MergeSortEven test")
    public void sortByRecNumMergeSortEven() {
        MergeSortEven.mergeSort(students, Student.class, new RegBookComparator());

        printArrays();

        Assertions.assertArrayEquals(expectedStudents, students);
    }

    @Test
    @DisplayName("SortByGroup QuickSortEven test")
    public void sortByGroupQuickSortEven() {
        QuickSortEven.quickSort(students, Student.class, new GroupNumberComparator());

        printArrays();

        Assertions.assertArrayEquals(expectedStudents, students);
    }


    @Test
    @DisplayName("SortByGrade QuickSortEven test")
    public void sortByGradeQuickSortEven() {
        QuickSortEven.quickSort(students, Student.class, new AvgGradeComparator());

        printArrays();

        Assertions.assertArrayEquals(expectedStudents, students);
    }

    @Test
    @DisplayName("SortByRecNum QuickSortEven test")
    public void sortByRecNumQuickSortEven() {
        QuickSortEven.quickSort(students, Student.class, new RegBookComparator());

        printArrays();

        Assertions.assertArrayEquals(expectedStudents, students);
    }
}
