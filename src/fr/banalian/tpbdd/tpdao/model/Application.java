package fr.banalian.tpbdd.tpdao.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "application")
public class Application implements Serializable {

    @Id @PrimaryKeyJoinColumn(name = "studentid")
    private String studentId;

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "scholarship")
    private Scholarship scholarship;

    @OneToOne
    @JoinColumn(name = "university")
    private Courses university;

    @OneToOne(optional = false)
    @JoinColumn(name = "evaluation1")
    private Evaluation eval1;

    @OneToOne(optional = false)
    @JoinColumn(name = "evaluation2")
    private Evaluation eval2;

    @Column(name = "finalgrade")
    private float finalGrade;

    public Application(String studentId, Scholarship scholarship, Courses university, Evaluation eval1, Evaluation eval2, float finalGrade) {
        this.studentId = studentId;
        this.scholarship = scholarship;
        this.university = university;
        this.eval1 = eval1;
        this.eval2 = eval2;
        this.finalGrade = finalGrade;
    }

    public Application(String studentId, Scholarship scholarship, Courses university) {
        this.studentId = studentId;
        this.scholarship = scholarship;
        this.university = university;
    }

    public Application() {

    }


    public String toString() {
        return  "\t" + studentId + "\t\t" + scholarship + "\t\t" + university + "\t\t" + eval1 + "\t\t" + eval2 + "\t\t" + finalGrade;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Scholarship getGrant() {
        return scholarship;
    }

    public void setGrant(Scholarship scholarship) {
        this.scholarship = scholarship;
    }

    public Courses getUniversity() {
        return university;
    }

    public void setUniversity(Courses university) {
        this.university = university;
    }

    public Evaluation getEval1() {
        return eval1;
    }

    public void setEval1(Evaluation eval1) {
        this.eval1 = eval1;
    }

    public Evaluation getEval2() {
        return eval2;
    }

    public void setEval2(Evaluation eval2) {
        this.eval2 = eval2;
    }

    public float getFinalGrade() {
        return finalGrade;
    }

    public void setFinalGrade(float finalGrade) {
        this.finalGrade = finalGrade;
    }
}
