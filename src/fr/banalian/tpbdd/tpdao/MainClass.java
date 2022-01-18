package fr.banalian.tpbdd.tpdao;

import fr.banalian.tpbdd.tpdao.model.*;
import fr.banalian.tpbdd.tpdao.parser.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;


public class MainClass {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("erasmus");
        EntityManager em = emf.createEntityManager();


        ConnectBdd.initConnection();
        String input;
        String[] arguments;
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
                    result = Add.add(arguments);
                    if (result) {
                        System.out.println("Entry added");
                    } else {
                        System.err.println("No entry added");
                    }
                    break;

                case "get":
                    result = Get.get(arguments);
                    if (!result) {
                        System.err.println("No entry found");
                    }
                    break;

                case "delete":
                    result = Delete.delete(arguments);
                    if (result) {
                        System.out.println("Entry deleted");
                    } else {
                        System.err.println("No entry deleted");
                    }
                    break;

                case "update":
                    result = Update.update(arguments);
                    if (result) {
                        System.out.println("Entry updated");
                    } else {
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


    public void menu() {
        System.out.println("Welcome. type 'help' for help");
        String input;
        boolean stop = false;
        Scanner sc = new Scanner(System.in);
        do {
            input = sc.nextLine().toLowerCase();
            switch (input) {
                case "help":
                    helpMenu("help", null);
                    break;
                case "exit":
                    stop = true;
                    break;
                case "1":
                case "2":
                case "3":
                case "4":
                case "5":
                case "6":
                    classMenu(Integer.parseInt(input), sc);
                    break;
                default:
                    System.out.println("Unknown command, type 'help' for help");
            }
        } while (!stop);
    }

    public void helpMenu(String help, Class currentClass) {

        switch (help) {
            case "help":
                System.out.println("Available commands :");
                System.out.println("\t- Help : display this help");
                System.out.println("\t- Exit : exit the program");
                System.out.print("Else, ");
            case "main":
                System.out.println("Use one of the following numbers to see the corresponding menu :");
                System.out.println("\t- 1 - Application");
                System.out.println("\t- 2 - Students");
                System.out.println("\t- 3 - Teachers");
                System.out.println("\t- 4 - Universities");
                System.out.println("\t- 5 - Scholarships");
                System.out.println("\t- 6 - Courses");
                break;
            case "class":
                if (currentClass == null) {
                    System.err.println("No class selected");
                    break;
                }
                String name = currentClass.getSimpleName();
                System.out.println("enter a number:");
                System.out.println("\t- 1 - Add one " + name);
                System.out.println("\t- 2 - Update one " + name);
                System.out.println("\t- 3 - Delete one " + name);
                System.out.println("\t- 4 - Get one or more " + name);
                System.out.println("Back : - Go back to the previous menu");

        }


    }


    public void classMenu(int classNumber, Scanner sc) {
        Scanner scanner = sc;
        Class currentClass = null;
        if(classNumber<1 || classNumber>6){
            System.err.println("Unknown class");
            return;
        }

        switch (classNumber) {
            case 1:
                currentClass = Application.class;
                break;
            case 2:
                currentClass = Student.class;
                break;
            case 3:
                currentClass = Teacher.class;
                break;
            case 4:
            case 6:
                currentClass = Courses.class;
                break;
            case 5:
                currentClass = Scholarship.class;
                break;
        }

        //TODO: show menu
    }
/*
Main:
Help
Applications
    Create new application
    Update application X
    Update application evaluation
    See all applications
    See application of student X
    Delete applicationX
Students
    Create new student
    Update student X
    See all students
    See student X
    Delete student X
Teachers
    Create new teacher
    Update teacher X
    See all teachers
    See teacher X
    Delete teacher X
Scholarships
    Create new scholarship
    See all scholarship
    See specific scholarship using X column
    Delete scholarship
Courses
    Create new course
    See all courses
    See courses by X
    Delete course X
University
    Delete university X
Exit
 */


    //=====================================ISTYPE=========================================

    public static boolean isInteger(String argument) {
        try {
            Integer.parseInt(argument);
            return true;
        } catch (NumberFormatException e) {
            System.err.println("The value '" + argument + "' is not an integer");
            return false;
        }
    }

    public static boolean isFloat(String argument) {
        try {
            Float.parseFloat(argument);
            return true;
        } catch (NumberFormatException e) {
            System.err.println("The value '" + argument + "' is not a float");
            return false;
        }
    }


}



