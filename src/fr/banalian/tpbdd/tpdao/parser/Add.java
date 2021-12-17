package fr.banalian.tpbdd.tpdao.parser;
import fr.banalian.tpbdd.tpdao.dao.*;
import fr.banalian.tpbdd.tpdao.model.*;

public class Add {

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

}
