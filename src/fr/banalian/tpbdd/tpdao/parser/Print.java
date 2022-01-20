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
                    course.getSyllabusName(),
                    course.getEcts(),
                    course.getHours());
        }
    }

    public static void printApplication(ArrayList<Application> applications) {
        System.out.printf("| %-20s | %-20s | %-20s | %-20s | %-20s | %-20s | %-20s |\n",
                "Row number",
                "Student Id",
                "Scholarship destination",
                "University",
                "Eval1 grade",
                "Eval2 grade",
                "Final grade");
        int i = 1;
        for (Application application : applications) {
            System.out.printf("| %-20s | %-20s | %-20s | %-20s | %-20s | %-20s | %-20s |\n",
                    i,
                    application.getStudentId(),
                    application.getGrant().getDestination(),
                    application.getUniversity(),
                    application.getEval1().getGrade(),
                    application.getEval2().getGrade(),
                    application.getFinalGrade());
            i++;
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

    public static void printGrant(ArrayList<Scholarship> scholarships) {
        System.out.printf("| %-20s | %-20s | %-20s | %-20s |\n",
                "Grant Id",
                "Destination",
                "Total Seats",
                "Teacher Id");

        for (Scholarship scholarship : scholarships) {
            System.out.printf("| %-20s | %-20s | %-20s | %-20s |\n",
                    scholarship.getId(),
                    scholarship.getDestination(),
                    scholarship.getTotalSeats(),
                    scholarship.getTeacher().getId());
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
                "ID",
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
