package fr.banalian.tpbdd.tpdao;

import fr.banalian.tpbdd.tpdao.dao.*;
import fr.banalian.tpbdd.tpdao.model.*;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class MainClass {

    public static void main(String[] args) {

        ConnectBdd.initConnection();
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


        ConnectBdd.closeConnection();
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
                if(arguments.length < 5) {
                    System.err.println("Not enough arguments");
                    System.out.println("Usage : add application <studentid : String> <grantid : int> <university : string>");
                    return false;
                }else if(arguments.length > 5) {
                    System.err.println("Too many arguments");
                    System.out.println("Usage : add application <studentid : String> <grantid : int> <university : string>");
                }
                String [] valuesApplication = new String[arguments.length - 2];
                System.arraycopy(arguments, 2, valuesApplication, 0, arguments.length - 2);
                try {
                    int grantId = Integer.parseInt(valuesApplication[1]);
                    if(grantId < 0) {
                        throw new IllegalArgumentException("grantid must be positive");
                    }
                }catch (NumberFormatException e) {
                    System.err.println("The grant id must be an integer");
                    return false;
                }catch (IllegalArgumentException e) {
                    System.err.println(e.getMessage());
                }

                EvaluationDAO evaluationDAO = new EvaluationDAO();
                //TODO : handle the teacher id request
                Evaluation evaluation1 = new Evaluation(0, 0);
                evaluationDAO.add(evaluation1);
                Evaluation evaluation2 = new Evaluation(0, 0);
                evaluationDAO.add(evaluation1);

                ApplicationDAO applicationDAO = new ApplicationDAO();
                Application temp = new Application(valuesApplication[0], Integer.parseInt(valuesApplication[1]), valuesApplication[2], evaluation1.getId(), evaluation2.getId(), 0);
                result = applicationDAO.add(temp);
                break;

            case "courses":
                if(arguments.length < 6) {
                    System.err.println("Not enough arguments");
                    System.out.println("Usage : add courses <university : String> <name : String> <ects : int> <hours : float>");
                }else if(arguments.length > 6) {
                    System.err.println("Too many arguments");
                    System.out.println("Usage : add courses <university : String> <name : String> <ects : int> <hours : float>");
                }
                String [] valuesCourses = new String[arguments.length - 2];
                System.arraycopy(arguments, 2, valuesCourses, 0, arguments.length - 2);
                try {
                    int ects = Integer.parseInt(valuesCourses[2]);
                    float hours = Float.parseFloat(valuesCourses[3]);
                    if(ects < 0 || hours < 0) {
                        throw new IllegalArgumentException("ects and hours must be positive");
                    }
                }catch (NumberFormatException e) {
                    System.err.println("The ects and hours must be an integer");
                }catch (IllegalArgumentException e) {
                    System.err.println(e.getMessage());
                }

                CoursesDAO courseDAO = new CoursesDAO();
                Courses tempCourse = new Courses(valuesCourses[1], Integer.parseInt(valuesCourses[2]), Float.parseFloat(valuesCourses[3]), valuesCourses[0]);
                result = courseDAO.add(tempCourse);

                break;
            case "evaluation":

                break;
            case "grant":
                if(arguments.length < 5) {
                    System.err.println("Not enough arguments");
                    System.out.println("Usage : add grant <destination : String> <total seats : int> <teacher id : int>");
                }else if(arguments.length > 5) {
                    System.err.println("Too many arguments");
                    System.out.println("Usage : add grant <destination : String> <total seats : int> <teacher id : int>");
                }
                String [] valuesGrant = new String[arguments.length - 2];
                System.arraycopy(arguments, 2, valuesGrant, 0, arguments.length - 2);
                try {
                    int totalSeats = Integer.parseInt(valuesGrant[1]);
                    int teacherId = Integer.parseInt(valuesGrant[2]);
                    if(teacherId < 0 || totalSeats < 0) {
                        throw new IllegalArgumentException("teacher id and total seats must be positive");
                    }
                }catch (NumberFormatException e) {
                    System.err.println("The teacher id and total seats must be an integer");
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getMessage());
                }

                GrantDAO grantDAO = new GrantDAO();
                Grant tempGrant = new Grant(valuesGrant[0], Integer.parseInt(valuesGrant[1]), Integer.parseInt(valuesGrant[2]));
                result = grantDAO.add(tempGrant);
                break;
            case "student":
                // TODO
                break;

            case "teacher":
                if(arguments.length < 4) {
                    System.err.println("Not enough arguments");
                    System.out.println("Usage : add teacher <first name : String> <last name : String>");
                }else if(arguments.length > 4) {
                    System.err.println("Too many arguments");
                    System.out.println("Usage : add teacher <first name : String> <last name : String>");
                }
                String [] valuesTeacher = new String[arguments.length - 2];
                System.arraycopy(arguments, 2, valuesTeacher, 0, arguments.length - 2);
                TeacherDAO teacherDAO = new TeacherDAO();
                Teacher tempTeacher = new Teacher(valuesTeacher[0], valuesTeacher[1]);
                result = teacherDAO.add(tempTeacher);
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
                        result = true;
                        break;
                    case "grant":
                        GrantDAO grantDAO = new GrantDAO();
                        ArrayList<Grant> grants;
                        System.out.println("|\tId\t|\tDestination\t|\tTotal Seats\t|\tTeacher ID\t|");
                        grants = grantDAO.getAll();
                        for(Grant grant : grants){
                            System.out.println(grant.toString());
                        }
                        result = true;
                        break;
                    case "student":
                        StudentDAO studentDAO = new StudentDAO();
                        ArrayList<Student> students;
                        System.out.println("|\tStudent Number\t|\tLast Name\t|\tFirst Name\t|\tAverage Grade\t|");
                        students = studentDAO.getAll();
                        for(Student student : students){
                            System.out.println(student.toString());
                        }
                        result = true;
                        break;
                    case "teacher":
                        TeacherDAO teacherDAO = new TeacherDAO();
                        ArrayList<Teacher> teachers;
                        System.out.println("|\tId\t|\tLast Name\t|\tFirst Name\t|");
                        teachers = teacherDAO.getAll();
                        for(Teacher teacher : teachers){
                            System.out.println(teacher.toString());
                        }
                        result = true;
                        break;

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
