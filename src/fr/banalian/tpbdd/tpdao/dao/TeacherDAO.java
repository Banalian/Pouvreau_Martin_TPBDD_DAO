package fr.banalian.tpbdd.tpdao.dao;

import fr.banalian.tpbdd.tpdao.ConnectBdd;
import fr.banalian.tpbdd.tpdao.model.Student;
import fr.banalian.tpbdd.tpdao.model.Teacher;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * DAO for Teachers. This class is used to access the database.
 */
public class TeacherDAO implements DAO<Teacher> {

    /**
     * get a connection to the database from connectBdd and make a query to get all the teacher
     */
    @Override
    public ArrayList<Teacher> getAll() {
        ArrayList<Teacher> teacher = new ArrayList<>();
        Statement stmt = ConnectBdd.getNewStatement();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM teacher");
            teacher = iterateThroughResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teacher;
    }

    /**
     * get a connection to the database from connectBdd and delete all entry from the table
     *
     * @return true if executed, false if errors found
     */
    public boolean deleteAll() {
        Statement stmt = ConnectBdd.getNewStatement();
        String query = "DELETE FROM teacher";
        try {
            stmt.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Teacher get(int id) {
        Statement stmt = ConnectBdd.getNewStatement();
        Teacher teacher = null;
        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM teacher WHERE id = " + id + ";");
            teacher = iterateThroughResultSet(rs).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teacher;
    }

    @Override
    public boolean update(Teacher teacher) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        Statement stmt = ConnectBdd.getNewStatement();
        try {
            stmt.executeUpdate("DELETE FROM teacher WHERE id=" + id + ";");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean add(Teacher teacher) {
        Statement stmt = ConnectBdd.getNewStatement();
        try {
            stmt.executeUpdate("INSERT INTO teacher (firstname, lastname) VALUES ('" + teacher.getFirstName() + "', '" + teacher.getLastName() + "');", Statement.RETURN_GENERATED_KEYS);
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                teacher.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Creating teacher failed, no ID obtained.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private ArrayList<Teacher> iterateThroughResultSet(ResultSet rs) throws SQLException {
        ArrayList<Teacher> teachers = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String lastName = rs.getString("lastname");
            String firstName = rs.getString("firstname");

            Teacher teacher = new Teacher(id, lastName, firstName);
            teachers.add(teacher);
        }
        return teachers;
    }

}