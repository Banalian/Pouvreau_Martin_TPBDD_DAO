package fr.banalian.tpbdd.tpdao.dao;

import fr.banalian.tpbdd.tpdao.ConnectBdd;
import fr.banalian.tpbdd.tpdao.model.Application;

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
     * get all the applications of a student
     * @param id the id of the student
     * @return ArrayList<Application> of all applications of the student
     */
    public ArrayList<Application> getAllbyStudentId(String id) {
        ArrayList<Application> apps = new ArrayList<>();
        Statement stmt = ConnectBdd.getNewStatement();
        ResultSet rs;
        String query = "SELECT * FROM application WHERE studentid = " + id;
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
                "UPDATE application SET studentid = " + application.getStudentId() +
                ", grant = " + application.getGrantId() +
                ", university = '" + application.getUniversity() +
                "', evaluation1 = " + application.getEval1Id() +
                ", evaluation2 = " + application.getEval2Id() +
                ", finalgrade = " + application.getFinalGrade() +
                " WHERE studentid = " + application.getStudentId();
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
     * @param id the id of the application to delete
     * @return true if the application has been deleted, false otherwise
     */
    @Override
    public boolean delete(int id) {
        Statement stmt = ConnectBdd.getNewStatement();
        String query = "DELETE FROM application WHERE studentid = " + id;
        try {
            stmt.executeUpdate(query);
            return true;
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
                "INSERT INTO application (studentid, grant, university, evaluation1, evaluation2, finalgrade) " +
                "VALUES (" + application.getStudentId() + ", " +
                        application.getGrantId() + ", '" +
                        application.getUniversity() + "', " +
                        application.getEval1Id() + ", " +
                        application.getEval2Id() + ", " +
                        application.getFinalGrade() +
                        ")";

        try {
            stmt.executeUpdate(query);
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
        String query = "SELECT * FROM application WHERE grant = " + id;
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


        String queryEval1 = "SELECT * FROM evaluation WHERE id = " + application.getEval1Id();
        String queryEval2 = "SELECT * FROM evaluation WHERE id = " + application.getEval2Id();
        ResultSet rsEval1, rsEval2;
        int finalGrade = 0;
        try {
            rsEval1 = stmt.executeQuery(queryEval1);
            rsEval2 = stmt.executeQuery(queryEval2);
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

        String queryStudent = "SELECT * FROM student WHERE id = " + application.getStudentId() + " AND grant = " + application.getGrantId();
        ResultSet rsStudent;
        try {
            rsStudent = stmt.executeQuery(queryStudent);
            if(rsStudent.next()){
                int studentGrade = rsStudent.getInt("grade");
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
                        " WHERE studentid = " + application.getStudentId() +
                        " AND grant = " + application.getGrantId();

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

}
