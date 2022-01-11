package fr.banalian.tpbdd.tpdao.parser;
import fr.banalian.tpbdd.tpdao.model.*;

import java.util.ArrayList;

//TODO : REFAIRE LES PRINTS (peut etre avec des ToString?)
public class Print {

    public static void printCourses(ArrayList<Courses> courses) {
        System.out.printf("| %-20s | %-20s | %-20s | %-20s | %-20s |\n",
                "Id",
                "University",
                "Name",
                "ECTS",
                "Hours");
        for (Courses course : courses) {
            System.out.printf("| %-20s | %-20s | %-20s | %-20s | %-20s |\n",
                    course.getId(),
                    course.getUniversity(),
                    course.getName(),
                    course.getEcts(),
                    course.getHours());
        }
    }

    public static void printApplication(ArrayList<Application> applications) {
        System.out.printf("| %-20s | %-20s | %-20s | %-20s | %-20s | %-20s |\n",
                "Student Id",
                "Grant Id",
                "University",
                "Eval1 Id",
                "Eval2 Id",
                "Final Grade");

        for (Application application : applications) {
            System.out.printf("| %-20s | %-20s | %-20s | %-20s | %-20s | %-20s |\n",
                    application.getStudentId(),
                    application.getGrant(),
                    application.getUniversity(),
                    application.getEval1(),
                    application.getEval2(),
                    application.getFinalGrade());
        }
    }

    public static void printEvaluation(ArrayList<Evaluation> evaluations) {
        System.out.printf("| %-20s | %-20s | %-20s |\n",
                "Evaluation Id",
                "Grade",
                "Teacher Id");

        for (Evaluation evaluation : evaluations) {
            System.out.printf("| %-20s | %-20s | %-20s |\n",
                    evaluation.getId(),
                    evaluation.getGrade(),
                    evaluation.getTeacher().getId());
        }
    }

    public static void printGrant(ArrayList<Grant> grants) {
        System.out.printf("| %-20s | %-20s | %-20s | %-20s |\n",
                "Grant Id",
                "Destination",
                "Total Seats",
                "Teacher Id");

        for (Grant grant : grants) {
            System.out.printf("| %-20s | %-20s | %-20s | %-20s |\n",
                    grant.getId(),
                    grant.getDestination(),
                    grant.getTotalSeats(),
                    grant.getTeacher().getId());
        }
    }

    public static void printStudent(ArrayList<Student> students) {
        System.out.printf("| %-20s | %-20s | %-20s | %-20s |\n",
                "Student Number",
                "Last Name",
                "First Name",
                "Average Grade");

        for (Student student : students) {
            System.out.printf("| %-20s | %-20s | %-20s | %-20s |\n",
                    student.getStudentNumber(),
                    student.getLastName(),
                    student.getFirstName(),
                    student.getAverageGrade());
        }
    }

    public static void printTeacher(ArrayList<Teacher> teachers) {
        System.out.printf("| %-20s | %-20s | %-20s |\n",
                "Student Number",
                "Last Name",
                "First Name");

        for (Teacher teacher : teachers) {
            System.out.printf("| %-20s | %-20s | %-20s |\n",
                    teacher.getId(),
                    teacher.getLastName(),
                    teacher.getFirstName());
        }
    }

}
