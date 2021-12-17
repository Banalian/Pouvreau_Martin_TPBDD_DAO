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
                    result = add(arguments);
                    if (result) {
                        System.out.println("Entry added");
                    } else {
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
                    } else {
                        System.err.println("No entry deleted");
                    }
                    break;

                case "update":
                    result = update(arguments);
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

    //=====================================ADD=========================================

    public static boolean add(String[] arguments) {
        if (arguments.length < 2) {
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
                System.out.println("\t\t- add <firstname : String> <lastname : String> <student number : String> <average grade : float>");

                System.out.println("\t - teacher");
                System.out.println("\t\t- add <firstname : String> <lastname : String>");

            case "application":
                if (arguments.length < 7) {
                    System.err.println("Not enough arguments");
                    System.out.println("Usage : add application <studentid : String> <grantid : int> <university : string> <eval1 TeacherId : int> <eval2 TeacherId : int>");
                    break;
                } else if (arguments.length > 7) {
                    System.err.println("Too many arguments");
                    System.out.println("Usage : add application <studentid : String> <grantid : int> <university : string> <eval1 TeacherId : int> <eval2 TeacherId : int>");
                    break;
                }
                String[] valuesApplication = new String[arguments.length - 2];
                System.arraycopy(arguments, 2, valuesApplication, 0, arguments.length - 2);
                try {
                    int grantId = Integer.parseInt(valuesApplication[1]);
                    if (grantId < 0) {
                        throw new IllegalArgumentException("grantid must be positive");
                    }
                    int eval1TeacherId = Integer.parseInt(valuesApplication[3]);
                    if (eval1TeacherId < 0) {
                        throw new IllegalArgumentException("eval1TeacherId must be positive");
                    }
                    int eval2TeacherId = Integer.parseInt(valuesApplication[4]);
                    if (eval2TeacherId < 0) {
                        throw new IllegalArgumentException("eval2TeacherId must be positive");
                    }
                } catch (NumberFormatException e) {
                    System.err.println("The grant id must be an integer");
                    return false;
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getMessage());
                }

                EvaluationDAO evaluationDAO = new EvaluationDAO();
                //TODO : handle the teacher id request
                Evaluation evaluation1 = new Evaluation(0, Integer.parseInt(valuesApplication[3]));
                evaluationDAO.add(evaluation1);
                Evaluation evaluation2 = new Evaluation(0, Integer.parseInt(valuesApplication[4]));
                evaluationDAO.add(evaluation2);

                ApplicationDAO applicationDAO = new ApplicationDAO();
                Application temp = new Application(valuesApplication[0], Integer.parseInt(valuesApplication[1]), valuesApplication[2], evaluation1.getId(), evaluation2.getId(), 0);
                result = applicationDAO.add(temp);
                break;

            case "courses":
                if (arguments.length < 6) {
                    System.err.println("Not enough arguments");
                    System.out.println("Usage : add courses <university : String> <name : String> <ects : int> <hours : float>");
                    break;
                } else if (arguments.length > 6) {
                    System.err.println("Too many arguments");
                    System.out.println("Usage : add courses <university : String> <name : String> <ects : int> <hours : float>");
                    break;
                }
                String[] valuesCourses = new String[arguments.length - 2];
                System.arraycopy(arguments, 2, valuesCourses, 0, arguments.length - 2);
                try {
                    int ects = Integer.parseInt(valuesCourses[2]);
                    float hours = Float.parseFloat(valuesCourses[3]);
                    if (ects < 0 || hours < 0) {
                        throw new IllegalArgumentException("ects and hours must be positive");
                    }
                } catch (NumberFormatException e) {
                    System.err.println("The ects and hours must be an integer");
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getMessage());
                }

                CoursesDAO courseDAO = new CoursesDAO();
                Courses tempCourse = new Courses(valuesCourses[1], Integer.parseInt(valuesCourses[2]), Float.parseFloat(valuesCourses[3]), valuesCourses[0]);
                result = courseDAO.add(tempCourse);

                break;
            case "evaluation":
                System.out.println("Unsupported table, the evaluations are automatically created");
                break;

            case "grant":
                if (arguments.length < 5) {
                    System.err.println("Not enough arguments");
                    System.out.println("Usage : add grant <destination : String> <total seats : int> <teacher id : int>");
                    break;
                } else if (arguments.length > 5) {
                    System.err.println("Too many arguments");
                    System.out.println("Usage : add grant <destination : String> <total seats : int> <teacher id : int>");
                    break;
                }
                String[] valuesGrant = new String[arguments.length - 2];
                System.arraycopy(arguments, 2, valuesGrant, 0, arguments.length - 2);
                try {
                    int totalSeats = Integer.parseInt(valuesGrant[1]);
                    int teacherId = Integer.parseInt(valuesGrant[2]);
                    if (teacherId < 0 || totalSeats < 0) {
                        throw new IllegalArgumentException("teacher id and total seats must be positive");
                    }
                } catch (NumberFormatException e) {
                    System.err.println("The teacher id and total seats must be an integer");
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getMessage());
                }

                GrantDAO grantDAO = new GrantDAO();
                Grant tempGrant = new Grant(valuesGrant[0], Integer.parseInt(valuesGrant[1]), Integer.parseInt(valuesGrant[2]));
                result = grantDAO.add(tempGrant);
                break;
            case "student":
                if (arguments.length < 6) {
                    System.err.println("Not enough arguments");
                    System.out.println("Usage : add student <last name : String> <first name : String> <student number : String> <average grade : float>");
                    break;
                } else if (arguments.length > 6) {
                    System.err.println("Too many arguments");
                    System.out.println("Usage : add student <last name : String> <first name : String> <student number : String> <average grade : float>");
                    break;
                }
                String[] valuesStudent = new String[arguments.length - 2];
                System.arraycopy(arguments, 2, valuesStudent, 0, arguments.length - 2);
                try {
                    float averageGrade = Float.parseFloat(valuesStudent[3]);
                    if (averageGrade < 0) {
                        throw new IllegalArgumentException("average grade must be positive");
                    }
                } catch (NumberFormatException e) {
                    System.err.println("The average grade must be a float");
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getMessage());
                }

                StudentDAO studentDAO = new StudentDAO();
                Student tempStudent = new Student(valuesStudent[0], valuesStudent[1], valuesStudent[2], Float.parseFloat(valuesStudent[3]));
                result = studentDAO.add(tempStudent);
                break;

            case "teacher":
                if (arguments.length < 4) {
                    System.err.println("Not enough arguments");
                    System.out.println("Usage : add teacher <first name : String> <last name : String>");
                    break;
                } else if (arguments.length > 4) {
                    System.err.println("Too many arguments");
                    System.out.println("Usage : add teacher <first name : String> <last name : String>");
                    break;
                }
                String[] valuesTeacher = new String[arguments.length - 2];
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

    //=====================================GET=========================================

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
//                        System.out.printf("| %-20s | %-20s | %-20s | %-20s | %-20s | %-20s |\n", "Student number", "GrantId", "University", "Evaluation 1", "Evaluation 2", "Final grade");
//
//                        for (Application application : applications) {
//                            System.out.printf("| %-20s | %-20s | %-20s | %-20s | %-20s | %-20s |\n",
//                                    application.getStudentId(),
//                                    application.getGrantId(),
//                                    application.getUniversity(),
//                                    application.getEval1Id(),
//                                    application.getEval2Id(),
//                                    application.getFinalGrade());
//                        }
                        result = true;
                        break;
                    case "courses":
                        CoursesDAO coursesDAO = new CoursesDAO();
                        ArrayList<Courses> courses = coursesDAO.getAll();
                        printCourses(courses);
