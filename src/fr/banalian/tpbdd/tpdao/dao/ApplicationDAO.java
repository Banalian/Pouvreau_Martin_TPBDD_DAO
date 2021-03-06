package fr.banalian.tpbdd.tpdao.dao;

import fr.banalian.tpbdd.tpdao.ConnectBdd;
import fr.banalian.tpbdd.tpdao.model.Application;
import fr.banalian.tpbdd.tpdao.model.Courses;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * DAO for Applications. This class is used to access the database.
 */
public class ApplicationDAO implements DAO<Application> {

    /**
     * get a connection to the database from connectBdd and make a query to get all the teacher
     * @return ArrayList<Application> of all applications
     */
    @Override
    public ArrayList<Application> getAll() {
        ArrayList<Application> applications = new ArrayList<>();
        Statement stmt = ConnectBdd.getNewStatement();
        ResultSet rs;
        String query = "SELECT * FROM application";
        try {
            rs = stmt.executeQuery(query);
            applications = iterateThroughResultSet(rs);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return applications;
    }

    /**
     * get a connection to the database from connectBdd and delete all entry from the table
     * @return true if executed, false if errors found
     */
    public boolean deleteAll() {
        Statement stmt = ConnectBdd.getNewStatement();
        String query = "DELETE FROM application";
        try {
            stmt.executeUpdate(query);
            return true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }



    /**
     * get all the applications of a specific student and grant
     * @param id the id of the student
     * @param grantId the id of the grant
     * @return ArrayList<Application> of all applications of the student
     */
    public ArrayList<Application> getByStudentIdGrantId(String id, int grantId) {
        ArrayList<Application> apps = new ArrayList<>();
        Statement stmt = ConnectBdd.getNewStatement();
        ResultSet rs;
        String query = "SELECT * FROM application WHERE studentid = '" + id + "' AND `grant` = " + grantId;
        try {
            rs = stmt.executeQuery(query);
            apps = iterateThroughResultSet(rs);
        }catch (SQLException e){
            e.printStackTrace();
        }

        return apps;
    }

    /**
     * update an application in the database
     * @param application the application to update
     * @return true if the application has been updated, false otherwise
     */
    @Override
    public boolean update(Application application) {
        //update the bdd application with the new values
        Statement stmt = ConnectBdd.getNewStatement();
        String query =
                "UPDATE application SET studentid = '" + application.getStudentId() +
                "', `grant` = " + application.getGrantId() +
                ", university = '" + application.getUniversity() +
                "', evaluation1 = " + application.getEval1Id() +
                ", evaluation2 = " + application.getEval2Id() +
                ", finalgrade = " + application.getFinalGrade() +
                " WHERE studentid = '" + application.getStudentId()+ "' AND `grant` = " + application.getGrantId();
        try {
            stmt.executeUpdate(query);
            return true;
        }catch (SQLException e){
            e.printStackTrace();
        }

        return false;

    }

    /**
     * Delete an application from the database
     * WARNING: this method will throw an exception.
     * @param id the id of the student
     * @return true if the application has been deleted, false otherwise
     * @throws UnsupportedOperationException every time because this method is not supported
     */
    @Override
    public boolean delete(int id) throws UnsupportedOperationException{
        throw new UnsupportedOperationException("Not supported. Use deleteStudentApplication instead.");
    }


    /**
     * Delete an application from the database
     * @param studentId the id of the student
     * @param grantId the id of the grant associated to the application
     * @return true if the application has been deleted, false otherwise
     */
    public boolean deleteStudentApplication(String studentId, int grantId) {
        Statement stmt = ConnectBdd.getNewStatement();
        int resAffected;
        String query = "DELETE FROM application WHERE studentid = '" + studentId + "' AND `grant` = " + grantId;
        try {
            resAffected = stmt.executeUpdate(query);
            return resAffected != 0;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }


    /**
     * Add a new application in the database
     * @param application the application to add
     * @return true if the application has been added, false otherwise
     */
    @Override
    public boolean add(Application application) {
        Statement stmt = ConnectBdd.getNewStatement();
        String query =
                "INSERT INTO application (studentid, `grant`, university, evaluation1, evaluation2, finalgrade) " +
                "VALUES (" + application.getStudentId() + ", " +
                        application.getGrantId() + ", '" +
                        application.getUniversity() + "', " +
                        application.getEval1Id() + ", " +
                        application.getEval2Id() + ", " +
                        application.getFinalGrade() +
                        ")";

        try {
            stmt.executeUpdate(query);
            updateEvaluations(application);
            return true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }


    /**
     * get all the applications having a given grant
     * @param id the id of the grant
     * @return ArrayList<Application> of all applications
     */
    public ArrayList<Application> getAllByGrantId(int id) {
        ArrayList<Application> apps = new ArrayList<>();
        Statement stmt = ConnectBdd.getNewStatement();
        ResultSet rs;
        String query = "SELECT * FROM application WHERE `grant` = " + id;
        try {
            rs = stmt.executeQuery(query);
            apps = iterateThroughResultSet(rs);
        }catch (SQLException e){
            e.printStackTrace();
        }

        return apps;
    }


    /**
     * get all the applications having a given student id
     * @param id the id of the student
     * @return ArrayList<Application> of all applications
     */
    public ArrayList<Application> getAllByStudentId(String id) {
        ArrayList<Application> apps = new ArrayList<>();
        Statement stmt = ConnectBdd.getNewStatement();
        ResultSet rs;
        String query = "SELECT * FROM application WHERE studentid = '" + id + "'";
        try {
            rs = stmt.executeQuery(query);
            apps = iterateThroughResultSet(rs);
        }catch (SQLException e){
            e.printStackTrace();
        }

        return apps;
    }

    /**
     * update an application of a student with the new values
     * will update the value of the final grade based on the evaluation1 and evaluation2, getting them from the bdd
     * @param application the application to update
     * @return true if the update is successful, false otherwise
     */
    public boolean updateEvaluations(Application application){
        Statement stmt = ConnectBdd.getNewStatement();

        Statement stmtEval1 = ConnectBdd.getNewStatement();
        Statement stmtEval2 = ConnectBdd.getNewStatement();

        Statement stmtStudent = ConnectBdd.getNewStatement();




        String queryEval1 = "SELECT * FROM evaluation WHERE id = " + application.getEval1Id();
        String queryEval2 = "SELECT * FROM evaluation WHERE id = " + application.getEval2Id();
        ResultSet rsEval1, rsEval2;
        int finalGrade = 0;
        try {
            rsEval1 = stmtEval1.executeQuery(queryEval1);
            rsEval2 = stmtEval2.executeQuery(queryEval2);
            if(rsEval1.next() && rsEval2.next()){
                int eval1 = rsEval1.getInt("grade");
                int eval2 = rsEval2.getInt("grade");
                finalGrade = (eval1 + eval2);
            }
        }catch (SQLException e){
            System.err.println("Error while selecting the student's evaluations in the updateEvaluations method");
            e.printStackTrace();
            return false;
        }

        String queryStudent = "SELECT * FROM student WHERE studentnumber = '" + application.getStudentId()+"'";
        ResultSet rsStudent;
        try {
            rsStudent = stmtStudent.executeQuery(queryStudent);
            if(rsStudent.next()){
                int studentGrade = rsStudent.getInt("averagegrade");
                finalGrade = (finalGrade + studentGrade)/3;
                application.setFinalGrade(finalGrade);
            }
        }catch (SQLException e){
            System.err.println("Error while selecting the student grant in the updateEvaluations method");
            e.printStackTrace();
            return false;
        }


        String query =
                "UPDATE application SET evaluation1 = " + application.getEval1Id() +
                        ", evaluation2 = " + application.getEval2Id() +
                        ", finalgrade = " + application.getFinalGrade() +
                        " WHERE studentid = '" + application.getStudentId() +
                        "' AND `grant` = " + application.getGrantId();

        try {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.err.println("Error while updating the application in the updateEvaluations method");
            e.printStackTrace();
            return false;
        }
        return true;
    }



    /**
     * Iterate through the result set and create an ArrayList of Application
     * @param rs the result set
     * @return ArrayList of Application
     * @throws SQLException if an error occurs
     */
    private ArrayList<Application> iterateThroughResultSet(ResultSet rs) throws SQLException {
        ArrayList<Application> applications = new ArrayList<>();
        while(rs.next()) {
            String studentId = rs.getString("studentid");
            int grant = rs.getInt("grant");
            String university = rs.getString("university");
            int eval1 = rs.getInt("evaluation1");
            int eval2 = rs.getInt("evaluation2");
            float finalGrade = rs.getFloat("finalgrade");

            applications.add(new Application(studentId, grant, university, eval1, eval2, finalGrade));
        }
        return applications;
    }

    /**
     * Get all the application for a precise grant
     * @param id the grant's id
     * @return an ArrayList of all the application for this grant
     */
    public ArrayList<Application> getByGrantId(int id) {
        String query = "SELECT * FROM application WHERE `grant` ="+ id;
        Statement stmt = ConnectBdd.getNewStatement();
        ResultSet rs;
        try {
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                rs.beforeFirst();
                return iterateThroughResultSet(rs);
            }else{
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get all the application where the final grade of the student is superior to a certain grade
     * @param grade the grade the final grade has to be superior to
     * @return an ArrayList of all the application where the condition is fulfilled
     */
    public ArrayList<Application> getBySupFinalGrade(float grade) {
        String query = "SELECT * FROM application WHERE finalgrade >="+ grade;
        Statement stmt = ConnectBdd.getNewStatement();
        ResultSet rs;
        try {
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                rs.beforeFirst();
                return iterateThroughResultSet(rs);
            }else{
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get all the application for a certain university
     * @param university the university the application is for
     * @return an ArrayList of all the application where the condition is fulfilled
     */
    public ArrayList<Application> getByUniversity(String university) {
        String query = "SELECT * FROM application WHERE university ='"+university+"'";
        Statement stmt = ConnectBdd.getNewStatement();
        ResultSet rs;
        try {
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                rs.beforeFirst();
                return iterateThroughResultSet(rs);
            }else{
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
