package fr.banalian.tpbdd.tpdao.dao;

import fr.banalian.tpbdd.tpdao.ConnectBdd;
import fr.banalian.tpbdd.tpdao.model.Grant;
import fr.banalian.tpbdd.tpdao.model.Teacher;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class GrantDAO implements DAO<Grant>{

    @Override
    public ArrayList<Grant> getAll() {
        ArrayList<Grant> grant = new ArrayList<Grant>();
        Statement stmt = ConnectBdd.getNewStatement();
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery("SELECT * FROM grant");
            grant = iterateThroughResultSet(rs);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return grant;
    }

    public Grant get(int id) {
        Statement stmt = ConnectBdd.getNewStatement();
        Grant grant = null;
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery("SELECT * FROM grant WHERE id = "+ id +";");
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
            stmt.executeUpdate("DELETE FROM grant WHERE id="+id+";");
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
            stmt.executeUpdate("INSERT INTO grant (destination, totalseats, teacherid) VALUES ('"+grant.getDestination()+"', "+grant.getTotalSeats()+", "+grant.getTeacherId()+";)");
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;

    }

    private ArrayList<Grant> iterateThroughResultSet(ResultSet rs) throws SQLException {
        ArrayList<Grant> grants = new ArrayList<Grant>();
        while(rs.next()) {
            int id = rs.getInt("id");
            String destination = rs.getString("destination");
            int totalSeats = rs.getInt("totalseats");
            int teacherId = rs.getInt("teacherid")

            Grant grant = new Grant(id, destination, totalSeats, teacherId);
            grants.add(grant);
        }
        return grants;
    }
}