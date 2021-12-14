package fr.banalian.tpbdd.tpdao.dao;

import fr.banalian.tpbdd.tpdao.ConnectBdd;
import fr.banalian.tpbdd.tpdao.model.Evaluation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class EvaluationDAO implements DAO<Evaluation>{
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

    @Override
    public boolean add(Evaluation evaluation) {
        String query = "INSERT INTO evaluation (grade, teacher) VALUES (" + evaluation.getGrade() + ", " + evaluation.getTeacherId() + ")";
        Statement stmt = ConnectBdd.getNewStatement();
        try {
            stmt.executeUpdate(query);
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                evaluation.setId(generatedKeys.getInt(1));
            }else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

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

    public Evaluation getByEvaluationId(int id) {
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
