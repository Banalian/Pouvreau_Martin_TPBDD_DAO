package fr.banalian.tpbdd.tpdao.dao;

import fr.banalian.tpbdd.tpdao.ConnectBdd;
import fr.banalian.tpbdd.tpdao.model.Application;
import fr.banalian.tpbdd.tpdao.model.Grant;
import fr.banalian.tpbdd.tpdao.model.Teacher;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * DAO for grants. This class is used to access the database.
 */
public class GrantDAO implements DAO<Grant>{

    @Override
    public ArrayList<Grant> getAll() {
        ArrayList<Grant> grant = new ArrayList<>();
        Statement stmt = ConnectBdd.getNewStatement();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM erasmus.grant");
            grant = iterateThroughResultSet(rs);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return grant;
    }

    /**
     * get a connection to the database from connectBdd and delete all entry from the table
     * @return true if executed, false if errors found
     */
    public boolean deleteAll() {
        Statement stmt = ConnectBdd.getNewStatement();
        String query = "DELETE FROM erasmus.grant";
        try {
            stmt.executeUpdate(query);
            return true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public Grant get(int id) {
        Statement stmt = ConnectBdd.getNewStatement();
        Grant grant = null;
        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM erasmus.grant WHERE id = "+ id +";");
            grant = iterateThroughResultSet(rs).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grant;

    }

    @Override
    public boolean update(Grant grant) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        Statement stmt = ConnectBdd.getNewStatement();
        try {
            stmt.executeUpdate("DELETE FROM erasmus.grant WHERE id="+id+";");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    @Override
    public boolean add(Grant grant) {
        Statement stmt = ConnectBdd.getNewStatement();
        try {
            stmt.executeUpdate("INSERT INTO erasmus.grant (destination, totalseats, teacher) VALUES ('"+grant.getDestination()+"', "+grant.getTotalSeats()+", "+grant.getTeacherId()+")", Statement.RETURN_GENERATED_KEYS);
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                grant.setId(generatedKeys.getInt(1));
            }else {
                throw new SQLException("Creating grant failed, no ID obtained.");
            }
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    private ArrayList<Grant> iterateThroughResultSet(ResultSet rs) throws SQLException {
        ArrayList<Grant> grants = new ArrayList<>();
        while(rs.next()) {
            int id = rs.getInt("id");
            String destination = rs.getString("destination");
            int totalSeats = rs.getInt("totalseats");
            int teacherId = rs.getInt("teacher");

            Grant grant = new Grant(id, destination, totalSeats, teacherId);
            grants.add(grant);
        }
        return grants;
    }

    /**
     * Get all the grant for a certain destination
     * @param destination the destination concerned
     * @return an ArrayList of all the application where the condition is fulfilled
     */
    public ArrayList<Grant> getByDestination(String destination) {
        String query = "SELECT * FROM erasmus.grant WHERE destination ="+ destination;
        Statement stmt = ConnectBdd.getNewStatement();
        ResultSet rs;
        try {
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                rs.first();
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
     * Get all the grant managed by a certain teacher
     * @param id the id of the teacher managing this grant
     * @return an ArrayList of all the grant where the condition is fulfilled
     */
    public ArrayList<Grant> getByTeacherId(int id) {
        String query = "SELECT * FROM erasmus.grant WHERE teacherid ="+ id;
        Statement stmt = ConnectBdd.getNewStatement();
        ResultSet rs;
        try {
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                rs.first();
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
     * Get the grant corresponding to the id
     * @param id the id to search for
     * @return the grant correspondingt to the id
     */
    public Grant getById(int id) {
        String query = "SELECT * FROM erasmus.grant WHERE id ="+ id;
        Statement stmt = ConnectBdd.getNewStatement();
        ResultSet rs;
        try {
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                rs.first();
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
