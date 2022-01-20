package fr.banalian.tpbdd.tpdao;

import fr.banalian.tpbdd.tpdao.dao.DAO;
import fr.banalian.tpbdd.tpdao.model.*;
import fr.banalian.tpbdd.tpdao.parser.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;


public class MainClass {

    public static void main(String[] args) {
        ConnectBdd.initConnection();

        menu();

        ConnectBdd.closeConnection();
    }


    public static void menu() {
        System.out.println("Welcome. type 'help' for help");
        String input;
        boolean stop = false;
        Scanner sc = new Scanner(System.in);
        do {
            input = sc.nextLine().toLowerCase();
            switch (input) {
                case "help" -> helpMenu("help", null);
                case "exit" -> stop = true;
                case "1", "2", "3", "4", "5" -> classMenu(Integer.parseInt(input), sc);
                default -> System.out.println("Unknown command, type 'help' for help");
            }
        } while (!stop);
    }

    public static void helpMenu(String help, Class currentClass) {

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
                System.out.println("\t- 4 - Scholarships");
                System.out.println("\t- 5- Courses");
                break;
            case "class":
                if (currentClass == null) {
                    System.err.println("No class selected");
                    break;
                }
                String name = currentClass.getSimpleName();
                System.out.println("Enter a number:");
                System.out.println("\t- 1 - Add one " + name);
                System.out.println("\t- 2 - Update one " + name);
                System.out.println("\t- 3 - Delete one " + name);
                System.out.println("\t- 4 - Get one or more " + name);
                System.out.println("Back : - Go back to the previous menu");

        }


    }


    public static void classMenu(int classNumber, Scanner sc) {
        Class currentClass = null;
        if(classNumber<1 || classNumber>5){
            System.err.println("Unknown class");
            return;
        }

        switch (classNumber) {
            case 1 -> currentClass = Application.class;
            case 2 -> currentClass = Student.class;
            case 3 -> currentClass = Teacher.class;
            case 4 -> currentClass = Courses.class;
            case 5 -> currentClass = Scholarship.class;
        }

        //TODO: show menu
        String input;
        boolean stop = false;
        do {
            helpMenu("class", currentClass);
            input = sc.nextLine().toLowerCase();
            switch (input) {
                case "help":
                    helpMenu("class", currentClass);
                case "1":
                    add(currentClass, sc);
                    break;
                case "2":
                    update(currentClass, sc);
                    break;
                case "3":
                    delete(currentClass, sc);
                    break;
                case "4":
                    get(currentClass, sc);
                    break;
                case "back":
                    stop = true;
                    break;
                default:
                    System.out.println("Unknown command, type 'help' for help");
            }
        }while (!stop);
    }

    public static void add(Class classToAdd, Scanner sc){
        switch (classToAdd.getSimpleName()) {
            case "Application" -> {
                //request the user to enter the data
                System.out.println("Enter the student's number :");
                String studentNumber = sc.nextLine();
                System.out.println("What is the destination/university of the application?");
                String destination = sc.nextLine();
                System.out.println("What is the name of the course?");
                String courseName = sc.nextLine();

                //create the application
                //call the method to add the application

                System.out.println("Application added, id : ");
            }
            case "Student" -> {
                //request the user to enter the data
                System.out.println("Enter the student's number :");
                String studentNumber = sc.nextLine();
                System.out.println("Enter the student's firstname :");
                String firstName = sc.nextLine();
                System.out.println("Enter the student's lastname :");
                String lastName = sc.nextLine();
                System.out.println("Enter the student's average grade :");
                float averageGrade = sc.nextFloat();

                //create the student
                //call the method to add the student
                StudentFunction.create(studentNumber, lastName, firstName, averageGrade);


            }

            case "Teacher" -> {
                //request the user to enter the data
                System.out.println("Enter the teacher's firstname :");
                String firstName = sc.nextLine();
                System.out.println("Enter the teacher's lastname :");
                String lastName = sc.nextLine();

                //create the teacher
                //call the method to add the teacher
                TeacherFunction.create(lastName, firstName);

            }

            case "Courses" -> {
                //request the user to enter the data
                System.out.println("Enter the course's name :");
                String courseName = sc.nextLine();
                System.out.println("Enter the course's description :");
                String description = sc.nextLine();
                System.out.println("Enter the course's total credit amount :");
                float ects = sc.nextFloat();
                System.out.println("Enter the course's total amount of hours :");
                int hours = sc.nextInt();
                System.out.println("Enter the course's University name :");
                String universityName = sc.nextLine();

                //create the course
                //call the method to add the course
            }

            case "Scholarship" -> {
                //request the user to enter the data
                System.out.println("Enter the scholarship's destination :");
                String destination = sc.nextLine();
                System.out.println("Enter the scholarship's total seat places :");
                int totalSeatPlaces = sc.nextInt();
                System.out.println("Who's the teacher of the scholarship? (firstname lastname)");
                String teacher = sc.nextLine();

                //create the scholarship
                //call the method to add the scholarship
            }

            default -> System.out.println("Unknown class, type 'help' for help");
        }
    }

    private static void update (Class classToUpdate, Scanner sc){
        switch (classToUpdate.getSimpleName()) {
            case "Application" -> {
                System.out.println("Do you want to update an application or an evaluation? (1 or 2)");
                int choice = sc.nextInt();
                if (choice == 1) {
                    System.out.println("Enter the student number of the application :");
                    String studentNumber = sc.nextLine();

                    //call the method to update the application
                } else if (choice == 2) {
                    System.out.println("Enter the student number of the application :");
                    String studentNumber = sc.nextLine();
                    System.out.println("Which evaluation do you want to update? (1 or 2)");
                    int evaluationChoice = sc.nextInt();
                    System.out.println("Enter the new grade :");
                    double newGrade = sc.nextDouble();
                    System.out.println("Enter the new teacher:");
                    String newTeacher = sc.nextLine();

                    //call the method to update the evaluation


                } else {
                    System.out.println("Unknown choice, type 'help' for help");
                }
            }

            case "Student" -> {
                System.out.println("Who's the student you want to update?");
                String studentNumber = sc.nextLine();

                //call the method to update the student

            }

            case "Teacher" -> {
                System.out.println("Who's the teacher you want to update?");

                //call the method to update the teacher
                TeacherFunction.update(sc);
            }

            case "Courses" -> {
                System.out.println("You cannot modify a course, please delete one an recreate it");
            }

            case "Scholarship" -> {
                System.out.println("Which scholarship do you want to update?");

            }

            default -> System.out.println("Unknown class, type 'help' for help");
        }
    }

    private static void delete(Class classToDelete, Scanner sc){
        switch (classToDelete.getSimpleName()) {
            case "Application" -> {
                System.out.println("Enter the student number of the application :");
                String studentNumber = sc.nextLine();

                //call the method to delete the application
            }

            case "Student" -> {
                System.out.println("Who's the student you want to delete?");
                //call the method to delete the student
                StudentFunction.delete(sc);
            }

            case "Teacher" -> {
                System.out.println("Who's the teacher you want to delete?");
                //call the method to delete the teacher
                TeacherFunction.delete(sc);
            }

            case "Courses" -> {
                System.out.println("Which course do you want to delete?");
                CoursesFunction.delete(sc);

                //call the method to delete the course
            }

            case "Scholarship" -> {
                System.out.println("Which scholarship do you want to delete?");
                String scholarshipName = sc.nextLine();

                //call the method to delete the scholarship
            }

            default -> System.out.println("Unknown class, type 'help' for help");
        }
    }

    private static void get(Class classToGet, Scanner sc){
        switch (classToGet.getSimpleName()) {
            case "Application" -> {
                System.out.println("Enter the student number of the application :");
                String studentNumber = sc.nextLine();

                //call the method to get the application
            }

            case "Student" -> {
                System.out.println("Do you want to see all the students or a specific one? (1 or 2)");
                int choice = sc.nextInt();
                if (choice == 1) {
                    StudentFunction.seeAll();
                } else if (choice == 2) {
                    System.out.println("Do you want to check by student number, by last name or by first name only, or by both names? (1 or 2 or 3 or 4)");
                    int choice2 = sc.nextInt();
                    if (choice2 == 1) {
                        System.out.println("Enter the student number :");
                        String studentNumber = sc.nextLine();
                        StudentFunction.seeOne(1,new String[]{studentNumber});
                    }else if (choice2 == 2) {
                        System.out.println("Enter the last name :");
                        String lastName = sc.nextLine();
                        StudentFunction.seeOne(2,new String[]{lastName});
                    }else if (choice2 == 3) {
                        System.out.println("Enter the first name :");
                        String firstName = sc.nextLine();
                        StudentFunction.seeOne(3,new String[]{firstName});
                    }else if (choice2 == 4) {
                        System.out.println("Enter the first name :");
                        String firstName = sc.nextLine();
                        System.out.println("Enter the last name :");
                        String lastName = sc.nextLine();
                        StudentFunction.seeOne(4,new String[]{lastName,firstName});

                    }else {
                        System.out.println("Unknown choice, type 'help' for help");
                    }
                }
            }

            case "Teacher" -> {
                System.out.println("Do you want to see all the teachers or a specific one? (1 or 2)");
                int choice = sc.nextInt();
                if (choice == 1) {
                    TeacherFunction.seeAll();
                }else if (choice == 2) {
                    System.out.println("Do you want to check by last name or by first name only, or by both names? (1 or 2 or 3)");
                    int choice2 = sc.nextInt();
                    if (choice2 == 1) {
                        System.out.println("Enter the last name :");
                        String lastName = sc.nextLine();
                        TeacherFunction.seeOne(1,new String[]{lastName});
                    } else if (choice2 == 2) {
                        System.out.println("Enter the first name :");
                        String firstName = sc.nextLine();
                        TeacherFunction.seeOne(2,new String[]{firstName});
                    }else if (choice2 == 3) {
                        System.out.println("Enter the first name :");
                        String firstName = sc.nextLine();
                        System.out.println("Enter the last name :");
                        String lastName = sc.nextLine();
                        TeacherFunction.seeOne(3,new String[]{lastName,firstName});
                    }
                }else {
                    System.out.println("Unknown choice, type 'help' for help");
                }


            }

            case "Courses" -> {
                System.out.println("Do you want to see all the courses or a specific one? (1 or 2)");
                int choice = sc.nextInt();
                if (choice == 1) {
                    CoursesFunction.seeAll();
                }else if (choice == 2) {
                    System.out.println("Do you want to check by university name, by hours, by ects or by course name ? (1 or 2 or 3 or 4)");
                    int choice2 = sc.nextInt();
                    switch (choice2) {
                        case 1 -> {
                            System.out.println("Enter the university name :");
                            String universityName = sc.nextLine();
                            CoursesFunction.seeOne(2, new String[]{universityName});
                        }
                        case 2 -> {
                            System.out.println("Enter the total amount of hours :");
                            int totalHours = sc.nextInt();
                            CoursesFunction.seeOne(3, new String[]{String.valueOf(totalHours)});
                        }
                        case 3 -> {
                            System.out.println("Enter the ects :");
                            int ects = sc.nextInt();
                            CoursesFunction.seeOne(1, new String[]{String.valueOf(ects)});
                        }
                        case 4 -> {
                            System.out.println("Enter the course name :");
                            String courseName = sc.nextLine();
                            CoursesFunction.seeOne(4, new String[]{courseName});
                        }
                    }
                }else {
                    System.out.println("Unknown choice, type 'help' for help");
                }
            }

            case "Scholarship" -> {
                System.out.println("Which scholarship do you want to get?");
                String scholarshipName = sc.nextLine();

                //call the method to get the scholarship
            }

            default -> System.out.println("Unknown class, type 'help' for help");
        }
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



