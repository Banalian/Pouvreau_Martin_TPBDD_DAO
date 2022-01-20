package fr.banalian.tpbdd.tpdao.parser;

import fr.banalian.tpbdd.tpdao.dao.DAO;
import fr.banalian.tpbdd.tpdao.model.Courses;

import java.util.ArrayList;
import java.util.Scanner;

public class CoursesFunction {
    public static void create(String name, String description, int ects, float hours, String university) {
        DAO<Courses> coursesDAO = new DAO<>(Courses.class);
        coursesDAO.persist(new Courses(name,ects,hours,university, description));
    }

    public static void seeAll() {
        DAO<Courses> coursesDAO = new DAO<>(Courses.class);
        ArrayList<Courses> result = (ArrayList<Courses>) coursesDAO.getAll();
        Print.printCourses(result);
    }

    public static void seeOne(int mode, String[] info) {
        DAO<Courses> coursesDAO = new DAO<>(Courses.class);
        String[] columns;
        switch (mode) {
            case 1 -> columns = new String[]{"ects"};
            case 2 -> columns = new String[]{"university"};
            case 3 -> columns = new String[]{"hours"};
            case 4 -> columns = new String[]{"name"};
            default -> throw new IllegalArgumentException();
        }
        ArrayList<Courses> result = (ArrayList<Courses>) coursesDAO.getAllByColumns(columns, info);
        Print.printCourses(result);

    }

    public static void delete(Scanner scanner) {
        DAO<Courses> coursesDAO = new DAO<>(Courses.class);
        coursesDAO.delete(search(scanner));
    }


    private static Courses search(Scanner scanner){
        ArrayList<Courses> courses = new ArrayList<>();
        courses.add(gather(scanner));

        return courses.get(0);
    }

    private static Courses gather(Scanner scanner) {
        DAO<Courses> coursesDAO = new DAO<>(Courses.class);
        boolean correct = false;
        Courses courses = null;

        System.out.println("Name: ");
        String name = scanner.nextLine().toLowerCase();
        System.out.println("University");
        String univ = scanner.nextLine().toLowerCase();

        String[] columns = new String[]{"syllabus_name", "university"};
        String[] values = new String[]{name, univ};
        ArrayList<Courses> result = (ArrayList<Courses>) coursesDAO.getAllByColumns(columns, values);

        if (result.size() > 1) {
            do {
                System.out.println("""
                        Several courses correspond to your search.
                        Choose the course to be edited by typing its ID.
                        """);
                Print.printCourses(result);

                int id = scanner.nextInt();

                for (Courses value : result) {
                    if (((Courses) value).getId() == id) {
                        correct = true;
                    }
                    courses = ((Courses) value);
                }

            } while (!correct);
        }

        return courses;

    }
}
