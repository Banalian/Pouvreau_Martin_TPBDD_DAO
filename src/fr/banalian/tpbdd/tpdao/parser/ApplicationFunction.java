package fr.banalian.tpbdd.tpdao.parser;

import fr.banalian.tpbdd.tpdao.dao.DAO;
import fr.banalian.tpbdd.tpdao.model.*;

import java.util.ArrayList;
import java.util.Scanner;

public class ApplicationFunction {
    public static void create(Scanner scanner) {
        DAO<Application> applicationDAO = new DAO<>(Application.class);
        System.out.println("For which student do you want to create an application?");
        String studentNumber = StudentFunction.gather(scanner).getStudentNumber();
        System.out.println("Which courses will the student be taking?");
        Courses courses = CoursesFunction.gather(scanner);
        System.out.println("Where is the student going?");
        Scholarship scholarship = ScholarshipFunction.gather(scanner);
        applicationDAO.persist(new Application(studentNumber, scholarship, courses));
    }

    public static void update(int mode, Scanner scanner) {
        DAO<Application> applicationDAO = new DAO<>(Application.class);
        Application application = gather(scanner);

        switch (mode) {
            case 1 -> {
                System.out.println("Which courses will the student be taking?");
                Courses courses = CoursesFunction.gather(scanner);
                application.setUniversity(courses);
            }
            case 2 -> {
                System.out.println("Where is the student going?");
                Scholarship scholarship = ScholarshipFunction.gather(scanner);
                application.setGrant(scholarship);
            }
            case 3 -> {
                System.out.println("Which evaluation do you want to edit ? (1 or 2)");
                int evaluation = scanner.nextInt();
                scanner.nextLine();
                if (evaluation == 1 || evaluation == 2) {
                    System.out.println("What is the new evaluation grade?");
                    float grade = scanner.nextFloat();
                    scanner.nextLine();
                    System.out.println("What is the new evaluation teacher?");
                    Teacher teacher = TeacherFunction.gather(scanner);
                    switch (evaluation) {
                        case 1:
                            if (application.getEval1() != null) {
                                application.getEval1().setGrade(grade);
                                application.getEval1().setTeacher(teacher);
                            } else {
                                application.setEval1(new Evaluation(grade, teacher));
                            }
                            break;
                        case 2:
                            if (application.getEval2() != null) {
                                application.getEval2().setGrade(grade);
                                application.getEval2().setTeacher(teacher);
                            } else {
                                application.setEval2(new Evaluation(grade, teacher));
                            }
                            break;
                    }

                } else {
                    System.out.println("Invalid evaluation, try the process again");
                }
            }
            default -> System.out.println("Invalid mode, try the process again");
        }


        applicationDAO.update(application);
    }


    public static void seeAll() {
        DAO<Application> applicationDAO = new DAO<>(Application.class);
        ArrayList<Application> result = (ArrayList<Application>) applicationDAO.getAll();
        Print.printApplication(result);
    }

    public static void seeOne(Scanner scanner) {
        DAO<Application> applicationDAO = new DAO<>(Application.class);
        System.out.println("For which student do you want to see the application?");
        Student student = StudentFunction.gather(scanner);
        ArrayList<Object> list = new ArrayList<>();
        list.add(student.getStudentNumber());
        ArrayList<Application> result = (ArrayList<Application>) applicationDAO.getAllByColumns(new String[]{"studentId"}, list);

        Print.printApplication(result);
    }

    public static void delete(Scanner scanner) {
        DAO<Application> applicationDAO = new DAO<>(Application.class);
        applicationDAO.delete(gather(scanner));
    }

    protected static Application gather(Scanner scanner) {
        DAO<Application> applicationDAO = new DAO<>(Application.class);
        boolean correct = false;
        Application application = null;

        System.out.println("For which student do you want to edit an application?");
        String studentNumber = StudentFunction.gather(scanner).getStudentNumber();

        String[] columns = new String[]{"studentId"};
        ArrayList<Object> values = new ArrayList<>();
        values.add(studentNumber);
        ArrayList<Application> result = (ArrayList<Application>) applicationDAO.getAllByColumns(columns, values);

        if (result.size() > 1) {
            do {
                System.out.println("""
                        Several application correspond to your search.
                        Choose the application to be edited by typing its row number.
                        """);
                Print.printApplication(result);

                int id = scanner.nextInt();
                scanner.nextLine();

                    if ((id <= result.size())) {
                        correct = true;
                        application = result.get(id-1);
                    } else {
                        System.out.println("Your row number is incorrect. Please try again.");
                }

            } while (!correct);
        }

        return application;

    }
}
