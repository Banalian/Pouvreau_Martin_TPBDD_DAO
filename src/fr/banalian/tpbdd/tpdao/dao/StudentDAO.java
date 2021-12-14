package fr.banalian.tpbdd.tpdao.dao;

import fr.banalian.tpbdd.tpdao.ConnectBdd;
import fr.banalian.tpbdd.tpdao.model.Student;
import fr.banalian.tpbdd.tpdao.model.Teacher;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class StudentDAO implements DAO<Student>{
    @Override
    public ArrayList<Student> getAll() {
        ArrayList<Student> student = new ArrayList<>();
        Statement stmt = ConnectBdd.getNewStatement();
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery("SELECT * FROM student");
            student = iterateThroughResultSet(rs);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return student;
    }

    public Student get(String id) {
        Statement stmt = ConnectBdd.getNewStatement();
        Student student = null;
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery("SELECT * FROM student WHERE studentnumber = "+ id +";");
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

    @Override
    public boolean delete(String id) {
        Statement stmt = ConnectBdd.getNewStatement();
        try {
            stmt.executeUpdate("DELETE FROM student WHERE studentnumber="+id+";");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    @Override
    public boolean add(Student student) {
        Statement stmt = ConnectBdd.getNewStatement();
        try {
            stmt.executeUpdate("INSERT INTO student (studentnumber, lastname, firstname, averagegrade) VALUES ('"+student.getStudentNumber()+"', '"+student.getLastName()+"', '"+student.getFirstName()+"', "+student.getAverageGrade()+";");
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private ArrayList<Student> iterateThroughResultSet(ResultSet rs) throws SQLException {
        ArrayList<Student> students = new ArrayList<Student>();
        while(rs.next()) {
            String id = rs.getString("studentid");
            String lastName = rs.getString("lastname");
            String firstName = rs.getString("firstname");
            int averageGrade = rs.getInt("averagegrade");

            Student student = new Student(id, lastName, firstName, averageGrade);
            students.add(student);
        }
        return students;
    }
}
