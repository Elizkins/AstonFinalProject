package org.secondgroup.customcollection.processing;

import org.secondgroup.customcollection.EnhancedModArray;
import org.secondgroup.customcollection.FixedListOfElements;
import org.secondgroup.sort.TestObjectForStrategiesUse;
import org.secondgroup.sort.comparators.AvgGradeComparator;
import org.secondgroup.sort.comparators.GroupNumberComparator;
import org.secondgroup.sort.comparators.RegBookComparator;
import org.secondgroup.sort.strategy.MergeSortStrategy;
import org.secondgroup.sort.strategy.MergeSortStrategyEven;
import org.secondgroup.sort.strategy.QuickSortStrategy;
import org.secondgroup.sort.strategy.QuickSortStrategyEven;
import org.secondgroup.sort.strategy.SelectionSortStrategy;
import org.secondgroup.sort.strategy.SelectionSortStrategyEvenAlt;
import org.secondgroup.sort.strategy.SelectionSortStrategyEven;
import org.secondgroup.sort.strategy.SortStrategy;
import org.secondgroup.student.model.Student;

import java.util.Comparator;

//TODO: Inject this functionality in app menu,
//      may be delete this class then in final project
public class TestStreamFileProcess {
    public static void main(String[] args) {
        // Create test file
//        String filePath = "students.txt";
        String filePath = "";   //if blank, then will be used relative path, defined in StudentFileToCollection

        // Fill test file
//        try {
//            Files.write(Paths.get(filePath), List.of("2;5.0;654486", "1;4.0;654485", "4;4.5;654483", "2;4.5;654482",
//                    "1;4.0;654481", "3;5.0;654480", "4;5.0;654487"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        // Fill custom collection using stream:
        // without specifying size(number of elements)
        EnhancedModArray<Student> dest = StudentsFileToCollection.processFile(filePath);
        // and with specifying number of elements
        FixedListOfElements<Student> dest2 = StudentsFileToCollection.processFile(filePath, 7);

        // Can sort it before (in custom collections) using one of three comparators for Student:
        Comparator<Student> regBookComp = new RegBookComparator(); // - on regBook field
        Comparator<Student> avgComp = new AvgGradeComparator(); // - on averageGrade field;
        Comparator<Student> groupNumComp = new GroupNumberComparator(); // - on groupNUmber field;

//        dest.sort(avgComp);
//        dest2.sort(regBookComp);

        // Or we can sort as in main app using strategies
        SortStrategy merge = new MergeSortStrategy();
        SortStrategy mergeEven = new MergeSortStrategyEven();
        SortStrategy quick = new QuickSortStrategy();
        SortStrategy quickEven = new QuickSortStrategyEven();
        SortStrategy select = new SelectionSortStrategy();
        SortStrategy selectEven = new SelectionSortStrategyEven();
        SortStrategy selectEvenAlt = new SelectionSortStrategyEvenAlt();

        TestObjectForStrategiesUse strategy = new TestObjectForStrategiesUse(selectEvenAlt);
        Student[] studs = dest.getStandardArray(Student.class);

        //See what we got
        for (Student st : dest) {
            System.out.println(st);
        }

        System.out.println(dest); // to see if there is free space in array

        System.out.println();

        /*for (Student st : dest2) {
            System.out.println(st);
        }

        System.out.println(dest2); // to see if there is free space in array

        System.out.println(dest.showValuesCount());*/

        System.out.println();

        // Sort by even values
        // Selection sort
//        strategy.execSortOnEven(studs, Student.class, groupNumComp);
//        TestObjectForStrategiesUse.printArray(studs);
//        System.out.println();
//        strategy.execSortOnEven(studs, Student.class, avgComp);
//        TestObjectForStrategiesUse.printArray(studs);

        // Quick sort
//        strategy.changeStrategy(quickEven);
//        strategy.execSortOnEven(studs, Student.class, groupNumComp);
//        TestObjectForStrategiesUse.printArray(studs);
//        strategy.execSortOnEven(studs, Student.class, avgComp);
//        TestObjectForStrategiesUse.printArray(studs);
//        strategy.execSortOnEven(studs, Student.class, regBookComp);
//        TestObjectForStrategiesUse.printArray(studs);

        // Merge sort
        strategy.changeStrategy(mergeEven);
//        strategy.execSortOnEven(studs, Student.class, groupNumComp);
//        TestObjectForStrategiesUse.printArray(studs);
//        strategy.execSortOnEven(studs, Student.class, avgComp);
//        TestObjectForStrategiesUse.printArray(studs);
        strategy.execSortOnEven(studs, Student.class, regBookComp);
        TestObjectForStrategiesUse.printArray(studs);

    }

}
