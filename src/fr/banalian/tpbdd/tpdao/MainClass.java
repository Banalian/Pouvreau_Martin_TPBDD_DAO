package fr.banalian.tpbdd.tpdao;

import fr.banalian.tpbdd.tpdao.dao.*;
import fr.banalian.tpbdd.tpdao.model.*;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class MainClass {

    public static void main(String[] args) {

        //ConnectBdd.initConnection();
        String input;
        String [] arguments;
        Scanner sc = new Scanner(System.in);
        boolean result;
        boolean exit = false;
        System.out.println("Welcome. type 'help' for help");
        // while the user doesn't input "exit"
        do {
            input = sc.nextLine();
            arguments = input.split(" ");

            switch (arguments[0]) {
                case "help":
                    System.out.println("Available commands :");
                    System.out.println("\t- help : display this help");
                    System.out.println("\t- exit : exit the program");
                    System.out.println("\t- add : add a new entry in the database");
                    System.out.println("\t- get : get one or multiples entry/ies from the database");
                    System.out.println("\t- delete : delete one or multiples entry/ies from the database");
                    System.out.println("\t- update : update an entry from the database");


                    break;

                case "add":
                    result = add(arguments);
                    if (result) {
                        System.out.println("Entry added");
                    }else {
                        System.err.println("No entry added");
                    }
                    break;

                case "get":
                    result = get(arguments);
                    if (!result) {
                        System.err.println("No entry found");
                    }
                    break;

                case "delete":
                    result = delete(arguments);
                    if (result) {
                        System.out.println("Entry deleted");
                    }else {
                        System.err.println("No entry deleted");
                    }
                    break;

                case "update":
                    result = update(arguments);
                    if (result) {
                        System.out.println("Entry updated");
                    }else {
                        System.err.println("No entry updated");
                    }
                    break;


                case "exit":
                    System.out.println("Bye bye");
                    exit = true;
                    break;

                default:
                    System.err.println("Unknown command, type 'help' for help");
                    break;
            }



        } while (!exit);


        //ConnectBdd.closeConnection();
    }

    public static boolean add(String [] arguments) {
        if(arguments.length < 2) {
            System.err.println("Not enough arguments");
            System.out.println("Usage : add <tableName> [<Values> ...]\n Or use 'add help' for help");
            return false;
        }

        boolean result = false;

        String tableName = arguments[1].toLowerCase();
        switch (tableName) {

            case "help":
                System.out.println("Available tables :");
                System.out.println("\t - application");
                System.out.println("\t\t- add <studentid : String> <grantid : int> <university : string>");
                System.out.println("\t\tWill create the 2 evaluations for the student's application");

                System.out.println("\t - courses");
                System.out.println("\t\t- add <university : String> <name : String> <ects : int> <hours : float>");
                System.out.println("\t\tname is the name of the course, ects is the number of ECTS given, hours is the number of hours in the course");

                System.out.println("\t - evaluation :  you should not use this table directly");
                System.out.println("\t\t- add <grade : float> <teacherid : int>");

                System.out.println("\t - grant");
                System.out.println("\t\t- add <destination : String> <total seat opportunities : int> <teacher id : int>");
                System.out.println("\t\tthe teacher id is the id of the teacher who is responsible for the grant/destination");

                System.out.println("\t - student");
                System.out.println("\t\t- add <firstname : String> <lastname : String> <student number : String>");

                System.out.println("\t - teacher");
                System.out.println("\t\t- add <firstname : String> <lastname : String>");

            case "application":
                // TODO
                break;
            case "courses":
                // TODO
                break;
            case "evaluation":
                // TODO
                break;
            case "grant":
                // TODO
                break;
            case "student":
                // TODO
                break;
            case "teacher":
                // TODO
                break;

            default:
                System.err.println("Unknown table or argument, type 'add help' for help");
        }


        return result;
    }

    public static boolean get(String [] arguments) {
        boolean result = false;
        boolean all = false;
        int iToRead = 1;

        if(arguments.length < 2) {
            System.err.println("Not enough arguments");
            System.out.println("Usage : get [all] <tableName> [<Values> ...]\n Or use 'get help' for help");
            return false;
        }

        String tableName = arguments[1].toLowerCase();

        if(tableName.equals("help")) {
            System.out.println("Add 'all' to get all the entries of a table");
            System.out.println("for example : get all student\n");

            System.out.println("Available tables :");
            System.out.println("\t - application");

            System.out.println("\t - courses");

            System.out.println("\t - evaluation :  you should not use this table directly");

            System.out.println("\t - grant");

            System.out.println("\t - student");

            System.out.println("\t - teacher");

        }

        if(arguments.length >= 2) {

            // IF you want to get all the entries of a table
            if(arguments[iToRead].equals("all")) {
                iToRead++;
                if (arguments.length < 3) {
                    System.err.println("Not enough arguments");
                    System.out.println("Usage : get [all] <tableName> [<Values> ...]\n Or use 'get help' for help");
                    return false;
                }

                switch (arguments[iToRead].toLowerCase()) {
                    case "application":
                        ApplicationDAO applicationDAO = new ApplicationDAO();
                        ArrayList<Application> applications;
                        System.out.println("|\tStudent number\t|\tGrantId\t|\tUniversity\t|\tEvaluation 1\t|\tEvaluation 2\t|\tFinal grade\t|");
                        applications = applicationDAO.getAll();
                        for(Application application : applications) {
                            System.out.println(application.toString());
                        }
                        result = true;
                        break;
                    case "courses":
                        CoursesDAO coursesDAO = new CoursesDAO();
                        ArrayList<Courses> courses;
                        System.out.println("|\tId\t|\tName\t|\tHours\t|\tECTS\t|\tUniversity\t|");
                        courses = coursesDAO.getAll();
                        for(Courses course : courses) {
                            System.out.println(course.toString());
                        }
                        result = true;
                        break;
                    case "evaluation":
                        EvaluationDAO evaluationDAO = new EvaluationDAO();
                        ArrayList<Evaluation> evaluations;
                        System.out.println("|\tId\t|\tGrade\t|\tTeacher ID\t|");
                        evaluations = evaluationDAO.getAll();
                        for(Evaluation evaluation : evaluations){
                            System.out.println(evaluation.toString());
                        }
                        break;
                    case "grant":
                        GrantDAO grantDAO = new GrantDAO();
                        ArrayList<Grant> grants;
                        System.out.println("|\tId\t|\tDestination\t|\tTotal Seats\t|\tTeacher ID\t|");
                        grants = grantDAO.getAll();
                        for(Grant grant : grants){
                            System.out.println(grant.toString());
                        }
                        break;
                    case "student":
                        StudentDAO studentDAO = new StudentDAO();
                        ArrayList<Student> students;
                        System.out.println("|\tStudent Number\t|\tLast Name\t|\tFirst Name\t|\tAverage Grade\t|");
                        students = studentDAO.getAll();
                        for(Student student : students){
                            System.out.println(student.toString());
                        }
                        break;
                    case "teacher":
                        TeacherDAO teacherDAO = new TeacherDAO();
                        ArrayList<Teacher> teachers;
                        System.out.println("|\tId\t|\tLast Name\t|\tFirst Name\t|");
                        teachers = teacherDAO.getAll();
                        for(Teacher teacher : teachers){
                            System.out.println(teacher.toString());
                        }

                    default:
                        System.err.println("Unknown table, type 'get help' for help");


                }


            }else{ //Else if you want to get a specific entry of a table

                switch (arguments[iToRead].toLowerCase()) {
                    //TODO : add the possibilities of each table
                    case "application":
                        // TODO
                        break;
                    case "courses":
                        // TODO
                        break;
                    case "evaluation":
                        // TODO
                        break;
                    case "grant":
                        // TODO
                        break;
                    case "student":
                        // TODO

                        break;
                    case "teacher":
                        // TODO

                    default:
                        System.err.println("Unknown table, type 'get help' for help");


                }

            }





        }


        return result;
    }


    private static boolean delete(String[] arguments) {
        // TODO
        System.out.println("Not yet implemented");
        return false;
    }

    private static boolean update(String[] arguments) {
        // TODO
        System.out.println("Not yet implemented");
        return false;
    }



}
