package fr.banalian.tpbdd.tpdao.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "courses")
public class Courses implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "syllabus_name")
    private String syllabusName;
    private int ects;
    private float hours;
    private String university;

    private String description;

    public Courses(int id, String syllabusName, int ects, float hours, String university, String description) {
        this.id = id;
        this.syllabusName = syllabusName;
        this.ects = ects;
        this.hours = hours;
        this.university = university;
        this.description = description;
    }

    public Courses(String syllabusName, int ects, float hours, String university, String description) {
        this.syllabusName = syllabusName;
        this.ects = ects;
        this.hours = hours;
        this.university = university;
        this.description = description;
    }

    public Courses() {

    }

    public String toString() {
        return "\t" + id + "\t\t" + syllabusName + "\t\t" + hours + "\t\t" + ects + "\t\t" + university;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSyllabusName() {
        return syllabusName;
    }

    public void setSyllabusName(String syllabusName) {
        this.syllabusName = syllabusName;
    }

    public int getEcts() {
        return ects;
    }

    public void setEcts(int ects) {
        this.ects = ects;
    }

    public float getHours() {
        return hours;
    }

    public void setHours(float hours) {
        this.hours = hours;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
