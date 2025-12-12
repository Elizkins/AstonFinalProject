package org.secondgroup.repository;

import org.secondgroup.student.model.Student;

import java.util.Random;
import java.util.Scanner;

public abstract class StudentInputService {

    public static Student getStudentWithConsole(Scanner scanner){
        System.out.print("Номер группы (1-6 символов): ");
        String group = scanner.nextLine().trim();

        System.out.print("Средний балл (0.0 – 5.0): ");
        double grade = Double.parseDouble(scanner.nextLine().trim());

        System.out.print("Номер зачётной книжки (6-10 цифр): ");
        String recordBook = scanner.nextLine().trim();

        return new Student.Builder()
                .groupNumber(group)
                .averageGrade(grade)
                .recordBookNumber(recordBook)
                .build();
    }

    public static Student getStudentWithRandom(){
        Random rnd = new Random();

        String group = String.valueOf(rnd.nextInt(1, 999999));

        double grade = Math.round((2.0 + rnd.nextDouble() * 3.0) * 100.0) / 100.0;

        int digits = 6 + rnd.nextInt(5); // 6–10 цифр
        long minNum = (long) Math.pow(10, digits - 1);
        long maxNum = (long) Math.pow(10, digits);

        String recordBook = String.format("%0" + digits + "d", rnd.nextLong(minNum, maxNum));

        return new Student.Builder()
                .groupNumber(group)
                .averageGrade(grade)
                .recordBookNumber(recordBook)
                .build();
    }
}
