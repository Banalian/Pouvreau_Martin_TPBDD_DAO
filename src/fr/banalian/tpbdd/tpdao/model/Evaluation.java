package fr.banalian.tpbdd.tpdao.model;

import javax.persistence.*;

@Entity
@Table(name ="evaluation")
public class Evaluation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "grade")
    private float grade;

    @OneToOne
    @JoinColumn(name = "id")
    private Teacher teacher;

    public Evaluation() {
    }

    public Evaluation(int id, float grade, Teacher teacher) {
        this.id = id;
        this.grade = grade;
        this.teacher = teacher;
    }

    public Evaluation(float grade, Teacher teacher) {
        this.grade = grade;
        this.teacher = teacher;
    }

    public String toString() {
        return "\t" + id + "\t\t" + grade + "\t\t" + teacher.getId();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
