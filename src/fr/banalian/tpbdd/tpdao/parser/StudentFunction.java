package fr.banalian.tpbdd.tpdao.parser;

import fr.banalian.tpbdd.tpdao.dao.DAO;
import fr.banalian.tpbdd.tpdao.model.Student;
import fr.banalian.tpbdd.tpdao.model.Teacher;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class StudentFunction {
    public static void create(String studentNumber, String lastName, String firstName, float averageGrade) {
        DAO<Student> studentDAO = new DAO<>(Student.class);
        studentDAO.persist(new Student(lastName, firstName, studentNumber, averageGrade));
    }

    public static void update(Scanner scanner) {
        DAO<Student> studentDAO = new DAO<>(Student.class);
        boolean correct = false;

        Student student = search(scanner);

        System.out.println("Type the new informations:");

        System.out.println("First Name: ");
        String newFirstName = scanner.nextLine();
        System.out.println("Last Name: ");
        String newLastname = scanner.nextLine();
        System.out.println("Student number: ");
        String newId = scanner.nextLine();

        student.setFirstName(newFirstName);
        student.setLastName(newLastname);
        student.setStudentNumber(newId);

        studentDAO.update(student);
    }

    public static void seeAll() {
        DAO<Student> studentDAO = new DAO<>(Student.class);
        ArrayList<Student> result = (ArrayList<Student>) studentDAO.getAll();
        Print.printStudent(result);
    }

    public static void seeOne(int mode, ArrayList<Object> info) {
        DAO<Student> studentDAO = new DAO<>(Student.class);
        String[] columns;
        switch (mode) {
            case 1 -> columns = new String[]{"lastname"};
            case 2 -> columns = new String[]{"firstname"};
            case 3 -> columns = new String[]{"lastname", "firstname"};
            case 4 -> columns = new String[]{"studentnumber"};
            default -> throw new IllegalArgumentException();
        }
        ArrayList<Student> result = (ArrayList<Student>) studentDAO.getAllByColumns(columns, info);
        Print.printStudent(result);

    }

    public static void delete(Scanner scanner) {
        DAO<Student> studentDao = new DAO<>(Student.class);
        studentDao.delete(search(scanner));
    }


    private static Student search(Scanner scanner){
        DAO<Student> studentDAO = new DAO<>(Student.class);
        boolean correct = false;
        int mode;
        ArrayList<Student> student = new ArrayList<>();
        System.out.println("How do you want to search for the student?");
        System.out.println("Type 1 for searching by its student number\n" +
                "Type 2 for searching by its first and last name");

        do {
            mode = scanner.nextInt();
            if(mode == 1 || mode == 2 ){
                correct = true;
            } else {
                System.out.println("Your entry is incorrect.\n" +
                        "Do you want to search by student number (1) or by name (2)?");
            }

        } while (!correct);

        if(mode == 1){
            correct = false;
            do {
                System.out.println("What is the student number you're searching for?\n");
                String id = scanner.nextLine();
                student.add(studentDAO.get(id));
                //TODO: gestion du "si 0 étudiant correspondant"

                System.out.println("Is this the correct Student?");
                Print.printStudent(student);

                if(scanner.nextLine().equalsIgnoreCase("yes")){
                    correct = true;
                }
            } while (!correct);

        } else {
            student.add(gather(scanner));
        }

        return student.get(0);
    }

    protected static Student gather(Scanner scanner) {
        DAO<Student> studentDAO = new DAO<>(Student.class);
        boolean correct = false;
        Student student = null;

        System.out.println("Last Name: ");
        String lastName = scanner.nextLine().toLowerCase();
        System.out.println("First Name: ");
        String firstName = scanner.nextLine().toLowerCase();

        String[] columns = new String[]{"lastname", "firstname"};
        ArrayList<Object> values = new ArrayList<>();
        values.add(lastName);
        values.add(firstName);
        ArrayList<Student> result = (ArrayList<Student>) studentDAO.getAllByColumns(columns, values);

        if (result.size() > 1) {
            do {
                System.out.println("""
                        Several student correspond to your search.
                        Choose the student to be edited by typing its ID.
                        """);
                Print.printStudent(result);

                String id = scanner.nextLine();

                for (Student value : result) {
                    if (((Student) value).getStudentNumber().equals(id)) {
                        correct = true;
                    }
                    student = ((Student) value);
                }

            } while (!correct);
        }

        return student;

    }
}
