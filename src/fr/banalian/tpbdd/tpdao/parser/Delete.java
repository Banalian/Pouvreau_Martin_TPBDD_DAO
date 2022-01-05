package fr.banalian.tpbdd.tpdao.parser;

import static fr.banalian.tpbdd.tpdao.MainClass.isInteger;

public class Delete {

    public static boolean delete(String[] arguments) {
        boolean result = false;
        if (arguments.length < 2) {
            System.err.println("Not enough arguments");
            System.out.println("Type 'delete help' for help");
        }

        //Special case for delete application (needs a string id and grant id)
        if (arguments[1].equalsIgnoreCase("application")) {
            if (arguments.length < 4) {
                System.err.println("Not enough arguments");
                System.out.println("Type 'delete help' for help");
            } else {
                if (isInteger(arguments[3])) {

                    // TODO: delete application
                    if (!result) {
                        System.err.println("No application found with this student number and/or grant id");
                    }
                    return result;
                }
            }

        }

        //Special case for delete student (needs a string id)
        if (arguments[1].equalsIgnoreCase("student")) {
            if (arguments.length < 3) {
                System.err.println("Not enough arguments");
                System.out.println("Type 'delete help' for help");
            } else {
                //TODO: delete student

                if (!result) {
                    System.err.println("No student found with this id");
                }
                return result;
            }
        }


        if (arguments.length > 3) {
            System.err.println("Too many arguments");
            System.out.println("Type 'delete help' for help");
        }

        if (arguments[1].equalsIgnoreCase("help")) {
            System.out.println("delete [all <table>] | [<table> <id>]");
            System.out.println("Deletes a/all row/rows from a table");
            System.out.println("<table> can be:");
            System.out.println("application : Special case if there's no 'all' : delete application <Student Number> <grant id>");
            System.out.println("course");
            System.out.println("evaluation");
            System.out.println("grant");
            System.out.println("student : Special case if there's no 'all' : delete student <Student Number>");
            System.out.println("teacher");
            System.out.println("<id> is the id of the row to delete");
            return false;
        }

        if (arguments[1].equalsIgnoreCase("all")) {
            switch (arguments[2].toLowerCase()) {
                case "application":
                    //TODO : delete all applications
                    break;
                case "courses":
                    //TODO : delete all courses
                    break;
                case "evaluation":
                    //TODO : delete all evaluations
                    break;
                case "grant":
                    //TODO : delete all grants
                    break;
                case "student":
                    //TODO : delete all students
                    break;
                case "teacher":
                    //TODO : delete all teachers
                    break;

                default:
                    System.err.println("Unknown table, type 'get help' for help");


            }

        } else {

            if (isInteger(arguments[1])) {
                switch (arguments[1].toLowerCase()) {
                    case "courses":
                        //TODO : delete course with id
                        break;
                    case "evaluation":
                        //TODO : delete evaluation with id
                        break;
                    case "grant":
                        //TODO : delete grant with id
                        break;
                    case "teacher":
                        //TODO : delete teacher with id
                        break;

                    default:
                        System.err.println("Unknown table, type 'get help' for help");


                }

            } else {
                System.err.println("Invalid id");
            }
        }


        return result;
    }
}
