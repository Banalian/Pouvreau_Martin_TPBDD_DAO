package fr.banalian.tpbdd.tpdao.dao;

import fr.banalian.tpbdd.tpdao.ConnectBdd;
import fr.banalian.tpbdd.tpdao.model.Courses;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * DAO for Courses. This class is used to access the database.
 */
public class CoursesDAO implements DAO<Courses> {

    /**
     * Get all the courses
     * @return ArrayList<Courses> of all the courses
     */
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

    /**
     * get a connection to the database from connectBdd and delete all entry from the table
     * @return true if executed, false if errors found
     */
    public boolean deleteAll() {
        Statement stmt = ConnectBdd.getNewStatement();
        String query = "DELETE FROM courses";
        try {
            stmt.executeUpdate(query);
            return true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Update a course
     * @param course the course to update
     * @return true if the update is successful, false otherwise
     */
    @Override
    public boolean update(Courses course) {
        String query = "UPDATE courses SET name = '" + course.getName() +
                "', ects = " + course.getEcts() +
                ", hours = " + course.getHours() +
                ", university = '" + course.getUniversity() +
                "' WHERE id = " + course.getId();
        Statement stmt = ConnectBdd.getNewStatement();
        try {
            stmt.executeUpdate(query);
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Delete a course
     * @param id id of the course to delete
     * @return true if the delete is successful, false otherwise
     */
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

    /**
     * Add a course to the database
     * @param course the course to create
     * @return true if the creation is successful, false otherwise
     */
    @Override
    public boolean add(Courses course) {
        String query = "INSERT INTO courses (name, ects, hours, university) VALUES ('" +
                course.getName() + "', " +
                course.getEcts() + ", " +
                course.getHours() + ", '" +
                course.getUniversity() + "')";
        Statement stmt = ConnectBdd.getNewStatement();
        try {
            stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                course.setId(generatedKeys.getInt(1));
            }else {
                throw new SQLException("Creating course failed, no ID obtained.");
            }
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Get a course by its id
     * @param id id of the course to get
     * @return the course if it exists, null otherwise
     */
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

    /**
     * get all the courses of a university from a student's application
     * @param id the student's number
     * @param grant the id of the grant
     * @return an ArrayList of all the courses of the university
     */
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

    /**
     * Get all the courses of a university
     * @param university the university's name
     * @return an ArrayList of all the courses of the university
     */
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


    /**
     * Iterate through the result set and create an ArrayList of Courses
     * @param rs the result set
     * @return ArrayList of Courses
     * @throws SQLException if an error occurs
     */
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
