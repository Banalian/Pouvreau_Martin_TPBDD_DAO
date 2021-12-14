package fr.banalian.tpbdd.tpdao.dao;

import fr.banalian.tpbdd.tpdao.ConnectBdd;
import fr.banalian.tpbdd.tpdao.model.Application;
import fr.banalian.tpbdd.tpdao.model.Courses;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CoursesDAO implements DAO<Courses> {
    @Override
    public ArrayList<Courses> getAll() {
        ArrayList<Courses> courses = new ArrayList<>();
        String query = "SELECT * FROM courses";
        Statement stmt = ConnectBdd.getNewStatement();
        ResultSet rs;
        try {
            rs = stmt.executeQuery(query);
            courses = iterateThroughResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    @Override
    public boolean update(Courses courses) {
        String query = "UPDATE courses SET name = '" + courses.getName() +
                "', ects = " + courses.getEcts() +
                ", hours = " + courses.getHours() +
                ", university = '" + courses.getUniversity() +
                "' WHERE id = " + courses.getId();
        Statement stmt = ConnectBdd.getNewStatement();
        try {
            stmt.executeUpdate(query);
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM courses WHERE id = " + id;
        Statement stmt = ConnectBdd.getNewStatement();
        try {
            stmt.executeUpdate(query);
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean add(Courses courses) {
        String query = "INSERT INTO courses (name, ects, hours, university) VALUES ('" +
                courses.getName() + "', " +
                courses.getEcts() + ", " +
                courses.getHours() + ", '" +
                courses.getUniversity() + "')";
        Statement stmt = ConnectBdd.getNewStatement();
        try {
            stmt.executeUpdate(query);
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                courses.setId(generatedKeys.getInt(1));
            }else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Courses getByCourseId(int id) {
        String query = "SELECT * FROM courses WHERE id = " + id;
        Statement stmt = ConnectBdd.getNewStatement();
        ResultSet rs;
        try {
            rs = stmt.executeQuery(query);
           return iterateThroughResultSet(rs).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Courses> getByApplicationId(String id, int grant) {
        String query = "SELECT * FROM courses WHERE university IN (SELECT university FROM application WHERE studentid= " + id + " AND grant = " + grant + ")";
        Statement stmt = ConnectBdd.getNewStatement();
        ResultSet rs;
        try {
            rs = stmt.executeQuery(query);
            return iterateThroughResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Courses> getByUniversity(String university) {
        String query = "SELECT * FROM courses WHERE university = '" + university + "'";
        Statement stmt = ConnectBdd.getNewStatement();
        ResultSet rs;
        try {
            rs = stmt.executeQuery(query);
            return iterateThroughResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    private ArrayList<Courses> iterateThroughResultSet(ResultSet rs) throws SQLException {
        ArrayList<Courses> courses = new ArrayList<>();
        while(rs.next()) {
            courses.add(new Courses(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("ects"),
                    rs.getFloat("hours"),
                    rs.getString("university")));

        }
        return courses;
    }
}