//                        System.out.printf("| %-20s | %-20s | %-20s | %-20s | %-20s |\n", "Id", "Name", "Hours", "ECTS", "University");
//                        for (Courses course : courses) {
//                            System.out.printf("| %-20s | %-20s | %-20s | %-20s | %-20s |\n",
//                                    course.getId(),
//                                    course.getName(),
//                                    course.getHours(),
//                                    course.getEcts(),
//                                    course.getUniversity());
//                        }
                        result = true;
                        break;
                    case "evaluation":
                        EvaluationDAO evaluationDAO = new EvaluationDAO();
                        ArrayList<Evaluation> evaluations = evaluationDAO.getAll();
                        printEvaluation(evaluations);
//                        System.out.printf("| %-20s | %-20s | %-20s |\n", "Id", "Grade", "Teacher ID");
//                        for (Evaluation evaluation : evaluations) {
//                            System.out.printf("|%-20s | %-20s | %-20s |\n",
//                                    evaluation.getId(),
//                                    evaluation.getGrade(),
//                                    evaluation.getTeacherId());
//                        }
                        result = true;
                        break;
                    case "grant":
                        GrantDAO grantDAO = new GrantDAO();
                        ArrayList<Grant> grants = grantDAO.getAll();
                        printGrant(grants);
//                        System.out.printf("| %-20s | %-20s | %-20s | %-20s |\n", "Id", "Destination", "Total Seats", "Teacher ID");
//                        for (Grant grant : grants) {
//                            System.out.printf("| %-20s | %-20s | %-20s | %-20s |\n",
//                                    grant.getId(),
//                                    grant.getDestination(),
//                                    grant.getTotalSeats(),
//                                    grant.getTeacherId());
//                        }
                        result = true;
                        break;
                    case "student":
                        StudentDAO studentDAO = new StudentDAO();
                        ArrayList<Student> students = studentDAO.getAll();
                        printStudent(students);
//                        System.out.printf("| %-20s | %-20s | %-20s | %-20s |\n", "Student Number", "Last Name", "First Name", "Average Grade");
//                        for (Student student : students) {
//                            System.out.printf("| %-20s | %-20s | %-20s | %-20s |\n",
//                                    student.getStudentNumber(),
//                                    student.getLastName(),
//                                    student.getFirstName(),
//                                    student.getAverageGrade());
//                        }
                        result = true;
                        break;
                    case "teacher":
                        TeacherDAO teacherDAO = new TeacherDAO();
                        ArrayList<Teacher> teachers = teacherDAO.getAll();
                        printTeacher(teachers);
