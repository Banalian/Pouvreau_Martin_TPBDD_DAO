package fr.banalian.tpbdd.tpdao;


import java.sql.*;
import java.util.Properties;


public class ConnectBdd {

    private static Connection conn;
    private static Statement stmt;
    private static PreparedStatement pstmt;

    public static void initConnection() {

        Properties userInfo = new Properties();
        userInfo.setProperty("user", "root");
        userInfo.setProperty("password", "root");

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/erasmus", userInfo);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static Connection getConn() {
        return conn;
    }

    /**
     * Create a new statement and return it
     * @return a new statement
     */
    public static Statement getNewStatement() {
        try {
            stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stmt;
    }

    /**
     * Create a new prepared statement and return it
     * @param sql the sql query
     * @return a new prepared statement
     */
    public static PreparedStatement getNewPreparedStatement(String sql) {
        try {
            pstmt = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pstmt;
    }


}
