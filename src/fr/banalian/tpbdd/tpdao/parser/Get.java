package fr.banalian.tpbdd.tpdao.parser;
import fr.banalian.tpbdd.tpdao.ConnectBdd;
import fr.banalian.tpbdd.tpdao.MainClass;
import fr.banalian.tpbdd.tpdao.model.*;

import java.util.ArrayList;
import java.util.List;

import static fr.banalian.tpbdd.tpdao.parser.Print.*;

public class Get {
    public static boolean get(String[] arguments) {
        boolean result = false;
        boolean all = false;
        int iToRead = 1;

        if (arguments.length < 2) {
            System.err.println("Not enough arguments");
            System.out.println("Usage : get [all] <tableName> [<colName> <value>]\n Or use 'get help' for help");
            return false;
        }

        String tableName = arguments[1].toLowerCase();

        if (tableName.equals("help")) {
            System.out.println("Usage : get [all] <tableName> [<colName> <value>]\n Or use 'get help' for help");
            System.out.println("Add 'all' to get all the entries of a table");
            System.out.println("for example : get all student\n");

            System.out.println("Available tables :");
            System.out.println("\t - application");

            System.out.println("\t - courses");

            System.out.println("\t - evaluation :  you should not use this table directly");

            System.out.println("\t - grant");

            System.out.println("\t - student");

            System.out.println("\t - teacher");
            return false;
        }

        if (arguments.length >= 2) {

            // IF you want to get all the entries of a table
            if (arguments[iToRead].equals("all")) {
                iToRead++;
                if (arguments.length < 3) {
                    System.err.println("Not enough arguments");
                    System.out.println("Usage : get [all] <tableName> [<colName> <value>]\n Or use 'get help' for help");
                    return false;
                }

                switch (arguments[iToRead].toLowerCase()) {
                    case "application":
                        //TODO : get all application
                        List<Application> apps = ConnectBdd.getEntityManager().createQuery("SELECT a FROM Application a").getResultList();
                        for (Application a : apps) {
                            System.out.println(a);
                        }
                        result = true;
                        break;
                    case "courses":
                        //TODO : get all courses
                        List<Courses> courses = ConnectBdd.getEntityManager().createQuery("SELECT c FROM Courses c").getResultList();
                        for (Courses c : courses) {
                            System.out.println(c);
                        }

                        result = true;
                        break;
                    case "evaluation":
                        //TODO : get all evaluation
                        //get all evaluation using hibernate
                        List<Evaluation> evaluations = ConnectBdd.getEntityManager().createQuery("SELECT e FROM Evaluation e").getResultList();
                        for (Evaluation e : evaluations) {
                            System.out.println(e);
                        }

                        result = true;
                        break;
                    case "grant":
                        //TODO : get all grant
                        List<Scholarship> scholarships = ConnectBdd.getEntityManager().createQuery("SELECT s FROM Scholarship s").getResultList();
                        for (Scholarship s : scholarships) {
                            System.out.println(s);
                        }
                        result = true;
                        break;
                    case "student":
                        //TODO : get all student
                        List<Student> students = ConnectBdd.getEntityManager().createQuery("SELECT s FROM Student s").getResultList();
                        for (Student s : students) {
                            System.out.println(s);
                        }
                        result = true;
                        break;
                    case "teacher":
                        //TODO : get all teacher
                        List<Teacher> teachers = ConnectBdd.getEntityManager().createQuery("SELECT t FROM Teacher t").getResultList();
                        for (Teacher t : teachers) {
                            System.out.println(t);
                        }
                        result = true;
                        break;

                    default:
                        System.err.println("Unknown table, type 'get help' for help");


                }


            } else { //Else if you want to get a specific entry of a table

                if (arguments.length < 4) {
                    //TODO : adapt help with the correct column available
                    System.err.println("Not enough arguments");
                    System.out.println("Usage : get [all] <tableName> <colName> <value>\n Or use 'get help' for help");
                    System.out.println("Available columns per table : ");
                    System.out.println("\t- student : studentNumber");
                    System.out.println("\t- teacher : id");
                    System.out.println("\t- evaluation : id, teacherId");
                    System.out.println("\t- grant : id, destination, teacherId");
                    System.out.println("\t- course : id, ects, university");
                    System.out.println("\t- application : grant, university, finalgrade");
                    return false;
                }

                switch (arguments[iToRead].toLowerCase()) {
                    case "application":
                        //TODO : get application with specific columns
                        break;
                    case "courses":
                        //TODO : get courses with specific columns
                        break;
                    case "evaluation":
                        //TODO : get evaluation with specific columns
                        break;
                    case "grant":
                        //TODO : get grant with specific columns
                        break;

                    case "student":
                        //TODO : get student with specific columns
                        break;

                    case "teacher":
                        //TODO : get teacher with specific columns
                        break;
                    default:
                        System.err.println("Unknown table, type 'get help' for help");


                }
            }
        }
        return result;
    }

}
