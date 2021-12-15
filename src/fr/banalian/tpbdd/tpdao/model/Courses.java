package fr.banalian.tpbdd.tpdao.model;

public class Courses {
    private int id;
    private String name;
    private int ects;
    private float hours;
    private String university;

    public Courses(int id, String name, int ects, float hours, String university) {
        this.id = id;
        this.name = name;
        this.ects = ects;
        this.hours = hours;
        this.university = university;
    }

    public Courses(String name, int ects, float hours, String university) {
        this.name = name;
        this.ects = ects;
        this.hours = hours;
        this.university = university;
    }

    public String toString() {
        return "\t" + id + "\t\t" + name + "\t\t" + hours + "\t\t" + ects + "\t\t" + university;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
