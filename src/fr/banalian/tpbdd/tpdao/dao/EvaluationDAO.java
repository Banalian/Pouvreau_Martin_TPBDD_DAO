package fr.banalian.tpbdd.tpdao.dao;

import fr.banalian.tpbdd.tpdao.ConnectBdd;
import fr.banalian.tpbdd.tpdao.model.Evaluation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * DAO for Evaluations. This class is used to access the database.
 */
public class EvaluationDAO implements DAO<Evaluation>{
    /**
     * Get all the evaluations entries
     * @return ArrayList of evaluations
     */
    @Override
    public ArrayList<Evaluation> getAll() {
        String query = "SELECT * FROM evaluation";
        Statement stmt = ConnectBdd.getNewStatement();
        ResultSet rs;
        try {
            rs = stmt.executeQuery(query);
            return iterateThroughResultSet(rs);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * get a connection to the database from connectBdd and delete all entry from the table
     * @return true if executed, false if errors found
     */
    public boolean deleteAll() {
        Statement stmt = ConnectBdd.getNewStatement();
        String query = "DELETE FROM evaluation";
        try {
            stmt.executeUpdate(query);
            return true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * update an evaluation
     * @param evaluation the evaluation to update
     * @return true if the update is successful, false otherwise
     */
    @Override
    public boolean update(Evaluation evaluation) {
        String query = "UPDATE evaluation SET grade = " + evaluation.getGrade() + ", teacher="+ evaluation.getTeacherId() + " WHERE id = " + evaluation.getId();
        Statement stmt = ConnectBdd.getNewStatement();
        try {
            stmt.executeUpdate(query);
            return true;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Delete an evaluation
     * @param id the id of the evaluation to delete
     * @return true if the delete is successful, false otherwise
     */
    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM evaluation WHERE id = " + id;
        Statement stmt = ConnectBdd.getNewStatement();
        try {
            stmt.executeUpdate(query);
            return true;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * Add an evaluation to the database
     * @param evaluation the evaluation to add
     * @return true if the add is successful, false otherwise
     */
    @Override
    public boolean add(Evaluation evaluation) {
        String query = "INSERT INTO evaluation (grade, teacher) VALUES (" + evaluation.getGrade() + ", " + evaluation.getTeacherId() + ")";
        Statement stmt = ConnectBdd.getNewStatement();
        try {
            stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                evaluation.setId(generatedKeys.getInt(1));
            }else {
                throw new SQLException("Creating evaluation failed, no ID obtained.");
            }
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Get all the evaluations from a teacher
     * @param teacherId the id of the teacher
     * @return ArrayList of evaluations
     */
    public ArrayList<Evaluation> getAllByTeacher(int teacherId) {
        String query = "SELECT * FROM evaluation WHERE teacher = " + teacherId;
        Statement stmt = ConnectBdd.getNewStatement();
        ResultSet rs;
        try {
            rs = stmt.executeQuery(query);
            return iterateThroughResultSet(rs);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get a specific evaluation
     * @param id the id of the evaluation
     * @return the evaluation
     */
    public Evaluation getEvaluationById(int id) {
        String query = "SELECT * FROM evaluation WHERE id = " + id;
        Statement stmt = ConnectBdd.getNewStatement();
        ResultSet rs;
        try {
            rs = stmt.executeQuery(query);
            return iterateThroughResultSet(rs).get(0);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Update the grade of an evaluation
     * @param id the id of the evaluation
     * @param grade the new grade
     * @return true if the update is successful, false otherwise
     */
    public boolean updateGrade(int id, int grade) {
        String query = "UPDATE evaluation SET grade = " + grade + " WHERE id = " + id;
        Statement stmt = ConnectBdd.getNewStatement();
        try {
            stmt.executeUpdate(query);
            return true;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * Iterate through the result set and create an ArrayList of evaluations
     * @param rs the result set
     * @return ArrayList of evaluations
     * @throws SQLException if an error occurs
     */
    private ArrayList<Evaluation> iterateThroughResultSet(ResultSet rs) throws SQLException {
        ArrayList<Evaluation> evaluations = new ArrayList<>();
        while(rs.next()) {
            evaluations.add(new Evaluation(
                    rs.getInt("id"),
                    rs.getFloat("grade"),
                    rs.getInt("teacher")));
        }
        return evaluations;
    }
}
