package fr.banalian.tpbdd.tpdao.parser;
import fr.banalian.tpbdd.tpdao.MainClass;
import fr.banalian.tpbdd.tpdao.dao.*;
import fr.banalian.tpbdd.tpdao.model.*;

import java.util.ArrayList;

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
                        ApplicationDAO applicationDAO = new ApplicationDAO();
                        ArrayList<Application> applications = applicationDAO.getAll();
                        printApplication(applications);
                        result = true;
                        break;
                    case "courses":
                        CoursesDAO coursesDAO = new CoursesDAO();
                        ArrayList<Courses> courses = coursesDAO.getAll();
                        printCourses(courses);
                        result = true;
                        break;
                    case "evaluation":
                        EvaluationDAO evaluationDAO = new EvaluationDAO();
                        ArrayList<Evaluation> evaluations = evaluationDAO.getAll();
                        printEvaluation(evaluations);
                        result = true;
                        break;
                    case "grant":
                        GrantDAO grantDAO = new GrantDAO();
                        ArrayList<Grant> grants = grantDAO.getAll();
                        printGrant(grants);
                        result = true;
                        break;
                    case "student":
                        StudentDAO studentDAO = new StudentDAO();
                        ArrayList<Student> students = studentDAO.getAll();
                        printStudent(students);
                        result = true;
                        break;
                    case "teacher":
                        TeacherDAO teacherDAO = new TeacherDAO();
                        ArrayList<Teacher> teachers = teacherDAO.getAll();
                        printTeacher(teachers);
                        result = true;
                        break;

                    default:
                        System.err.println("Unknown table, type 'get help' for help");


                }


            } else { //Else if you want to get a specific entry of a table

                if (arguments.length < 4) {
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
                        ApplicationDAO applicationDAO = new ApplicationDAO();
                        ArrayList<Application> applications;
                        switch (arguments[iToRead + 1].toLowerCase()) {
                            case "grant":
                                if (MainClass.isInteger(arguments[iToRead + 2])) {
                                    applications = applicationDAO.getByGrantId(Integer.parseInt(arguments[iToRead + 2]));
                                    if (applications != null) {
                                        printApplication(applications);
                                        result = true;
                                    } else {
                                        System.err.println("No application found for this grant");
                                    }
                                }
                                break;

                            case "university":
                                applications = applicationDAO.getByUniversity(arguments[iToRead + 2]);
                                if (applications != null) {
                                    printApplication(applications);
                                    result = true;
                                } else {
                                    System.err.println("No application found for this University");
                                }
                                break;

                            case "finalgrade":
                                if (MainClass.isInteger(arguments[iToRead + 2])) {
                                    applications = applicationDAO.getBySupFinalGrade(Integer.parseInt(arguments[iToRead + 2]));
                                    if (applications != null) {
                                        printApplication(applications);
                                        result = true;
                                    } else {
                                        System.err.println("No application found where the student's final grade is superior");
                                    }
                                }
                                break;

                            default:
                                System.err.println("Invalid column\nUse 'get help' for help");
                        }
                        break;
                    case "courses":
                        CoursesDAO courseDAO = new CoursesDAO();
                        ArrayList<Courses> course;
                        switch (arguments[iToRead + 1].toLowerCase()) {
                            case "id":
                                if (MainClass.isInteger(arguments[iToRead + 2])) {
                                    course = new ArrayList<>();
                                    course.add(courseDAO.getByCourseId(Integer.parseInt(arguments[iToRead + 2])));
                                    if (course.size() > 0) {
                                        printCourses(course);
                                        result = true;
                                    } else {
                                        System.err.println("No course found with this id");
                                    }
                                }
                                break;

                            case "university":
                                course = courseDAO.getByUniversity(arguments[iToRead + 2]);
                                if (course != null) {
                                    printCourses(course);
                                    result = true;
                                } else {
                                    System.err.println("No course found with this university");
                                }
                                break;

                            case "ects":
                                if (MainClass.isInteger(arguments[iToRead + 2])) {
                                    course = courseDAO.getByEcts(Integer.parseInt(arguments[iToRead + 2]));
                                    if (course != null) {
                                        printCourses(course);
                                        result = true;
                                    } else {
                                        System.err.println("No course found with this number of ECTS");
                                    }
                                }
                                break;

                            default:
                                System.err.println("Invalid column\nUse 'get help' for help");

                        }
                        break;
                    case "evaluation":
                        EvaluationDAO evaluationDAO = new EvaluationDAO();
                        ArrayList<Evaluation> evaluation;
                        switch (arguments[iToRead + 1].toLowerCase()) {
                            case "id":
                                if (MainClass.isInteger(arguments[iToRead + 2])) {
                                    evaluation = new ArrayList<>();
                                    evaluation.add(evaluationDAO.getByEvaluationId(Integer.parseInt(arguments[iToRead + 2])));
                                    if (evaluation.size() > 0) {
                                        printEvaluation(evaluation);
                                        result = true;
                                    } else {
                                        System.err.println("No Evaluation found with this id");
                                    }
                                }
                                break;

                            case "teacherid":
                                if (MainClass.isInteger(arguments[iToRead + 2])) {
                                    evaluation = evaluationDAO.getByTeacher(Integer.parseInt(arguments[iToRead + 2]));
                                    if (evaluation != null) {
                                        printEvaluation(evaluation);
                                        result = true;
                                    } else {
                                        System.err.println("No course found with this university");
                                    }
                                }
                                break;

                            default:
                                System.err.println("Invalid column\nUse 'get help' for help");
                        }
                        break;
                    case "grant":
                        GrantDAO grantDAO = new GrantDAO();
                        ArrayList<Grant> grant;
                        switch (arguments[iToRead + 1].toLowerCase()) {
                            case "id":
                                if (MainClass.isInteger(arguments[iToRead + 2])) {
                                    grant = new ArrayList<>();
                                    grant.add(grantDAO.get(Integer.parseInt(arguments[iToRead + 2])));
                                    if (grant.size() > 0) {
                                        printGrant(grant);
                                        result = true;
                                    } else {
                                        System.err.println("No grant found for this id");
                                    }
                                }
                                break;

                            case "destination":
                                grant = grantDAO.getByDestination(arguments[iToRead + 2]);
                                if (grant != null) {
                                    printGrant(grant);
                                    result = true;
                                } else {
                                    System.err.println("No Grant found for this destination");
                                }
                                break;

                            case "teacherid":
                                if (MainClass.isInteger(arguments[iToRead + 2])) {
                                    grant = grantDAO.getByTeacherId(Integer.parseInt(arguments[iToRead + 2]));
                                    if (grant != null) {
                                        printGrant(grant);
                                        result = true;
                                    } else {
                                        System.err.println("No grant found managed by this teacher");
                                    }
                                }
                                break;

                            default:
                                System.err.println("Invalid column\nUse 'get help' for help");
                        }
                        break;

                    case "student":
                        StudentDAO studentDAO = new StudentDAO();
                        ArrayList<Student> student;
                        switch (arguments[iToRead + 1].toLowerCase()) {
                            case "studentnumber":
                                student = new ArrayList<>();
                                student.add(studentDAO.get(arguments[iToRead + 2]));
                                if (student.size() > 0) {
                                    printStudent(student);
                                    result = true;
                                } else {
                                    System.err.println("No student found for this id");
                                }
                                break;

                            default:
                                System.err.println("Invalid column\nUse 'get help' for help");
                        }
                        break;

                    case "teacher":
                        TeacherDAO teacherDAO = new TeacherDAO();
                        ArrayList<Teacher> teachers;
                        switch (arguments[iToRead + 1].toLowerCase()) {
                            case "id":
                                if (MainClass.isInteger(arguments[iToRead + 2])) {
                                    teachers = new ArrayList<>();
                                    teachers.add(teacherDAO.get(Integer.parseInt(arguments[iToRead + 2])));
                                    if (teachers.size() > 0) {
                                        Print.printTeacher(teachers);
                                        result = true;
                                    } else {
                                        System.err.println("No teacher found for this id");
                                    }
                                }
                                break;

                            default:
                                System.err.println("Invalid column\nUse 'get help' for help");
                        }
                        break;
                    default:
                        System.err.println("Unknown table, type 'get help' for help");


                }
            }
        }
        return result;
    }

}
