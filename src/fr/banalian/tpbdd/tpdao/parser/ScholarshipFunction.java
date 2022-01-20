package fr.banalian.tpbdd.tpdao.parser;

import fr.banalian.tpbdd.tpdao.dao.DAO;
import fr.banalian.tpbdd.tpdao.model.Scholarship;
import fr.banalian.tpbdd.tpdao.model.Teacher;

import java.util.ArrayList;
import java.util.Scanner;

public class ScholarshipFunction {
    public static void create(String destination, int seats, Scanner scan) {
        DAO<Scholarship> coursesDAO = new DAO<>(Scholarship.class);
        DAO<Teacher> teacherDAO = new DAO<>(Teacher.class);
        System.out.println("Who's the teacher in charge of the scholarship?");
        Teacher teacher1 = TeacherFunction.gather(scan);
        coursesDAO.persist(new Scholarship(destination, seats,teacher1));
    }

    public static void seeAll() {
        DAO<Scholarship> scholarshipDAO = new DAO<>(Scholarship.class);
        ArrayList<Scholarship> result = (ArrayList<Scholarship>) scholarshipDAO.getAll();
        Print.printGrant(result);
    }

    public static void seeOne(int mode, ArrayList<Object> info, Scanner scanner) {
        DAO<Scholarship> scholarshipDAO = new DAO<>(Scholarship.class);

        String[] columns;
        switch (mode) {
            case 1 -> columns = new String[]{"destination"};
            case 2 -> {
                System.out.println("Who's the teacher you're looking for?");
                info.add(TeacherFunction.gather(scanner).getId());
                columns = new String[]{"teacher"};
            }
            default -> throw new IllegalArgumentException();
        }
        ArrayList<Scholarship> result = (ArrayList<Scholarship>) scholarshipDAO.getAllByColumns(columns, info);
        Print.printGrant(result);

    }

    public static void delete(Scanner scanner) {
        DAO<Scholarship> scholarshipDAO = new DAO<>(Scholarship.class);
        scholarshipDAO.delete(gather(scanner));
    }

    protected static Scholarship gather(Scanner scanner) {
        DAO<Scholarship> scholarshipDAO = new DAO<>(Scholarship.class);
        boolean correct = false;
        Scholarship scholarship = null;

        System.out.println("Destination: ");
        String destination = scanner.nextLine().toLowerCase();

        String[] columns = new String[]{"destination"};
        ArrayList<Object> values = new ArrayList<>();
        values.add(destination);
        ArrayList<Scholarship> result = (ArrayList<Scholarship>) scholarshipDAO.getAllByColumns(columns, values);

        if (result.size() > 1) {
            do {
                System.out.println("""
                        Several scholarship correspond to your search.
                        Choose the scholarship to be edited by typing its ID.
                        """);
                Print.printGrant(result);

                int id = scanner.nextInt();

                for (Scholarship value : result) {
                    if (((Scholarship) value).getId() == id) {
                        correct = true;
                    }
                    scholarship = ((Scholarship) value);
                }

            } while (!correct);
        }

        return scholarship;

    }

}
