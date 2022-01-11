package fr.banalian.tpbdd.tpdao;
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



