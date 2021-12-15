package fr.banalian.tpbdd.tpdao.model;

public class Student {
    private String lastName;
    private String firstName;
    private String studentNumber;
    private float averageGrade;

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
