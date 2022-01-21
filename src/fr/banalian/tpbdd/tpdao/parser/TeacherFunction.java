package fr.banalian.tpbdd.tpdao.parser;

import fr.banalian.tpbdd.tpdao.dao.DAO;
import fr.banalian.tpbdd.tpdao.model.Teacher;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class TeacherFunction {
    public static void create(String lastName, String firstName) {
        DAO<Teacher> teacherDAO = new DAO<>(Teacher.class);
        teacherDAO.persist(new Teacher(lastName, firstName));
    }

    public static void update(Scanner scanner) {
        DAO<Teacher> teacherDAO = new DAO<>(Teacher.class);
        Teacher teacher = gather(scanner);

        System.out.println("Type the new informations:");

        System.out.println("First Name: ");
        String newFirstName = scanner.nextLine();
        System.out.println("Last Name: ");
        String newLastname = scanner.nextLine();

        teacher.setFirstName(newFirstName);
        teacher.setLastName(newLastname);

        teacherDAO.update(teacher);
    }

    public static void seeAll() {
        DAO<Teacher> teacherDAO = new DAO<>(Teacher.class);
        ArrayList<Teacher> result = (ArrayList<Teacher>) teacherDAO.getAll();
        Print.printTeacher(result);
    }

    public static void seeOne(int mode, ArrayList<Object> info) {
        DAO<Teacher> teacherDAO = new DAO<>(Teacher.class);
        String[] columns;
        switch (mode) {
            case 1 -> columns = new String[]{"lastname"};
            case 2 -> columns = new String[]{"firstname"};
            case 3 -> columns = new String[]{"lastname", "firstname"};
            default -> throw new IllegalArgumentException();
        }
        ArrayList<Teacher> result = (ArrayList<Teacher>) teacherDAO.getAllByColumns(columns, info);
        Print.printTeacher(result);

    }

    public static void delete(Scanner scanner) {
        DAO<Teacher> teacherDAO = new DAO<>(Teacher.class);
        teacherDAO.delete(gather(scanner));
    }

    protected static Teacher gather(Scanner scanner) {
        DAO<Teacher> teacherDAO = new DAO<>(Teacher.class);
        boolean correct = false;
        Teacher teacher = null;

        System.out.println("Last Name: ");
        String lastName = scanner.nextLine().toLowerCase();
        System.out.println("First Name: ");
        String firstName = scanner.nextLine().toLowerCase();

        String[] columns = new String[]{"lastname", "firstname"};
        ArrayList<Object> values = new ArrayList<>();
        values.add(lastName);
        values.add(firstName);
        ArrayList<Teacher> result = (ArrayList<Teacher>) teacherDAO.getAllByColumns(columns, values);

        if (result.size() > 1) {
            do {
                System.out.println("""
                        Several teachers correspond to your search.
                        Choose the teacher to be edited by typing its ID.
                        """);
                Print.printTeacher(result);

                int id = scanner.nextInt();
                scanner.nextLine();

                for (Teacher value : result) {
                    if (((Teacher) value).getId() == id) {
                        correct = true;
                    }
                    teacher = ((Teacher) value);
                }

            } while (!correct);
        }

        return teacher;

    }

}