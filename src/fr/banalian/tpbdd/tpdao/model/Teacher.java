package fr.banalian.tpbdd.tpdao.model;

import fr.banalian.tpbdd.tpdao.ConnectBdd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Teacher {
    private int id;
    private String lastName;
    private String firstName;

    public Teacher(int id, String lastName, String firstName) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public Teacher(String lastName, String firstName) {
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public String toString() {
        return "\t" + id + "\t\t" + lastName + "\t\t" + firstName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


}
