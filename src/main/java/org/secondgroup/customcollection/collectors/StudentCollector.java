package org.secondgroup.customcollection.collectors;

import org.secondgroup.customcollection.EnhancedModArray;
import org.secondgroup.customcollection.FixedListOfElements;
import org.secondgroup.student.model.Student;

import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collector;
/** This class provides static methods to get supplier, needed in
 * {@link java.util.stream.Stream#collect(Supplier, BiConsumer, BiConsumer)} when filling custom collections. It helps
 * to create new collection, also with specified size. Anyway, such collection will accept only the number of elements
 * you specified when creating fixed collection. Other incoming elements will simply be ignored. In case of unknown or
 * unpredictable number of incoming elements - better use an {@link EnhancedModArray} without specifying size. */
public class StudentCollector {

    public static Collector<Student, ?, FixedListOfElements<Student>> getStudentFixedListOfElementsCollector(int len) {
        return Collector.of(
                () -> new FixedListOfElements<>(len),
                FixedListOfElements::addInTail,
                (list1, list2) -> {
                    list1.addInTail(list2);
                    return list1;
                });
    }

    public static Collector<Student, ?, EnhancedModArray<Student>> getStudentEnhancedModArrayCollector() {
        return Collector.of(
                EnhancedModArray::new,
                EnhancedModArray::addInTail,
                (list1, list2) -> {
                    list1.addInTail(list2);
                    return list1;
                });
    }

}
