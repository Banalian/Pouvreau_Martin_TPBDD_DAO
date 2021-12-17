package fr.banalian.tpbdd.tpdao.dao;

import fr.banalian.tpbdd.tpdao.ConnectBdd;
import fr.banalian.tpbdd.tpdao.model.Grant;
import fr.banalian.tpbdd.tpdao.model.Student;
import fr.banalian.tpbdd.tpdao.model.Teacher;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * DAO for Students. This class is used to access the database.
 */
public class StudentDAO implements DAO<Student>{
    @Override
    public ArrayList<Student> getAll() {
        ArrayList<Student> student = new ArrayList<>();
        Statement stmt = ConnectBdd.getNewStatement();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM student");
            student = iterateThroughResultSet(rs);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return student;
    }

    /**
     * get a connection to the database from connectBdd and delete all entry from the table
     * @return true if executed, false if errors found
     */
    public boolean deleteAll() {
        Statement stmt = ConnectBdd.getNewStatement();
        String query = "DELETE FROM student";
        try {
            stmt.executeUpdate(query);
            return true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public Student get(String id) {
        Statement stmt = ConnectBdd.getNewStatement();
        Student student = null;
        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM student WHERE studentnumber = '"+ id +"'");
            student = iterateThroughResultSet(rs).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }

    @Override
    public boolean update(Student student) {
        return false;
    }


    /**
     * This method should not be used. You should use the delete(String id) method instead.
     * @param id the id of the <T> to delete
     * @return true if executed, false if errors found
     */
    @Override
    public boolean delete(int id) {
        return delete(String.valueOf(id));
    }

    public boolean delete(String id) {
        Statement stmt = ConnectBdd.getNewStatement();
        int resAffected;
        try {
            resAffected = stmt.executeUpdate("DELETE FROM student WHERE studentnumber='"+id+"'");
            return resAffected != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean add(Student student) {
        Statement stmt = ConnectBdd.getNewStatement();
        try {
            stmt.executeUpdate("INSERT INTO student (studentnumber, lastname, firstname, averagegrade) VALUES ('"+student.getStudentNumber()+"', '"+student.getLastName()+"', '"+student.getFirstName()+"', "+student.getAverageGrade()+")", Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private ArrayList<Student> iterateThroughResultSet(ResultSet rs) throws SQLException {
        ArrayList<Student> students = new ArrayList<>();
        while(rs.next()) {
            String id = rs.getString("studentnumber");
            String lastName = rs.getString("lastname");
            String firstName = rs.getString("firstname");
            float averageGrade = rs.getFloat("averagegrade");

            Student student = new Student(lastName, firstName, id, averageGrade);
            students.add(student);
        }
        return students;
    }

    /**
     * Get the student corresponding to the id
     * @param id the id to search for
     * @return the student correspondingt to the id
     */
    public Student getById(String id) {
        String query = "SELECT * FROM student WHERE studentnumber ='"+ id+"'";
        Statement stmt = ConnectBdd.getNewStatement();
        ResultSet rs;
        try {
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                rs.beforeFirst();
                return iterateThroughResultSet(rs).get(0);
            }else{
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
