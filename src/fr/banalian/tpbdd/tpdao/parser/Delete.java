package fr.banalian.tpbdd.tpdao.parser;
import fr.banalian.tpbdd.tpdao.dao.*;

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
                    ApplicationDAO applicationDAO = new ApplicationDAO();
                    result = applicationDAO.deleteStudentApplication(arguments[2], Integer.parseInt(arguments[3]));
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
                StudentDAO studentDAO = new StudentDAO();
                result = studentDAO.delete(arguments[2]);
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
                    ApplicationDAO applicationDAO = new ApplicationDAO();
                    result = applicationDAO.deleteAll();
                    break;
                case "courses":
                    CoursesDAO coursesDAO = new CoursesDAO();
                    result = coursesDAO.deleteAll();
                    break;
                case "evaluation":
                    EvaluationDAO evaluationDAO = new EvaluationDAO();
                    result = evaluationDAO.deleteAll();
                    break;
                case "grant":
                    GrantDAO grantDAO = new GrantDAO();
                    result = grantDAO.deleteAll();
                    break;
                case "student":
                    StudentDAO studentDAO = new StudentDAO();
                    result = studentDAO.deleteAll();
                    break;
                case "teacher":
                    TeacherDAO teacherDAO = new TeacherDAO();
                    result = teacherDAO.deleteAll();
                    break;

                default:
                    System.err.println("Unknown table, type 'get help' for help");


            }

        } else {

            if (isInteger(arguments[1])) {
                switch (arguments[1].toLowerCase()) {
                    case "courses":
                        CoursesDAO coursesDAO = new CoursesDAO();
                        result = coursesDAO.delete(Integer.parseInt(arguments[2]));
                        break;
                    case "evaluation":
                        EvaluationDAO evaluationDAO = new EvaluationDAO();
                        result = evaluationDAO.delete(Integer.parseInt(arguments[2]));
                        break;
                    case "grant":
                        GrantDAO grantDAO = new GrantDAO();
                        result = grantDAO.delete(Integer.parseInt(arguments[2]));
                        break;
                    case "teacher":
                        TeacherDAO teacherDAO = new TeacherDAO();
                        result = teacherDAO.delete(Integer.parseInt(arguments[2]));
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