//                        System.out.printf("| %-20s | %-20s | %-20s |\n", "Id", "Last Name", "First Name");
//                        for (Teacher teacher : teachers) {
//                            System.out.printf("| %-20s | %-20s | %-20s |\n",
//                                    teacher.getId(),
//                                    teacher.getLastName(),
//                                    teacher.getFirstName());
//                        }
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
                                if (isInteger(arguments[iToRead + 2])) {
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
                                if (isInteger(arguments[iToRead + 2])) {
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
                                if (isInteger(arguments[iToRead + 2])) {
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
                                if (isInteger(arguments[iToRead + 2])) {
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
                                if (isInteger(arguments[iToRead + 2])) {
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
                                if (isInteger(arguments[iToRead + 2])) {
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
                                if (isInteger(arguments[iToRead + 2])) {
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
                                if (isInteger(arguments[iToRead + 2])) {
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
                                if (isInteger(arguments[iToRead + 2])) {
                                    teachers = new ArrayList<>();
                                    teachers.add(teacherDAO.get(Integer.parseInt(arguments[iToRead + 2])));
                                    if (teachers.size() > 0) {
                                        printTeacher(teachers);
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

    //=====================================PRINT=======================================

    private static void printCourses(ArrayList<Courses> courses) {
        System.out.printf("| %-20s | %-20s | %-20s | %-20s | %-20s |\n",
                "Id",
                "University",
                "Name",
                "ECTS",
                "Hours");
        for (Courses course : courses) {
            System.out.printf("| %-20s | %-20s | %-20s | %-20s | %-20s |\n",
                    course.getId(),
                    course.getUniversity(),
                    course.getName(),
                    course.getEcts(),
                    course.getHours());
        }
    }

    private static void printApplication(ArrayList<Application> applications) {
        System.out.printf("| %-20s | %-20s | %-20s | %-20s | %-20s | %-20s |\n",
                "Student Id",
                "Grant Id",
                "University",
                "Eval1 Id",
                "Eval2 Id",
                "Final Grade");

        for (Application application : applications) {
            System.out.printf("| %-20s | %-20s | %-20s | %-20s | %-20s | %-20s |\n",
                    application.getStudentId(),
                    application.getGrantId(),
                    application.getUniversity(),
                    application.getEval1Id(),
                    application.getEval2Id(),
                    application.getFinalGrade());
        }
    }

    private static void printEvaluation(ArrayList<Evaluation> evaluations) {
        System.out.printf("| %-20s | %-20s | %-20s |\n",
                "Evaluation Id",
                "Grade",
                "Teacher Id");

        for (Evaluation evaluation : evaluations) {
            System.out.printf("| %-20s | %-20s | %-20s |\n",
                    evaluation.getId(),
                    evaluation.getGrade(),
                    evaluation.getTeacherId());
        }
    }

    private static void printGrant(ArrayList<Grant> grants) {
        System.out.printf("| %-20s | %-20s | %-20s | %-20s |\n",
                "Grant Id",
                "Destination",
                "Total Seats",
                "Teacher Id");

        for (Grant grant : grants) {
            System.out.printf("| %-20s | %-20s | %-20s | %-20s |\n",
                    grant.getId(),
                    grant.getDestination(),
                    grant.getTotalSeats(),
                    grant.getTeacherId());
        }
    }

    private static void printStudent(ArrayList<Student> students) {
        System.out.printf("| %-20s | %-20s | %-20s | %-20s |\n",
                "Student Number",
                "Last Name",
                "First Name",
                "Average Grade");

        for (Student student : students) {
            System.out.printf("| %-20s | %-20s | %-20s | %-20s |\n",
                    student.getStudentNumber(),
                    student.getLastName(),
                    student.getFirstName(),
                    student.getAverageGrade());
        }
    }

    private static void printTeacher(ArrayList<Teacher> teachers) {
        System.out.printf("| %-20s | %-20s | %-20s |\n",
                "Student Number",
                "Last Name",
                "First Name");

        for (Teacher teacher : teachers) {
            System.out.printf("| %-20s | %-20s | %-20s |\n",
                    teacher.getId(),
                    teacher.getLastName(),
                    teacher.getFirstName());
        }
    }

    //=====================================DELETE======================================

    private static boolean delete(String[] arguments) {
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

    //=====================================UPDATE======================================

    private static boolean update(String[] arguments) {
        // TODO
        System.out.println("Not yet implemented");
        return false;
    }

    //=====================================ISTYPE=========================================

    private static boolean isInteger(String argument) {
        try {
            Integer.parseInt(argument);
            return true;
        } catch (NumberFormatException e) {
            System.err.println("The value '" + argument + "' is not an integer");
            return false;
        }
    }

    private static boolean isFloat(String argument) {
        try {
            Float.parseFloat(argument);
            return true;
        } catch (NumberFormatException e) {
            System.err.println("The value '" + argument + "' is not a float");
            return false;
        }
    }


}



