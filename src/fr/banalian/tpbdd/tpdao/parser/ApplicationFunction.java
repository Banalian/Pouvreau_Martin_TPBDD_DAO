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

    public static void update(Scanner scanner) {
        DAO<Application> applicationDAO = new DAO<>(Application.class);
        Application application = gather(scanner);

        // trucs à faire là

        applicationDAO.update(application);
    }

    public static void updateEvaluation(Scanner scanner){
        DAO<Application> applicationDAO = new DAO<>(Application.class);
        Application application = gather(scanner);

        //Trucs à faire là
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
        ArrayList<Object> list = new ArrayList<>(){{
        add(student.getStudentNumber();
        }};
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
