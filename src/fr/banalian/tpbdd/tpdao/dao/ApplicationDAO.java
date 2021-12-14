package fr.banalian.tpbdd.tpdao.dao;

import fr.banalian.tpbdd.tpdao.ConnectBdd;
import fr.banalian.tpbdd.tpdao.model.Application;
import fr.banalian.tpbdd.tpdao.model.Teacher;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ApplicationDAO implements DAO<Application> {

    /**
     * get a connection to the database from connectBdd and make a query to get all the teacher
     * @return ArrayList<Application> of all applications
     */
    @Override
    public ArrayList<Application> getAll() {
        ArrayList<Application> applications = new ArrayList<>();
        Statement stmt = ConnectBdd.getNewStatement();
        ResultSet rs = null;
        String query = "SELECT * FROM application";
        try {
            rs = stmt.executeQuery(query);
            applications = iterateThroughResultSet(rs);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return applications;
    }


    public Application getAllbyStudentId(String id) {
        Application application = null;
        Statement stmt = ConnectBdd.getNewStatement();
        ResultSet rs = null;
        String query = "SELECT * FROM application WHERE studentid = " + id;
        try {
            rs = stmt.executeQuery(query);
            ArrayList<Application> apps = iterateThroughResultSet(rs);
            if(apps.size() > 0) {
                application = apps.get(0);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return application;
    }

    @Override
    public boolean update(Application application) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean add(Application application) {
        return false;
    }


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
