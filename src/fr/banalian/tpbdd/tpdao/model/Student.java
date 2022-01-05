package fr.banalian.tpbdd.tpdao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "student")
public class Student {

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "firstname")
    private String firstName;

    @Id
    @Column(name = "studentnumber")
    private String studentNumber;

    @Column(name = "averagegrade")
    private float averageGrade;

    public Student() {
    }

    public Student(String lastName, String firstName, String studentNumber, float averageGrade) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.studentNumber = studentNumber;
        this.averageGrade = averageGrade;
    }

    public String toString(){
        return "\t" + studentNumber + "\t\t" + lastName + "\t\t" + firstName + "\t\t" + averageGrade;
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

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public float getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(float averageGrade) {
        this.averageGrade = averageGrade;
    }
}
